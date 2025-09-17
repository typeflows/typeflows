import io.typeflows.github.workflows.Cron
import io.typeflows.github.workflows.Job
import io.typeflows.github.workflows.Permission
import io.typeflows.github.workflows.Permission.Actions
import io.typeflows.github.workflows.Permission.Contents
import io.typeflows.github.workflows.Permission.PullRequests
import io.typeflows.github.workflows.PermissionLevel.Write
import io.typeflows.github.workflows.Permissions
import io.typeflows.github.workflows.RunsOn.Companion.UBUNTU_LATEST
import io.typeflows.github.workflows.Secrets
import io.typeflows.github.workflows.StrExp
import io.typeflows.github.workflows.Workflow
import io.typeflows.github.workflows.steps.RunCommand
import io.typeflows.github.workflows.steps.UseAction
import io.typeflows.github.workflows.steps.marketplace.Checkout
import io.typeflows.github.workflows.steps.marketplace.JavaDistribution.Temurin
import io.typeflows.github.workflows.steps.marketplace.JavaVersion.V21
import io.typeflows.github.workflows.steps.marketplace.SetupGradle
import io.typeflows.github.workflows.steps.marketplace.SetupJava
import io.typeflows.github.workflows.triggers.Schedule
import io.typeflows.github.workflows.triggers.WorkflowDispatch
import io.typeflows.util.Builder

class UpdateDependencies : Builder<Workflow> {
    override fun build() = Workflow("update-dependencies") {
        displayName = "Update Dependencies"
        on += Schedule {
            cron += Cron.of("0 12 * * *")
        }
        on += WorkflowDispatch()

        jobs += Job("update-dependencies", UBUNTU_LATEST) {
            permissions = Permissions(
                Actions to Write,
                Contents to Write,
                PullRequests to Write
            )

            steps += Checkout("Checkout repository") {
                token = Secrets.GITHUB_TOKEN.toString()
            }

            steps += SetupJava(Temurin, V21, "Set up JDK")

            steps += SetupGradle()

            steps += UseAction("stCarolas/setup-maven@v5", "Set up Maven") {
                with["maven-version"] = "3.9.9"
            }

            steps += RunCommand("./gradlew versionCatalogUpdate build", "Build (Root)")
            steps += RunCommand("cd jvm/examples/gradle && ./gradlew versionCatalogUpdate build && cd -", "Build (JVM Gradle)")
            steps += RunCommand("cd jvm/examples/maven/typeflows && mvn versions:update-properties -DgenerateBackupPoms=false && mvn typeflows:export && cd -", "Build (JVM Maven)")

            steps += RunCommand(
                $$"""
                if git diff --quiet; then
                  echo "has_changes=false" >> $GITHUB_OUTPUT
                else
                  echo "has_changes=true" >> $GITHUB_OUTPUT
                fi
                """.trimIndent(),
                "Check for changes"
            ) {
                id = "changes"
            }

            steps += UseAction("peter-evans/create-pull-request@v6", "Create Pull Request") {
                condition = StrExp.of("steps.changes.outputs.has_changes")

                with += mapOf(
                    "token" to Secrets.GITHUB_TOKEN.toString(),
                    "commit-message" to "chore: update dependencies",
                    "title" to "chore: update dependencies",
                    "body" to """
                        This PR updates dependencies in the various projects in this repo.
                        
                        Verified build passes with updated dependencies
                        
                        Please review the changes and merge if appropriate.
                    """.trimIndent(),
                    "branch" to "update-dependencies",
                    "delete-branch" to "true"
                )
            }
        }
    }
}
