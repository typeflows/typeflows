# Update Dependencies (update-dependencies.yml)

```mermaid
%%{init: {"flowchart": {"curve": "basis"}}}%%
flowchart TD
    schedule(["⏰ schedule<br/>0 12 * * *"])
    workflowdispatch(["👤 workflow_dispatch"])
    subgraph updatedependenciesyml["Update Dependencies"]
        updatedependenciesyml_updatedependencies["update-dependencies<br/>🐧 ubuntu-latest"]
    end
    schedule --> updatedependenciesyml_updatedependencies
    workflowdispatch --> updatedependenciesyml_updatedependencies
```

## Job: update-dependencies

| Job | OS | Dependencies | Config |
|-----|----|--------------|---------| 
| `update-dependencies` | 🐧 ubuntu-latest | - | 🔐 perms |

### Steps

```mermaid
%%{init: {"flowchart": {"curve": "basis"}}}%%
flowchart TD
    step1["Step 1: Checkout repository"]
    style step1 fill:#f8f9fa,stroke:#495057
    action1["🎬 actions<br/>checkout<br/><br/>📝 Inputs:<br/>• token: ${{ secrets.GITHUB_TOKEN }}"]
    style action1 fill:#e1f5fe,stroke:#0277bd
    step1 -.-> action1
    step2["Step 2: Setup Java"]
    style step2 fill:#f8f9fa,stroke:#495057
    action2["🎬 actions<br/>setup-java<br/><br/>📝 Inputs:<br/>• java-version: 21<br/>• distribution: temurin"]
    style action2 fill:#e1f5fe,stroke:#0277bd
    step2 -.-> action2
    step1 --> step2
    step3["Step 3: Setup Gradle"]
    style step3 fill:#f8f9fa,stroke:#495057
    action3["🎬 gradle<br/>actions/setup-gradle"]
    style action3 fill:#e1f5fe,stroke:#0277bd
    step3 -.-> action3
    step2 --> step3
    step4["Step 4: Set up Maven"]
    style step4 fill:#f8f9fa,stroke:#495057
    action4["🎬 stCarolas<br/>setup-maven<br/><br/>📝 Inputs:<br/>• maven-version: 3.9.9"]
    style action4 fill:#e1f5fe,stroke:#0277bd
    step4 -.-> action4
    step3 --> step4
    step5["Step 5: Build (Root)<br/>💻 bash"]
    style step5 fill:#f3e5f5,stroke:#7b1fa2
    step4 --> step5
    step6["Step 6: Build (JVM Gradle)<br/>💻 bash"]
    style step6 fill:#f3e5f5,stroke:#7b1fa2
    step5 --> step6
    step7["Step 7: Build (JVM Maven)<br/>💻 bash"]
    style step7 fill:#f3e5f5,stroke:#7b1fa2
    step6 --> step7
    step8["Step 8: Check for changes<br/>💻 bash"]
    style step8 fill:#f3e5f5,stroke:#7b1fa2
    step7 --> step8
    step9["Step 9: Create Pull Request<br/>🔐 if: steps.changes.outputs.has_changes"]
    style step9 fill:#f8f9fa,stroke:#495057
    action9["🎬 peter-evans<br/>create-pull-request<br/><br/>📝 Inputs:<br/>• token: ${{ secrets.GITHUB_TOKEN }}<br/>• commit-message: chore: update dependencies<br/>• title: chore: update dependencies<br/>• body: This PR updates dependencies i...<br/>• branch: update-dependencies<br/>• delete-branch: true"]
    style action9 fill:#e1f5fe,stroke:#0277bd
    step9 -.-> action9
    step8 --> step9
```

**Step Types Legend:**
- 🔘 **Step Nodes** (Gray): Workflow step execution
- 🔵 **Action Blocks** (Blue): External GitHub Actions
- 🔷 **Action Blocks** (Light Blue): Local repository actions
- 🟣 **Script Nodes** (Purple): Run commands/scripts
- **Solid arrows** (→): Step execution flow
- **Dotted arrows** (-.->): Action usage with inputs