package io.typeflows.kotlin

import io.typeflows.github.workflows.Cron
import io.typeflows.github.workflows.Job
import io.typeflows.github.workflows.Permission.Contents
import io.typeflows.github.workflows.Permission.PullRequests
import io.typeflows.github.workflows.PermissionLevel.Write
import io.typeflows.github.workflows.Permissions
import io.typeflows.github.workflows.RunsOn.Companion.UBUNTU_LATEST
import io.typeflows.github.workflows.Secrets
import io.typeflows.github.workflows.StrExp
import io.typeflows.github.workflows.Workflow
import io.typeflows.github.workflows.steps.RunCommand
import io.typeflows.github.workflows.steps.RunScript
import io.typeflows.github.workflows.steps.UseAction
import io.typeflows.github.workflows.steps.marketplace.Checkout
import io.typeflows.github.workflows.steps.marketplace.JavaDistribution
import io.typeflows.github.workflows.steps.marketplace.JavaVersion
import io.typeflows.github.workflows.steps.marketplace.SetupGradle
import io.typeflows.github.workflows.steps.marketplace.SetupJava
import io.typeflows.github.workflows.triggers.Schedule
import io.typeflows.github.workflows.triggers.WorkflowDispatch
import io.typeflows.util.Builder

class UpdateDependencies : Builder<Workflow> {
    override fun build() = Workflow.configure("update-dependencies") { workflow ->
        workflow.displayName = "Update Dependencies"
        workflow.on += Schedule.configure { it ->
            it.cron += Cron.of("0 12 * * *")
        }
        workflow.on += WorkflowDispatch()

        workflow.jobs += Job.configure("update-dependencies", UBUNTU_LATEST) { job ->
            job.permissions = Permissions(
                Contents to Write,
                PullRequests to Write
            )

            job.steps += Checkout.Companion.configure("Checkout repository") { checkout ->
                checkout.token = Secrets.GITHUB_TOKEN.toString()
            }

            job.steps += SetupJava.Companion(JavaDistribution.Temurin, JavaVersion.V17, "Set up JDK")

            job.steps += SetupGradle()

            job.steps += RunCommand.configure("./gradlew versionCatalogUpdate", "Update version catalog")

            job.steps += RunCommand.configure("./gradlew build", "Build project")

            job.steps += RunScript.configure(
                $$"""
                if git diff --quiet; then
                  echo "has_changes=false" >> $GITHUB_OUTPUT
                else
                  echo "has_changes=true" >> $GITHUB_OUTPUT
                fi
                """.trimIndent(),
                "Check for changes"
            ) { script ->
                script.id = "changes"
            }

            job.steps += UseAction.configure("peter-evans/create-pull-request@v6", "Create Pull Request") { action ->
                action.condition = StrExp.of("steps.changes.outputs.has_changes").isEqualTo("true")
                action.with += mapOf(
                    "token" to "\${{ secrets.GITHUB_TOKEN }}",
                    "commit-message" to "chore: update dependencies via version catalog",
                    "title" to "chore: update dependencies",
                    "body" to """
                        This PR updates dependencies using the gradle version catalog update plugin.
                        
                        Changes made:
                        - Updated dependency versions in gradle/libs.versions.toml
                        - Verified build passes with updated dependencies
                        
                        Please review the changes and merge if appropriate.
                    """.trimIndent(),
                    "branch" to "update-dependencies",
                    "delete-branch" to "true"
                )
            }
        }
    }
}
