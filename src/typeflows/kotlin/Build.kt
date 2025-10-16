import io.typeflows.github.workflow.Job
import io.typeflows.github.workflow.Permission.Contents
import io.typeflows.github.workflow.PermissionLevel.Write
import io.typeflows.github.workflow.Permissions
import io.typeflows.github.workflow.RunsOn.Companion.UBUNTU_LATEST
import io.typeflows.github.workflow.Workflow
import io.typeflows.github.workflow.step.RunCommand
import io.typeflows.github.workflow.step.UseAction
import io.typeflows.github.workflow.step.marketplace.Checkout
import io.typeflows.github.workflow.step.marketplace.JavaDistribution.Adopt
import io.typeflows.github.workflow.step.marketplace.JavaVersion.V21
import io.typeflows.github.workflow.step.marketplace.SetupGradle
import io.typeflows.github.workflow.step.marketplace.SetupJava
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
            paths = Paths.Ignore("**/.md")
        }

        on += PullRequest {
            paths = Paths.Ignore("**/.md")
        }

        val buildRoot = Job("build-root", UBUNTU_LATEST) {
            name = "Build (Root)"
            steps += Checkout()
            steps += SetupJava(Adopt, V21)
            steps += SetupGradle()

            steps += RunCommand("./gradlew check")
        }

        val buildJvmGradle = Job("build-jvm-gradle", UBUNTU_LATEST) {
            name = "Build (JVM Gradle)"
            steps += Checkout()
            steps += SetupJava(Adopt, V21)
            steps += SetupGradle()

            steps += RunCommand("cd jvm/examples/gradle && ./gradlew check && cd -")
        }

        val buildJvmMaven = Job("build-jvm-maven", UBUNTU_LATEST) {
            name = "Build (JVM Maven)"
            steps += Checkout()
            steps += SetupJava(Adopt, V21)
            steps += UseAction("stCarolas/setup-maven@v5") {
                name = "Set up Maven"
                with["maven-version"] = "3.9.9"
            }

            steps += RunCommand("cd jvm/examples/maven/typeflows && mvn test")
        }

        jobs += listOfNotNull(buildRoot, buildJvmGradle, buildJvmMaven)
    }
}
