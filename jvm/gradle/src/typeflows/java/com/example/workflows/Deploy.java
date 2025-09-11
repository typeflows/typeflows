package com.example.workflows;

import io.typeflows.github.workflows.Job;
import io.typeflows.github.workflows.Workflow;
import io.typeflows.github.workflows.steps.RunCommand;
import io.typeflows.github.workflows.steps.UseAction;
import io.typeflows.github.workflows.steps.marketplace.Checkout;
import io.typeflows.github.workflows.triggers.Branches;
import io.typeflows.github.workflows.triggers.Paths;
import io.typeflows.github.workflows.triggers.Push;
import io.typeflows.util.Builder;
import org.jetbrains.annotations.NotNull;

import static io.typeflows.github.workflows.RunsOn.UBUNTU_LATEST;

public class Deploy implements Builder<Workflow> {

    @NotNull
    @Override
    public Workflow build() {
        return Workflow.configure("deploy", workflow -> {
            workflow.displayName = "Deploy to Production";
            workflow.on.add(Push.configure(push -> {
                push.branches = Branches.Only("release");
                push.paths = Paths.Only("src/**");
            }));

            Job buildJob = Job.configure("build", UBUNTU_LATEST, job -> {
                job.steps.add(Checkout.configure());

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

