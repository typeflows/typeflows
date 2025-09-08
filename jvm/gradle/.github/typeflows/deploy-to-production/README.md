# Deploy to Production

```mermaid
%%{init: {"flowchart": {"curve": "basis"}}}%%
flowchart TD
    push(["📤 push<br/>branches(only: 1), paths(only: 1)"])
    subgraph deploytoproduction["Deploy to Production"]
        deploytoproduction_build["build<br/>🐧 ubuntu-latest"]
        deploytoproduction_deploy["deploy<br/>🐧 ubuntu-latest"]
        deploytoproduction_build --> deploytoproduction_deploy
    end
    push --> deploytoproduction_build
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
    step2["Step 2: node"]
    style step2 fill:#f8f9fa,stroke:#495057
    action2["🎬 actions<br/>setup-node<br/><br/>📝 Inputs:<br/>• node-version: 20"]
    style action2 fill:#e1f5fe,stroke:#0277bd
    step2 -.-> action2
    step1 --> step2
    step3["Step 3: Run command<br/>💻 bash"]
    style step3 fill:#f3e5f5,stroke:#7b1fa2
    step2 --> step3
```

**Step Types Legend:**
- 🔘 **Step Nodes** (Gray): Workflow step execution
- 🔵 **Action Blocks** (Blue): External GitHub Actions
- 🔷 **Action Blocks** (Light Blue): Local repository actions
- 🟣 **Script Nodes** (Purple): Run commands/scripts
- **Solid arrows** (→): Step execution flow
- **Dotted arrows** (-.->): Action usage with inputs




## Job: deploy

| Job | OS | Dependencies | Config |
|-----|----|--------------|---------| 
| `deploy` | 🐧 ubuntu-latest | `build` | - |

### Steps

```mermaid
%%{init: {"flowchart": {"curve": "basis"}}}%%
flowchart TD
    step1["Step 1: deploy"]
    style step1 fill:#f8f9fa,stroke:#495057
    action1["🎬 actions<br/>deploy<br/><br/>📝 Inputs:<br/>• target: production<br/>• token: ${{ secrets.DEPLOY_TOKEN }}"]
    style action1 fill:#e1f5fe,stroke:#0277bd
    step1 -.-> action1
```

**Step Types Legend:**
- 🔘 **Step Nodes** (Gray): Workflow step execution
- 🔵 **Action Blocks** (Blue): External GitHub Actions
- 🔷 **Action Blocks** (Light Blue): Local repository actions
- 🟣 **Script Nodes** (Purple): Run commands/scripts
- **Solid arrows** (→): Step execution flow
- **Dotted arrows** (-.->): Action usage with inputs