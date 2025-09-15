package com.example.actions

import io.typeflows.github.workflows.Action
import io.typeflows.github.workflows.Conditions.always
import io.typeflows.github.workflows.Input
import io.typeflows.github.workflows.steps.RunCommand
import io.typeflows.github.workflows.steps.UseAction
import io.typeflows.util.Builder

class RunMavenBuildAndReport : Builder<Action> {
    override fun build() = Action("run-maven-build-and-report", "Builds from Maven and reports test results") {
        displayName = "Run Maven Build and Report"
        inputs += Input.string(
            "github-token",
            "GitHub token for authentication with the GitHub API"
        )

        steps += RunCommand("mvn clean test", "Build and Test")

        steps += UseAction("mikepenz/action-junit-report@v5.6.2", "Publish Test Report") {
            condition = always()
            with["report_paths"] = "**/target/surefire-reports/TEST-*.xml"
            with["github_token"] = $$"${{ inputs.github-token }}"
            with["check_annotations"] = "true"
            with["update_check"] = "true"
        }
    }
}
