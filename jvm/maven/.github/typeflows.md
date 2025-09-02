# Workflows

- **Build**

## Table of Contents

- [Workflow Triggers - Flowchart](#workflow-triggers---flowchart)
- [Build](#build)

## Workflow Triggers - Flowchart

```mermaid
flowchart LR
    workflow_dispatch(["👤 workflow_dispatch"])
    push(["📤 push<br/>branches#91;ignore: 1#93;, paths#91;ignore: 1#93;"])
    pull_request(["🔀 pull_request<br/>#91;*#93;"])
    build[Build]
    workflow_dispatch --> build
    push --> build
    pull_request --> build
```

## Build

```mermaid
%%{init: {"flowchart": {"curve": "basis"}}}%%
flowchart TD
    workflow_dispatch(["👤 workflow_dispatch"])
    push(["📤 push<br/>branches#91;ignore: 1#93;, paths#91;ignore: 1#93;"])
    pull_request(["🔀 pull_request<br/>#91;*#93;"])
    subgraph build["Build"]
        build_metadata[["🔧 Workflow Config<br/>🔐 custom permissions"]]
        build_build["build<br/>🐧 ubuntu-latest"]
    end
    workflow_dispatch --> build_build
    push --> build_build
    pull_request --> build_build
```