import io.typeflows.github.WorkflowBuilder;
import io.typeflows.github.workflows.Job;
import io.typeflows.github.workflows.Workflow;
import io.typeflows.github.workflows.steps.RunCommand;
import io.typeflows.github.workflows.steps.UseAction;
import io.typeflows.github.workflows.triggers.Branches;
import io.typeflows.github.workflows.triggers.Paths;
import io.typeflows.github.workflows.triggers.Push;
import org.jetbrains.annotations.NotNull;

import static io.typeflows.github.workflows.RunsOn.UBUNTU_LATEST;
import static io.typeflows.github.workflows.steps.MarketplaceAction.checkout;

public class Deploy implements WorkflowBuilder {

    @NotNull
    @Override
    public Workflow toWorkflow() {
        return Workflow.configure("Deploy to Production", workflow -> {
            workflow.on.add(Push.configure(push -> {
                push.branches = Branches.Only("release");
                push.paths = Paths.Only("src/**");
            }));

            Job buildJob = Job.configure("build", UBUNTU_LATEST, job -> {
                job.steps.add(checkout());

                job.steps.add(UseAction.configure("actions/setup-node@v4", "node", action -> {
                    action.with.put("node-version", "20");
                }));

                job.steps.add(RunCommand.configure("npm run build && npm test"));
            });

            workflow.jobs.add(buildJob);

            workflow.jobs.add(Job.configure("deploy", UBUNTU_LATEST, job -> {
                job.needs.add(buildJob);
                job.steps.add(UseAction.configure("actions/deploy@v2", "deploy", action -> {
                    action.with.put("target", "production");
                    action.with.put("token", "${{ secrets.DEPLOY_TOKEN }}");
                }));
            }));
        });
    }
}

