import io.typeflows.TypeflowsRepo
import io.typeflows.github.DotGitHub
import io.typeflows.github.TypeflowsGitHubRepo
import io.typeflows.github.visualisation.WorkflowVisualisations
import io.typeflows.standards.TypeflowsProjectStandards
import io.typeflows.util.Builder

class Typeflows : Builder<TypeflowsRepo> {
    override fun build() = TypeflowsGitHubRepo {
        dotGithub = DotGitHub {
            workflows += Build()
            workflows += UpdateDependencies()

            files += WorkflowVisualisations(workflows)
        }
        files += TypeflowsProjectStandards()
    }
}
