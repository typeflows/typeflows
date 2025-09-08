package com.example.workflows

import io.typeflows.github.workflows.Conditions.always
import io.typeflows.github.workflows.GitHub
import io.typeflows.github.workflows.Job
import io.typeflows.github.workflows.Permission
import io.typeflows.github.workflows.PermissionLevel
import io.typeflows.github.workflows.Permissions
import io.typeflows.github.workflows.RunsOn
import io.typeflows.github.workflows.Secrets
import io.typeflows.github.workflows.StrExp
import io.typeflows.github.workflows.Workflow
import io.typeflows.github.workflows.steps.RunCommand
import io.typeflows.github.workflows.steps.RunScript
import io.typeflows.github.workflows.steps.SendRepositoryDispatch
import io.typeflows.github.workflows.steps.UseAction
import io.typeflows.github.workflows.steps.marketplace.Checkout
import io.typeflows.github.workflows.steps.marketplace.JavaDistribution.Adopt
import io.typeflows.github.workflows.steps.marketplace.JavaVersion
import io.typeflows.github.workflows.steps.marketplace.SetupGradle
import io.typeflows.github.workflows.steps.marketplace.SetupJava
import io.typeflows.github.workflows.triggers.Branches
import io.typeflows.github.workflows.triggers.Paths
import io.typeflows.github.workflows.triggers.PullRequest
import io.typeflows.github.workflows.triggers.Push
import io.typeflows.github.workflows.triggers.WorkflowDispatch
import io.typeflows.util.Builder

class Build : Builder<Workflow> {
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
            steps += Checkout()

            steps += SetupJava(Adopt, JavaVersion.V21)

            steps += SetupGradle()

            steps += RunCommand("./gradlew check --info", "Build")

            steps += UseAction("mikepenz/action-junit-report@v5.6.2", "Publish Test Report") {
                condition = always()
                with["report_paths"] = "**/build/test-results/test/TEST-*.xml"
                with["github_token"] = Secrets.string("GITHUB_TOKEN")
                with["check_annotations"] = "true"
                with["update_check"] = "true"
            }

            steps += RunScript("scripts/release-if-required.sh", "Release (if required)") {
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
