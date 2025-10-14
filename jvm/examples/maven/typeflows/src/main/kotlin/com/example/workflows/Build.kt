package com.example.workflows

import io.typeflows.github.workflow.Conditions.always
import io.typeflows.github.workflow.GitHub
import io.typeflows.github.workflow.Job
import io.typeflows.github.workflow.Permission.Contents
import io.typeflows.github.workflow.PermissionLevel.Write
import io.typeflows.github.workflow.Permissions
import io.typeflows.github.workflow.RunsOn.Companion.UBUNTU_LATEST
import io.typeflows.github.workflow.Secrets
import io.typeflows.github.workflow.StrExp
import io.typeflows.github.workflow.Workflow
import io.typeflows.github.workflow.step.RunCommand
import io.typeflows.github.workflow.step.RunScript
import io.typeflows.github.workflow.step.SendRepositoryDispatch
import io.typeflows.github.workflow.step.UseAction
import io.typeflows.github.workflow.step.marketplace.Checkout
import io.typeflows.github.workflow.step.marketplace.JavaDistribution.Adopt
import io.typeflows.github.workflow.step.marketplace.JavaVersion.V21
import io.typeflows.github.workflow.step.marketplace.SetupGradle
import io.typeflows.github.workflow.step.marketplace.SetupJava
import io.typeflows.github.workflow.trigger.Branches
import io.typeflows.github.workflow.trigger.Paths
import io.typeflows.github.workflow.trigger.PullRequest
import io.typeflows.github.workflow.trigger.Push
import io.typeflows.github.workflow.trigger.WorkflowDispatch
import io.typeflows.util.Builder

class Build : Builder<Workflow> {
    override fun build() = Workflow("build") {
        displayName = "Build"
        on += WorkflowDispatch()

        permissions = Permissions(Contents to Write)

        on += Push {
            branches = Branches.Ignore("develop")
            paths = Paths.Ignore("**/.md")
        }

        on += PullRequest {
            paths = Paths.Ignore("**/.md")
        }

        jobs += Job("build", UBUNTU_LATEST) {
            steps += Checkout()

            steps += SetupJava(Adopt, V21)

            steps += SetupGradle()

            steps += RunCommand("./gradlew check --info") {
                name = "Build"
            }

            steps += UseAction("mikepenz/action-junit-report@v5.6.2") {
                name = "Publish Test Report"
                condition = always()
                with["report_paths"] = "**/build/test-results/test/TEST-*.xml"
                with["github_token"] = Secrets.string("GITHUB_TOKEN")
                with["check_annotations"] = "true"
                with["update_check"] = "true"
            }

            steps += RunScript("scripts/release-if-required.sh") {
                name = "Release (if required)"
                id = "get-version"
                condition = GitHub.ref.isEqualTo("refs/heads/main")
                env["GH_TOKEN"] = Secrets.string("WORKFLOWS_TOKEN")
            }

            steps += SendRepositoryDispatch(
                "release", Secrets.string("WORKFLOWS_TOKEN"),
                mapOf("tag" to $$"${{ steps.get-version.outputs.tag }}")
            ) {
                condition = GitHub.ref.isEqualTo("refs/heads/main")
                    .and(StrExp.of("steps.get-version.outputs.tag-created").isEqualTo("true"))
            }
        }
    }
}
