import io.typeflows.TypeflowsRepo
import io.typeflows.github.TypeflowsGitHubRepo
import io.typeflows.standards.TypeflowsProjectStandards
import io.typeflows.util.Builder

class Typeflows : Builder<TypeflowsRepo> {
    override fun build() = TypeflowsGitHubRepo {
        files += TypeflowsProjectStandards()
    }
}
