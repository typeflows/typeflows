---
description: Typeflows GitHub Actions conversion instructions for Kotlin/Java workflow builders
globs:
  - "**/*.kt"
  - "**/*.java"
  - "src/main/kotlin/**/*"
  - "src/main/java/**/*"
  - ".github/workflows/**/*.yml"
  - ".github/workflows/**/*.yaml"
author: Typeflows
version: 1.0
---

# Typeflows Instructions

# Import GitHub Actions to Typeflows

Convert GitHub Actions workflow YAML files to type-safe Kotlin/Java code using Typeflows.

## Quick Start

**IMPORTANT**: This command will scan your `.github/` directory by default to import ALL workflows and actions unless you specify otherwise.

### Language Selection

Choose your preferred language:

- **Kotlin**: Idiomatic DSL syntax with operator overloading
- **Java**: Familiar builder patterns with static methods

If no preference is specified, examples will be provided in both languages.

### Automatic Import Process

By default, this command will:

1. Scan `.github/workflows/` for workflow files (*.yml, *.yaml)
2. Scan `.github/actions/` for action definitions
3. Generate Kotlin/Java classes in `src/typeflows/{kotlin|java}/workflows/`
4. Generate action classes in `src/typeflows/{kotlin|java}/actions/`
5. Follow proper naming conventions (YAML file → PascalCase class name)

## Dual-Language API Pattern

Typeflows provides dual-language support through companion objects:

### Kotlin DSL Pattern

- Uses `operator fun invoke()` for natural DSL syntax
- Call companion objects directly: `Workflow("name") { ... }`
- Leverages Kotlin's operator overloading and extension functions

### Java Builder Pattern

- Uses `@JvmStatic fun configure()` methods for builder pattern
- Call static methods: `Workflow.configure("name", workflow -> { ... })`
- Familiar Java lambda expressions and method chaining

## Language Choice

**IMPORTANT**: When converting workflows, always ask the user which language they prefer:

- **Kotlin** - Uses DSL syntax with lambdas and operator overloading
- **Java** - Uses builder pattern with configure() methods and lambda expressions

Provide examples in both languages unless the user specifically requests one language only.

### Key Differences

**Kotlin DSL Pattern:**

- Uses `+=` operator for adding items: `on += Push()`
- Lambda syntax in braces: `Push { branches = ... }`
- Direct property assignment: `branches = Branches.Only("main")`
- Map-style access: `env["KEY"] = "value"`

**Java Builder Pattern:**

- Uses `.add()` method: `workflow.on.add(Push.configure())`
- Lambda with configure methods: `Push.configure(push -> { ... })`
- Field assignment in lambdas: `push.branches = Branches.Only("main")`
- Map `.put()` method: `workflow.env.put("KEY", "value")`

## Required Imports

### Kotlin Imports

```kotlin
// Core workflow classes
import io.typeflows.github.workflows.*

// Triggers
import io.typeflows.github.workflows.triggers.*

// Steps  
import io.typeflows.github.workflows.steps.*

// Permission enums and levels
import io.typeflows.github.workflows.Permission.*
import io.typeflows.github.workflows.PermissionLevel.*

// Runner constants
import io.typeflows.github.workflows.RunsOn.Companion.*
```

### Java Imports

```java
// Core workflow classes

import io.typeflows.github.WorkflowBuilder;

// Triggers

// Steps  

// Permission enums and levels - static imports

// Runner constants - static imports

```

## Basic Workflow Structure

### YAML to Kotlin/Java Conversion

**YAML:**

```yaml
name: CI Pipeline
on:
  push:
    branches: [main, develop]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v4
    - run: echo 'Hello World'
```

**Typeflows Kotlin:**

```kotlin
val workflow = Workflow("CI Pipeline") {
    on += Push {
        branches = Branches.Only("main", "develop")
    }
    
    jobs += Job("test", UBUNTU_LATEST) {
        steps += UseAction("actions/checkout@v4")
        steps += RunCommand("echo 'Hello World'")
    }
}
```

**Typeflows Java:**

```java
public class CIPipeline implements WorkflowBuilder {
    @Override
    public Workflow toWorkflow() {
        return Workflow.configure("CI Pipeline", workflow -> {
            workflow.on.add(Push.configure(push -> {
                push.branches = Branches.Only("main", "develop");
            }));

            workflow.jobs.add(Job.configure("test", UBUNTU_LATEST, job -> {
                job.steps.add(UseAction.configure("actions/checkout@v4"));
                job.steps.add(RunCommand.configure("echo 'Hello World'"));
            }));
        });
    }
}
```

## Comprehensive Trigger Mappings

### Push Trigger

| YAML                              | Typeflows Kotlin                                                  | Typeflows Java                                                                                        |
|-----------------------------------|-------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------|
| `push: {}`                        | `on += Push()`                                                    | `workflow.on.add(Push.configure())`                                                                   |
| `push:`<br/>`  branches: [main]`  | `on += Push {`<br/>`    branches = Branches.Only("main")`<br/>`}` | `workflow.on.add(Push.configure(push -> {`<br/>`    push.branches = Branches.Only("main");`<br/>`}))` |
| `push:`<br/>`  paths: ["src/**"]` | `on += Push {`<br/>`    paths = Paths.Only("src/**")`<br/>`}`     | `workflow.on.add(Push.configure(push -> {`<br/>`    push.paths = Paths.Only("src/**");`<br/>`}))`     |
| `push:`<br/>`  tags: [v*]`        | `on += Push {`<br/>`    tags += Tag.of("v*")`<br/>`}`             | `workflow.on.add(Push.configure(push -> {`<br/>`    push.tags.add(Tag.of("v*"));`<br/>`}))`           |

### Pull Request Triggers

| YAML                                                 | Typeflows Kotlin                                                                                                       | Typeflows Java                                                                                                                                                 |
|------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `pull_request: {}`                                   | `on += PullRequest()`                                                                                                  | `workflow.on.add(PullRequest.configure())`                                                                                                                     |
| `pull_request:`<br/>`  types: [opened, synchronize]` | `on += PullRequest {`<br/>`    types += PullRequestType.opened`<br/>`    types += PullRequestType.synchronize`<br/>`}` | `workflow.on.add(PullRequest.configure(pr -> {`<br/>`    pr.types.add(PullRequestType.opened);`<br/>`    pr.types.add(PullRequestType.synchronize);`<br/>`}))` |

**Available PullRequestType values:**
`opened`, `synchronize`, `reopened`, `closed`, `assigned`, `unassigned`, `review_requested`, `review_request_removed`, `labeled`, `unlabeled`,
`ready_for_review`, `converted_to_draft`

### Schedule Trigger

| YAML                                    | Typeflows Kotlin                                                    |
|-----------------------------------------|---------------------------------------------------------------------|
| `schedule:`<br/>`- cron: "0 9 * * 1-5"` | `on += Schedule {`<br/>`    cron += Cron.of("0 9 * * 1-5")`<br/>`}` |

### Workflow Dispatch

| YAML                                                                                 | Typeflows Kotlin                                                                                 |
|--------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------|
| `workflow_dispatch: {}`                                                              | `on += WorkflowDispatch()`                                                                       |
| `workflow_dispatch:`<br/>`  inputs:`<br/>`    environment:`<br/>`      type: string` | `on += WorkflowDispatch {`<br/>`    inputs += Input.string("environment", "Target env")`<br/>`}` |

### Simple Event Triggers (No Configuration)

| YAML                    | Typeflows Kotlin         |
|-------------------------|--------------------------|
| `create: {}`            | `on += Create`           |
| `delete: {}`            | `on += Delete`           |
| `fork: {}`              | `on += Fork`             |
| `gollum: {}`            | `on += Gollum`           |
| `page_build: {}`        | `on += PageBuild`        |
| `public: {}`            | `on += Public`           |
| `status: {}`            | `on += Status`           |
| `deployment: {}`        | `on += Deployment`       |
| `deployment_status: {}` | `on += DeploymentStatus` |
| `merge_group: {}`       | `on += MergeGroup`       |

### Event Triggers with Types

| YAML                                               | Typeflows Kotlin                                                                                           | Available Types                                                                       |
|----------------------------------------------------|------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------|
| `issues:`<br/>`  types: [opened, closed]`          | `on += Issues {`<br/>`    types += IssueType.opened`<br/>`    types += IssueType.closed`<br/>`}`           | `opened`, `edited`, `closed`, `labeled`, `unlabeled`                                  |
| `issue_comment:`<br/>`  types: [created]`          | `on += IssueComment {`<br/>`    types += IssueCommentType.created`<br/>`}`                                 | `created`, `edited`, `deleted`                                                        |
| `release:`<br/>`  types: [published]`              | `on += Release {`<br/>`    types += ReleaseType.published`<br/>`}`                                         | `published`, `unpublished`, `created`, `edited`, `deleted`, `prereleased`, `released` |
| `check_run:`<br/>`  types: [completed]`            | `on += CheckRun {`<br/>`    types += CheckRunType.completed`<br/>`}`                                       | `created`, `completed`, `rerequested`, `requested_action`                             |
| `check_suite:`<br/>`  types: [completed]`          | `on += CheckSuite {`<br/>`    types += CheckSuiteType.completed`<br/>`}`                                   | `completed`, `requested`, `rerequested`                                               |
| `label:`<br/>`  types: [created]`                  | `on += Label {`<br/>`    types += LabelType.created`<br/>`}`                                               | `created`, `edited`, `deleted`                                                        |
| `milestone:`<br/>`  types: [created]`              | `on += Milestone {`<br/>`    types += MilestoneType.created`<br/>`}`                                       | `created`, `closed`, `opened`, `edited`, `deleted`                                    |
| `watch:`<br/>`  types: [started]`                  | `on += Watch {`<br/>`    types += WatchType.started`<br/>`}`                                               | `started`                                                                             |
| `repository_dispatch:`<br/>`  types: [deploy]`     | `on += RepositoryDispatch {`<br/>`    types += "deploy-production"`<br/>`}`                                | Custom string types                                                                   |
| `workflow_run:`<br/>`  types: [completed]`         | `on += WorkflowRun {`<br/>`    types += WorkflowRunType.completed`<br/>`    workflows += "ci.yml"`<br/>`}` | `completed`, `requested`                                                              |
| `branch_protection_rule:`<br/>`  types: [created]` | `on += BranchProtectionRule {`<br/>`    types += BranchProtectionRuleType.created`<br/>`}`                 | `created`, `edited`, `deleted`                                                        |

### Discussion and Review Triggers

| YAML                                                    | Typeflows Kotlin                                                                                   | Available Types                                     |
|---------------------------------------------------------|----------------------------------------------------------------------------------------------------|-----------------------------------------------------|
| `discussion:`<br/>`  types: [created]`                  | `on += Discussion {`<br/>`    types += DiscussionType.created`<br/>`}`                             | `created`, `edited`, `answered`, `category_changed` |
| `discussion_comment:`<br/>`  types: [created]`          | `on += DiscussionComment {`<br/>`    types += DiscussionCommentType.created`<br/>`}`               | `created`, `edited`, `deleted`                      |
| `pull_request_review:`<br/>`  types: [submitted]`       | `on += PullRequestReview {`<br/>`    types += PullRequestReviewType.submitted`<br/>`}`             | `submitted`, `edited`, `dismissed`                  |
| `pull_request_review_comment:`<br/>`  types: [created]` | `on += PullRequestReviewComment {`<br/>`    types += PullRequestReviewCommentType.created`<br/>`}` | `created`, `edited`, `deleted`                      |
| `registry_package:`<br/>`  types: [published]`          | `on += RegistryPackage {`<br/>`    types += RegistryPackageType.published`<br/>`}`                 | `published`, `updated`                              |

## Job Configuration

### Basic Job Structure

| YAML                                                   | Typeflows Kotlin                     |
|--------------------------------------------------------|--------------------------------------|
| `jobs:`<br/>`  test:`<br/>`    runs-on: ubuntu-latest` | `jobs += Job("test", UBUNTU_LATEST)` |

**Available RunsOn constants:**
`UBUNTU_LATEST`, `WINDOWS_LATEST`, `MACOS_LATEST`, `UBUNTU_22_04`, `UBUNTU_20_04`, `WINDOWS_2022`, `WINDOWS_2019`, `MACOS_13`, `MACOS_12`

### Job Properties

| YAML                          | Typeflows Kotlin           |
|-------------------------------|----------------------------|
| `if: always()`                | `condition = "always()"`   |
| `needs: [setup]`              | `needs += jobs.first()`    |
| `timeout-minutes: 30`         | `timeoutMinutes = 30`      |
| `continue-on-error: true`     | `continueOnError = true`   |
| `env:`<br/>`  NODE_ENV: test` | `env["NODE_ENV"] = "test"` |

### Container Configuration

| YAML                                                                                            | Typeflows Kotlin                                                                                                                                                         |
|-------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `container:`<br/>`  image: node:18`                                                             | `container = Container("node:18")`                                                                                                                                       |
| `container:`<br/>`  image: node:18`<br/>`  env:`<br/>`    NODE_ENV: prod`<br/>`  ports: [3000]` | `container = Container("node:18") {`<br/>`    env["NODE_ENV"] = "prod"`<br/>`    ports += 3000L`<br/>`    volumes += "/tmp:/tmp"`<br/>`    options = "--cpus 2"`<br/>`}` |

### Strategy Matrix

| YAML                                                                      | Typeflows Kotlin                                                                                   |
|---------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------|
| `strategy:`<br/>`  matrix:`<br/>`    os: [ubuntu-latest, windows-latest]` | `strategy = Strategy {`<br/>`    matrix["os"] = listOf("ubuntu-latest", "windows-latest")`<br/>`}` |
| `strategy:`<br/>`  fail-fast: false`<br/>`  max-parallel: 3`              | `strategy = Strategy {`<br/>`    failFast = false`<br/>`    maxParallel = 3`<br/>`}`               |

## Step Mappings

### Run Command Steps

| YAML                                                          | Typeflows Kotlin                                                                  |
|---------------------------------------------------------------|-----------------------------------------------------------------------------------|
| `- run: echo 'Hello'`                                         | `steps += RunCommand("echo 'Hello'")`                                             |
| `- name: Greet`<br/>`  run: echo 'Hello'`<br/>`  shell: bash` | `steps += RunCommand("echo 'Hello'", "Greet") {`<br/>`    shell = "bash"`<br/>`}` |
| `- run: Get-Process`<br/>`  shell: pwsh`                      | `steps += RunCommand("Get-Process") {`<br/>`    shell = "pwsh"`<br/>`}`           |

### Use Action Steps

| YAML                                                                                                | Typeflows Kotlin                                                                                                                                                                                                                                 |
|-----------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `- uses: actions/checkout@v4`                                                                       | `steps += UseAction("actions/checkout@v4")`                                                                                                                                                                                                      |
| `- name: Setup Node`<br/>`  uses: actions/setup-node@v4`<br/>`  with:`<br/>`    node-version: '18'` | `steps += UseAction("actions/setup-node@v4", "Setup Node") {`<br/>`    with["node-version"] = "18"`<br/>`    env["NODE_ENV"] = "production"`<br/>`    id = "setup-node"`<br/>`    condition = "success()"`<br/>`    timeoutMinutes = 10`<br/>`}` |

### Use Local Action Steps

| YAML                                                                                    | Typeflows Kotlin                                                                                                                                            |
|-----------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `- uses: ./.github/actions/setup`                                                       | `steps += UseLocalAction("./.github/actions/setup")`                                                                                                        |
| `- name: Deploy`<br/>`  uses: ./actions/deploy`<br/>`  with:`<br/>`    env: production` | `steps += UseLocalAction("./actions/deploy", "Deploy") {`<br/>`    with["env"] = "production"`<br/>`    workingDirectory = Path.of("./deployment")`<br/>`}` |

### Use Workflow Steps (Reusable Workflows)

| YAML                                                                                  | Typeflows Kotlin                                                                                                                                    |
|---------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------|
| `- uses: ./.github/workflows/reusable.yml`<br/>`  with:`<br/>`    node-version: '18'` | `steps += UseWorkflow("./.github/workflows/reusable.yml") {`<br/>`    with["node-version"] = "18"`<br/>`    env["WORKFLOW_ENV"] = "called"`<br/>`}` |

### Common Step Properties

| YAML                       | Typeflows Kotlin                      |
|----------------------------|---------------------------------------|
| `if: success()`            | `condition = "success()"`             |
| `id: step-id`              | `id = "step-id"`                      |
| `timeout-minutes: 10`      | `timeoutMinutes = 10`                 |
| `continue-on-error: true`  | `continueOnError = true`              |
| `working-directory: ./src` | `workingDirectory = Path.of("./src")` |

## Permissions

### Permission Levels

| YAML                                                               | Typeflows Kotlin                                                                                 |
|--------------------------------------------------------------------|--------------------------------------------------------------------------------------------------|
| `permissions: read-all`                                            | `permissions = Permissions.ReadAll`                                                              |
| `permissions: write-all`                                           | `permissions = Permissions.WriteAll`                                                             |
| `permissions:`<br/>`  contents: read`<br/>`  pull-requests: write` | `permissions = Permissions(`<br/>`    Contents to Read,`<br/>`    PullRequests to Write`<br/>`)` |

**Available Permissions:**
`Actions`, `Attestations`, `Checks`, `Contents`, `Deployments`, `Discussions`, `IdToken`, `Issues`, `Models`, `Packages`, `Pages`, `PullRequests`,
`SecurityEvents`, `Statuses`

**Available Permission Levels:**
`Read`, `Write`, `None`

## Workflow Configuration

### Environment Variables

| YAML                                                   | Typeflows Kotlin                                          |
|--------------------------------------------------------|-----------------------------------------------------------|
| `env:`<br/>`  CI: 'true'`<br/>`  NODE_ENV: production` | `env["CI"] = "true"`<br/>`env["NODE_ENV"] = "production"` |

### Defaults

| YAML                                                                              | Typeflows Kotlin                                                                                                                                         |
|-----------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------|
| `defaults:`<br/>`  run:`<br/>`    shell: bash`<br/>`    working-directory: ./src` | `defaults = Defaults {`<br/>`    run = RunDefaults {`<br/>`        shell = "bash"`<br/>`        workingDirectory = Path.of("./src")`<br/>`    }`<br/>`}` |

### Concurrency

| YAML                       | Typeflows Kotlin              |
|----------------------------|-------------------------------|
| `concurrency: ci-pipeline` | `concurrency = "ci-pipeline"` |

## Input and Secret Types

### Workflow Dispatch Inputs

| YAML Type                  | Typeflows Method                                                   |
|----------------------------|--------------------------------------------------------------------|
| `type: string`             | `Input.string("name", "description")`                              |
| `type: string` (optional)  | `Input.optionalString("name", "description").withDefault("value")` |
| `type: boolean`            | `Input.bool("name", "description")`                                |
| `type: boolean` (optional) | `Input.optionalBool("name", "description").withDefault(true)`      |
| `type: integer`            | `Input.integer("name", "description")`                             |
| `type: integer` (optional) | `Input.optionalInteger("name", "description").withDefault("300")`  |

### Workflow Call Secrets

| YAML                                                                              | Typeflows Kotlin                                                  |
|-----------------------------------------------------------------------------------|-------------------------------------------------------------------|
| `secrets:`<br/>`  token:`<br/>`    description: API token`<br/>`    type: string` | `secrets += Secret.string("token", "API token")`                  |
| `secrets:`<br/>`  api-key:`<br/>`    required: false`<br/>`    type: string`      | `secrets += Secret.optionalString("api-key", "Optional API key")` |
| `secrets: inherit`                                                                | `secrets = Secrets.Inherit`                                       |

### Outputs

| YAML                                                                       | Typeflows Kotlin                                                                           |
|----------------------------------------------------------------------------|--------------------------------------------------------------------------------------------|
| `outputs:`<br/>`  build-id:`<br/>`    value: ${{ jobs.build.outputs.id }}` | `outputs += Output.string("build-id", "Build identifier", "${{ jobs.build.outputs.id }}")` |

## Complete Workflow Example

### Input YAML

```yaml
name: CI/CD Pipeline
on:
  push:
    branches: [main, develop]
    paths: [src/**, **.kt]
  pull_request:
    types: [opened, synchronize]
    branches: [main]
  workflow_dispatch:
    inputs:
      environment:
        description: Target environment
        type: string
      version:
        description: Version to deploy
        required: false
        default: latest
        type: string

env:
  CI: 'true'
  NODE_ENV: production

permissions:
  contents: read
  pull-requests: write

defaults:
  run:
    shell: bash

jobs:
  test:
    runs-on: ubuntu-latest
    if: success()
    timeout-minutes: 15
    container:
      image: node:18
      env:
        NODE_ENV: production
      ports: [3000]
    
    steps:
    - name: Checkout
      uses: actions/checkout@v4
      
    - name: Setup Java
      uses: actions/setup-java@v4
      with:
        distribution: temurin
        java-version: '21'
        
    - name: Build
      run: ./gradlew build
      env:
        GRADLE_OPTS: -Xmx2g
        
    - name: Test
      run: ./gradlew test
```

### Generated Typeflows Kotlin

```kotlin
import io.typeflows.github.workflows.*
import io.typeflows.github.workflows.triggers.*
import io.typeflows.github.workflows.steps.*
import io.typeflows.github.workflows.Permission.*
import io.typeflows.github.workflows.PermissionLevel.*
import io.typeflows.github.workflows.RunsOn.Companion.*

val workflow = Workflow("CI/CD Pipeline") {
    on += Push {
        branches = Branches.Only("main", "develop")
        paths = Paths.Only("src/**", "**.kt")
    }
    
    on += PullRequest {
        types += PullRequestType.opened
        types += PullRequestType.synchronize
        branches = Branches.Only("main")
    }
    
    on += WorkflowDispatch {
        inputs += Input.string("environment", "Target environment")
        inputs += Input.optionalString("version", "Version to deploy").withDefault("latest")
    }
    
    env["CI"] = "true"
    env["NODE_ENV"] = "production"
    
    permissions = Permissions(
        Contents to Read,
        PullRequests to Write
    )
    
    defaults = Defaults {
        run = RunDefaults {
            shell = "bash"
        }
    }
    
    jobs += Job("test", UBUNTU_LATEST) {
        condition = "success()"
        timeoutMinutes = 15
        
        container = Container("node:18") {
            env["NODE_ENV"] = "production"
            ports += 3000L
        }
        
        steps += UseAction("actions/checkout@v4", "Checkout")
        
        steps += UseAction("actions/setup-java@v4", "Setup Java") {
            with["distribution"] = "temurin"
            with["java-version"] = "21"
        }
        
        steps += RunCommand("./gradlew build", "Build") {
            env["GRADLE_OPTS"] = "-Xmx2g"
        }
        
        steps += RunCommand("./gradlew test", "Test")
    }
}
```

### Generated Typeflows Java

```java
import io.typeflows.github.workflows.*;
import io.typeflows.github.workflows.triggers.*;
import io.typeflows.github.workflows.steps.*;

import static io.typeflows.github.workflows.Permission.*;
import static io.typeflows.github.workflows.PermissionLevel.*;
import static io.typeflows.github.workflows.RunsOn.*;

import java.util.HashMap;

public class CICDPipeline implements WorkflowBuilder {
    @Override
    public Workflow toWorkflow() {
        return Workflow.configure("CI/CD Pipeline", workflow -> {
            // Triggers
            workflow.on.add(Push.configure(push -> {
                push.branches = Branches.Only("main", "develop");
                push.paths = Paths.Only("src/**", "**.kt");
            }));

            workflow.on.add(PullRequest.configure(pr -> {
                pr.types.add(PullRequestType.opened);
                pr.types.add(PullRequestType.synchronize);
                pr.branches = Branches.Only("main");
            }));

            workflow.on.add(WorkflowDispatch.configure(wd -> {
                wd.inputs.add(Input.string("environment", "Target environment"));
                wd.inputs.add(Input.optionalString("version", "Version to deploy").withDefault("latest"));
            }));

            // Environment variables
            workflow.env.put("CI", "true");
            workflow.env.put("NODE_ENV", "production");

            // Permissions
            workflow.permissions = Permissions.configure(new HashMap<>() {{
                put(Contents, Read);
                put(PullRequests, Write);
            }});

            // Defaults
            workflow.defaults = Defaults.configure(defaults -> {
                defaults.run = RunDefaults.configure(run -> {
                    run.shell = "bash";
                });
            });

            // Jobs
            workflow.jobs.add(Job.configure("test", UBUNTU_LATEST, job -> {
                job.condition = "success()";
                job.timeoutMinutes = 15;

                job.container = Container.configure("node:18", container -> {
                    container.env.put("NODE_ENV", "production");
                    container.ports.add(3000L);
                });

                job.steps.add(UseAction.configure("actions/checkout@v4", "Checkout"));

                job.steps.add(UseAction.configure("actions/setup-java@v4", "Setup Java", step -> {
                    step.with.put("distribution", "temurin");
                    step.with.put("java-version", "21");
                }));

                job.steps.add(RunCommand.configure("./gradlew build", "Build", step -> {
                    step.env.put("GRADLE_OPTS", "-Xmx2g");
                }));

                job.steps.add(RunCommand.configure("./gradlew test", "Test"));
            }));
        });
    }
}
```

## Expression Syntax

GitHub Actions expressions using `${{ }}` syntax work the same in Typeflows:

| Context               | Example Usage                          |
|-----------------------|----------------------------------------|
| GitHub context        | `"${{ github.sha }}"`                  |
| Environment variables | `"${{ env.NODE_ENV }}"`                |
| Job context           | `"${{ job.status }}"`                  |
| Steps context         | `"${{ steps.build.outputs.version }}"` |
| Runner context        | `"${{ runner.os }}"`                   |
| Secrets context       | `"${{ secrets.GITHUB_TOKEN }}"`        |
| Inputs context        | `"${{ inputs.environment }}"`          |
| Matrix context        | `"${{ matrix.os }}"`                   |

## Code Generation Instructions

**CRITICAL**: The LLM must follow these step-by-step instructions to generate actual working code files:

### Step 1: Default Behavior - Scan All .github Content

Unless the user specifies otherwise, automatically scan and import ALL workflows and actions:

```bash
# Scan for workflow files
find .github/workflows -name "*.yml" -o -name "*.yaml"

# Scan for action files  
find .github/actions -name "action.yml" -o -name "action.yaml"
```

### Step 2: Language Selection Process

1. Ask user for language preference (Kotlin or Java)
2. If not specified, provide examples in BOTH languages
3. Create appropriate directory structure based on selection

### Step 3: Directory Structure Creation

```bash
# For Kotlin workflows and actions
mkdir -p src/typeflows/kotlin/workflows
mkdir -p src/typeflows/kotlin/actions

# For Java workflows and actions  
mkdir -p src/typeflows/java/workflows
mkdir -p src/typeflows/java/actions
```

### Step 4: File Naming Convention

Convert YAML file names to PascalCase class names:

- `build-and-test.yml` → `BuildAndTest.kt` / `BuildAndTest.java`
- `update-dependencies.yml` → `UpdateDependencies.kt` / `UpdateDependencies.java`
- `deploy-to-prod.yml` → `DeployToProd.kt` / `DeployToProd.java`

### Step 5: Generate Complete Working Class Files

**Kotlin Workflow Template:**

```kotlin
import io.typeflows.github.WorkflowBuilder
import io.typeflows.github.workflows.Workflow
// ... additional imports based on usage

class WorkflowName : WorkflowBuilder {
    override fun toWorkflow() = Workflow("Workflow Display Name") {
        // Converted YAML content here using Kotlin DSL patterns
    }
}
```

**Java Workflow Template:**

```java
import io.typeflows.github.workflows.WorkflowBuilder;
import io.typeflows.github.workflows.Workflow;
import org.jetbrains.annotations.NotNull;
// ... additional imports based on usage

public class WorkflowName implements WorkflowBuilder {
    @NotNull
    @Override
    public Workflow toWorkflow() {
        return Workflow.configure("Workflow Display Name", workflow -> {
            // Converted YAML content here using Java builder patterns
        });
    }
}
```

### Step 6: Action Class Generation

Process `action.yml` files in `.github/actions/*/` directories following the same patterns:

**Kotlin Action Template:**

```kotlin
import io.typeflows.github.workflows.Action
// ... imports

val action = Action("Action Name", "Description") {
    // Converted action.yml content
}
```

**Java Action Template:**

```java
import io.typeflows.github.workflows.Action;
// ... imports

public class ActionName {
    public static Action create() {
        return Action.configure("Action Name", "Description", action -> {
            // Converted action.yml content
        });
    }
}
```

### Step 7: File Creation Process

1. **Create actual files** in the correct directories
2. **Write complete, compilable code** (not just examples)
3. **Include all necessary imports** based on YAML content
4. **Use correct language patterns** (DSL for Kotlin, builders for Java)
5. **Test compilation** to ensure correctness

## Validation Commands

After converting your YAML to Typeflows code, validate your code:

### For Kotlin
```bash
# Compile to check syntax
./gradlew compileKotlin

# Generate YAML to verify output
./gradlew typeflowsExport

# Run tests if available
./gradlew test
```

### For Java

```bash
# Compile to check syntax
./gradlew compileJava

# Generate YAML to verify output
./gradlew typeflowsExport

# Run tests if available
./gradlew test
```

## Common Issues and Solutions

### Issue: Import not found

**Solution:** Ensure all required imports are added to your file. Check the "Required Imports" section above.

### Issue: Type mismatch on runners

**Solution:** Use the RunsOn companion object constants: `UBUNTU_LATEST`, `WINDOWS_LATEST`, etc.

### Issue: Unknown trigger type

**Solution:** Check if the trigger requires configuration vs. being a simple object. Simple triggers use object references (e.g., `Create`, `Fork`), while
configurable triggers use constructor calls (e.g., `Push()`, `Issues()`).

### Issue: Enum values not found

**Solution:** Import the correct enum types and use the exact case-sensitive values from the mappings above.

### Issue: Container ports configuration

**Solution:** Use `Long` values for ports: `ports += 3000L` not `ports += 3000`

### Issue: Path handling

**Solution:** Use `Path.of("./directory")` for working directories and local action paths.

## API Version Compatibility

This command is compatible with:

- Typeflows GitHub module version: Current
- GitHub Actions: All standard workflow syntax
- Gradle: 8.0+
- Kotlin: 1.8+
- Java: 17+

## Troubleshooting Guide

1. **Compilation errors:** Check all imports are present and correct
2. **Type safety issues:** Verify enum values match exactly
3. **YAML generation problems:** Run `./gradlew typeflowsExport` to see generated output
4. **Missing API features:** Check test files in `github/src/test` for additional examples
5. **Container configuration:** Ensure ports use Long type (`3000L`) and proper syntax
6. **Path issues:** Use `Path.of()` for file system paths

## Maintenance Instructions

To update these mappings when Typeflows changes:

1. Run existing tests: `./gradlew :typeflows-github:test`
2. Check for new test files in `github/src/test/kotlin/io/typeflows/github/workflow/`
3. Examine approved outputs in `github/src/test/resources/` for new YAML patterns
4. Update mappings based on new API discoveries
5. Validate all examples still compile and generate correct YAML
6. Test with approval tests using `approve` command if output changes are expected


## Usage in Copilot
These instructions help GitHub Copilot understand how to work with Typeflows for GitHub Actions workflow generation.
