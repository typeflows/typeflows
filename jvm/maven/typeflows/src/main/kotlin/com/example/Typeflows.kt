package com.example

import com.example.actions.RunGradleBuildAndReport
import com.example.workflows.Build
import io.typeflows.TypeflowsRepo
import io.typeflows.github.DotGitHub
import io.typeflows.github.TypeflowsGitHubRepo
import io.typeflows.github.dependabot.Dependabot
import io.typeflows.github.dependabot.PackageEcosystem.Maven
import io.typeflows.github.dependabot.Schedule
import io.typeflows.github.dependabot.ScheduleInterval.Monthly
import io.typeflows.github.dependabot.Update
import io.typeflows.util.Builder

class Typeflows : Builder<TypeflowsRepo> {
    override fun build() = TypeflowsGitHubRepo {
        dotGithub = DotGitHub {
            workflows += Build()

            actions += RunGradleBuildAndReport()

            files += Dependabot {
                updates += Update(Maven) {
                    schedule = Schedule(Monthly)
                }
            }
        }
    }
}
