package com.example

import com.example.actions.RunGradleBuildAndReport
import com.example.workflows.Build
import com.example.workflows.Deploy
import io.typeflows.github.DotGitHub
import io.typeflows.github.TypeflowsGitHubRepo
import io.typeflows.github.dependabot.Dependabot
import io.typeflows.github.dependabot.PackageEcosystem.Maven
import io.typeflows.github.dependabot.Schedule
import io.typeflows.github.dependabot.ScheduleInterval.Monthly
import io.typeflows.github.dependabot.Update
import io.typeflows.github.workflows.WorkflowVisualisations
import io.typeflows.util.Builder

class Typeflows : Builder<TypeflowsGitHubRepo> {
    override fun build() = TypeflowsGitHubRepo {
        dotGithub = DotGitHub {
            workflows += Build()
            workflows += Deploy()

            actions += RunGradleBuildAndReport()

            files += Dependabot {
                updates += Update(Maven) {
                    schedule = Schedule(Monthly)
                }
            }

            files += WorkflowVisualisations(workflows)
        }
    }
}
