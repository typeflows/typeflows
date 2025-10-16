# Build (build.yml)

```mermaid
%%{init: {"flowchart": {"curve": "basis"}}}%%
flowchart TD
    workflowdispatch(["👤 workflow_dispatch"])
    push(["📤 push<br/>paths(ignore: 1)"])
    pullrequest(["🔀 pull_request<br/>(*), paths(ignore: 1)"])
    subgraph buildyml["Build"]
        buildyml_metadata[["🔧 Workflow Config<br/>🔐 custom permissions"]]
        buildyml_buildroot["Build (Root)<br/>🐧 ubuntu-latest"]
        buildyml_buildjvmgradle["Build (JVM Gradle)<br/>🐧 ubuntu-latest"]
        buildyml_buildjvmmaven["Build (JVM Maven)<br/>🐧 ubuntu-latest"]
    end
    workflowdispatch --> buildyml_buildroot
    workflowdispatch --> buildyml_buildjvmgradle
    workflowdispatch --> buildyml_buildjvmmaven
    push --> buildyml_buildroot
    push --> buildyml_buildjvmgradle
    push --> buildyml_buildjvmmaven
    pullrequest --> buildyml_buildroot
    pullrequest --> buildyml_buildjvmgradle
    pullrequest --> buildyml_buildjvmmaven
```

## Job: Build (Root)

| Job | OS | Dependencies | Config |
|-----|----|--------------|---------| 
| `build-root` | 🐧 ubuntu-latest | - | - |

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
    step4["Step 4: Run command<br/>💻 bash"]
    style step4 fill:#f3e5f5,stroke:#7b1fa2
    step3 --> step4
```

**Step Types Legend:**
- 🔘 **Step Nodes** (Gray): Workflow step execution
- 🔵 **Action Blocks** (Blue): External GitHub Actions
- 🔷 **Action Blocks** (Light Blue): Local repository actions
- 🟣 **Script Nodes** (Purple): Run commands/scripts
- **Solid arrows** (→): Step execution flow
- **Dotted arrows** (-.->): Action usage with inputs




## Job: Build (JVM Gradle)

| Job | OS | Dependencies | Config |
|-----|----|--------------|---------| 
| `build-jvm-gradle` | 🐧 ubuntu-latest | - | - |

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
    step4["Step 4: Run command<br/>💻 bash"]
    style step4 fill:#f3e5f5,stroke:#7b1fa2
    step3 --> step4
```

**Step Types Legend:**
- 🔘 **Step Nodes** (Gray): Workflow step execution
- 🔵 **Action Blocks** (Blue): External GitHub Actions
- 🔷 **Action Blocks** (Light Blue): Local repository actions
- 🟣 **Script Nodes** (Purple): Run commands/scripts
- **Solid arrows** (→): Step execution flow
- **Dotted arrows** (-.->): Action usage with inputs




## Job: Build (JVM Maven)

| Job | OS | Dependencies | Config |
|-----|----|--------------|---------| 
| `build-jvm-maven` | 🐧 ubuntu-latest | - | - |

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
    step3["Step 3: Set up Maven"]
    style step3 fill:#f8f9fa,stroke:#495057
    action3["🎬 stCarolas<br/>setup-maven<br/><br/>📝 Inputs:<br/>• maven-version: 3.9.9"]
    style action3 fill:#e1f5fe,stroke:#0277bd
    step3 -.-> action3
    step2 --> step3
    step4["Step 4: Run command<br/>💻 bash"]
    style step4 fill:#f3e5f5,stroke:#7b1fa2
    step3 --> step4
```

**Step Types Legend:**
- 🔘 **Step Nodes** (Gray): Workflow step execution
- 🔵 **Action Blocks** (Blue): External GitHub Actions
- 🔷 **Action Blocks** (Light Blue): Local repository actions
- 🟣 **Script Nodes** (Purple): Run commands/scripts
- **Solid arrows** (→): Step execution flow
- **Dotted arrows** (-.->): Action usage with inputs