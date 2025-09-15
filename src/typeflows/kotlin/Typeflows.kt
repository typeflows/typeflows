import io.typeflows.TypeflowsRepo
import io.typeflows.env.Env.Companion.javaVersion
import io.typeflows.fs.TypeflowsResources
import io.typeflows.github.TypeflowsGitHubRepo
import io.typeflows.standards.TypeflowsProjectStandards
import io.typeflows.util.Builder

class Typeflows : Builder<TypeflowsRepo> {
    override fun build() = TypeflowsGitHubRepo {
        files += TypeflowsProjectStandards()
        files += TypeflowsResources(Typeflows::class.java)
        javaVersion = javaVersion("21")
    }
}
