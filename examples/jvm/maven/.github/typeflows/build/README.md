# Build (build.yml)

```mermaid
%%{init: {"flowchart": {"curve": "basis"}}}%%
flowchart TD
    workflowdispatch(["👤 workflow_dispatch"])
    push(["📤 push<br/>branches(ignore: 1), paths(ignore: 1)"])
    pullrequest(["🔀 pull_request<br/>(*)"])
    subgraph buildyml["Build"]
        buildyml_metadata[["🔧 Workflow Config<br/>🔐 custom permissions"]]
        buildyml_build["build<br/>🐧 ubuntu-latest"]
    end
    workflowdispatch --> buildyml_build
    push --> buildyml_build
    pullrequest --> buildyml_build
```

## Job: build

| Job | OS | Dependencies | Config |
|-----|----|--------------|---------| 
| `build` | 🐧 ubuntu-latest | - | - |

### Steps

```mermaid
%%{init: {"flowchart": {"curve": "basis"}}}%%
flowchart TD
    step1["Step 1: Checkout"]
    style step1 fill:#f8f9fa,stroke:#495057
    action1["🎬 actions<br/>checkout"]
    style action1 fill:#e1f5fe,stroke:#0277bd
    step1 -.-> action1
    step2["Step 2: Setup Java"]
    style step2 fill:#f8f9fa,stroke:#495057
    action2["🎬 actions<br/>setup-java<br/><br/>📝 Inputs:<br/>• java-version: 21<br/>• distribution: adopt"]
    style action2 fill:#e1f5fe,stroke:#0277bd
    step2 -.-> action2
    step1 --> step2
    step3["Step 3: Setup Gradle"]
    style step3 fill:#f8f9fa,stroke:#495057
    action3["🎬 gradle<br/>actions/setup-gradle"]
    style action3 fill:#e1f5fe,stroke:#0277bd
    step3 -.-> action3
    step2 --> step3
    step4["Step 4: Build<br/>💻 bash"]
    style step4 fill:#f3e5f5,stroke:#7b1fa2
    step3 --> step4
    step5["Step 5: Publish Test Report<br/>🔐 if: always()"]
    style step5 fill:#f8f9fa,stroke:#495057
    action5["🎬 mikepenz<br/>action-junit-report<br/><br/>📝 Inputs:<br/>• report_paths: **/build/test-results/test/TES...<br/>• github_token: ${{ secrets.GITHUB_TOKEN }}<br/>• check_annotations: true<br/>• update_check: true"]
    style action5 fill:#e1f5fe,stroke:#0277bd
    step5 -.-> action5
    step4 --> step5
    step6["Step 6: Release (if required)<br/>🔐 if: github.ref == 'refs/heads/main'<br/>💻 bash"]
    style step6 fill:#f3e5f5,stroke:#7b1fa2
    step5 --> step6
    step7["Step 7: Repository Dispatch<br/>🔐 if: (github.ref == 'refs/heads/main' && steps.get-version.outputs.tag-created == 'true')<br/>💻 bash"]
    style step7 fill:#f3e5f5,stroke:#7b1fa2
    step6 --> step7
```

**Step Types Legend:**
- 🔘 **Step Nodes** (Gray): Workflow step execution
- 🔵 **Action Blocks** (Blue): External GitHub Actions
- 🔷 **Action Blocks** (Light Blue): Local repository actions
- 🟣 **Script Nodes** (Purple): Run commands/scripts
- **Solid arrows** (→): Step execution flow
- **Dotted arrows** (-.->): Action usage with inputs