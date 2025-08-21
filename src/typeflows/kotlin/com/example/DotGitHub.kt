package com.example

import com.example.actions.RunGradleBuildAndReport
import com.example.workflows.Build
import com.example.workflows.Deploy
import io.typeflows.github.DotGitHub
import io.typeflows.github.DotGitHubBuilder
import io.typeflows.github.GitHubFile.Companion.dependabot
import io.typeflows.github.dependabot.Dependabot
import io.typeflows.github.dependabot.PackageEcosystem.Maven
import io.typeflows.github.dependabot.Schedule
import io.typeflows.github.dependabot.ScheduleInterval.Monthly
import io.typeflows.github.dependabot.Update

class DotGitHub : DotGitHubBuilder {
    override fun build() = DotGitHub.Companion {
        workflows += Build()
        workflows += Deploy()

        actions += RunGradleBuildAndReport()

        files += dependabot(Dependabot.Companion {
            updates += Update(Maven) {
                schedule = Schedule(Monthly)
            }
        })
    }
}
