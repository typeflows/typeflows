import io.typeflows.github.WorkflowBuilder
import io.typeflows.github.workflow.Job
import io.typeflows.github.workflow.Permission.Contents
import io.typeflows.github.workflow.PermissionLevel.Write
import io.typeflows.github.workflow.Permissions
import io.typeflows.github.workflow.RunsOn.Companion.UBUNTU_LATEST
import io.typeflows.github.workflow.Workflow
import io.typeflows.github.workflow.steps.MarketplaceAction.Companion.checkout
import io.typeflows.github.workflow.steps.MarketplaceAction.Companion.setupGradle
import io.typeflows.github.workflow.steps.MarketplaceAction.Companion.setupJava
import io.typeflows.github.workflow.steps.RunCommand
import io.typeflows.github.workflow.steps.RunScript
import io.typeflows.github.workflow.steps.UseAction
import io.typeflows.github.workflow.triggers.Branches
import io.typeflows.github.workflow.triggers.Paths
import io.typeflows.github.workflow.triggers.PullRequest
import io.typeflows.github.workflow.triggers.Push
import io.typeflows.github.workflow.triggers.WorkflowDispatch

class Build : WorkflowBuilder {
    override fun toWorkflow() = Workflow("Build") {
        on += WorkflowDispatch.configure()

        permissions = Permissions.Granular(Contents to Write)

        on += Push {
            branches = Branches.Ignore("develop")
            paths = Paths.Ignore("**/.md")
        }

        on += PullRequest {
            paths = Paths.Ignore("**/.md")
        }

        jobs += Job("build", UBUNTU_LATEST) {
            steps += checkout()

            steps += setupJava {
                with["distribution"] = "adopt"
                with["java-version"] = "21"
            }

            steps += setupGradle()

            steps += RunCommand("./gradlew check --info", "Build")

            steps += UseAction("mikepenz/action-junit-report@v5.6.2", "Publish Test Report") {
                condition = "always()"
                with["report_paths"] = "**/build/test-results/test/TEST-*.xml"
                with["github_token"] = $$"${{ secrets.GITHUB_TOKEN }}"
                with["check_annotations"] = "true"
                with["update_check"] = "true"
            }

            steps += RunScript("scripts/release-if-required.sh", "Release (if required)") {
                id = "get-version"
                condition = "github.ref == 'refs/heads/main'"
                env["GH_TOKEN"] = $$"${{ secrets.WORKFLOWS_TOKEN }}"
            }

            steps += UseAction("peter-evans/repository-dispatch@v3", "Trigger release workflow") {
                condition = """github.ref == 'refs/heads/main' && steps.get-version.outputs.tag-created == 'true'"""
                with["token"] = $$"${{ secrets.WORKFLOWS_TOKEN }}"
                with["event-type"] = "release"
                with["client-payload"] = $$"{\"tag\": \"${{ steps.get-version.outputs.tag }}\"}"
            }
        }
    }
}
