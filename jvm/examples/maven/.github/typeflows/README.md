# Workflows

```mermaid
flowchart LR
    workflowdispatch(["👤 workflow_dispatch"])
    push(["📤 push"])
    pullrequest(["🔀 pull_request"])
    repositorydispatchgithubrepository(["🔔 repository_dispatch<br/>→ this repo"])
    buildyml["Build"]
    deployyml["Deploy to Production"]
    noreceiverrelease["❌ No receiver<br/>for release"]
    workflowdispatch --> buildyml
    push -->|"branches(ignore: 1), paths(ignore: 1)"|buildyml
    push -->|"branches(only: 1), paths(only: 1)"|deployyml
    pullrequest -->|"(*), paths(ignore: 1)"|buildyml
    buildyml --> repositorydispatchgithubrepository
    repositorydispatchgithubrepository --> noreceiverrelease
```

## Workflows

- [Build](./build/)
- [Deploy to Production](./deploy/)