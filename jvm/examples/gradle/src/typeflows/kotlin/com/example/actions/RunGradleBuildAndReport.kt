package com.example.actions

import io.typeflows.github.workflows.Action
import io.typeflows.github.workflows.Conditions.always
import io.typeflows.github.workflows.Input
import io.typeflows.github.workflows.Workflow
import io.typeflows.github.workflows.steps.RunCommand
import io.typeflows.github.workflows.steps.UseAction
import io.typeflows.util.Builder

class RunGradleBuildAndReport : Builder<Action> {
    override fun build() = Action("run-build-and-report", "Builds from Gradle and reports test results") {
        displayName = "Run Gradle Build and Report"
        inputs += Input.string(
            "github-token",
            "GitHub token for authentication with the GitHub API"
        )

        steps += RunCommand("./gradlew check --info", "Build")

        steps += UseAction("mikepenz/action-junit-report@v5.6.2", "Publish Test Report") {
            condition = always()
            with["report_paths"] = "**/build/test-results/test/TEST-*.xml"
            with["github_token"] = Workflow.Inputs.string("github-token")
            with["check_annotations"] = "true"
            with["update_check"] = "true"
        }
    }
}
