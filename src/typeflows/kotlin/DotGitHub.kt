import com.example.workflows.Build
import com.example.workflows.Deploy
import io.typeflows.github.DotGitHub
import io.typeflows.github.DotGitHubBuilder

class DotGitHub : DotGitHubBuilder {
    override fun build() = DotGitHub.Companion {
        workflows += Build().build()
        workflows += Deploy().build()
    }
}
