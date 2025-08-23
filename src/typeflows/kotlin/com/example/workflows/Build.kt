package com.example.workflows

import io.typeflows.github.workflows.Job
import io.typeflows.github.workflows.Permission
import io.typeflows.github.workflows.PermissionLevel
import io.typeflows.github.workflows.Permissions
import io.typeflows.github.workflows.RunsOn
import io.typeflows.github.workflows.Workflow
import io.typeflows.github.workflows.WorkflowBuilder
import io.typeflows.github.workflows.steps.MarketplaceAction
import io.typeflows.github.workflows.steps.RunCommand
import io.typeflows.github.workflows.steps.RunScript
import io.typeflows.github.workflows.steps.UseAction
import io.typeflows.github.workflows.triggers.Branches
import io.typeflows.github.workflows.triggers.Paths
import io.typeflows.github.workflows.triggers.PullRequest
import io.typeflows.github.workflows.triggers.Push
import io.typeflows.github.workflows.triggers.WorkflowDispatch

class Build : WorkflowBuilder {
    override fun build() = Workflow("Build") {
        on += WorkflowDispatch.configure()

        permissions = Permissions(Permission.Contents to PermissionLevel.Write)

        on += Push {
            branches = Branches.Ignore("develop")
            paths = Paths.Ignore("**/.md")
        }

        on += PullRequest {
            paths = Paths.Ignore("**/.md")
        }

        jobs += Job("build", RunsOn.UBUNTU_LATEST) {
            steps += MarketplaceAction.checkout()

            steps += MarketplaceAction.setupJava {
                with["distribution"] = "adopt"
                with["java-version"] = "21"
            }

            steps += MarketplaceAction.setupGradle()

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
