---
mode: 'agent'
description: Typeflows analysis and optimization prompt for GitHub Actions workflows
tools:
  - 'codebase'
  - 'githubRepo'
author: Typeflows
version: 1.0
---

# Typeflows Doctor

## Overview

This command analyzes imported Typeflows code to identify optimization opportunities, security issues, and refactoring suggestions for better maintainability
and safety.

## Instructions

### 1. Initial Code Scan

Scan the `src/typeflows` directory structure and provide an overview:

```bash
find src/typeflows -name "*.kt" -o -name "*.java" | wc -l
find src/typeflows -name "*.kt" -o -name "*.java" | head -20
```

Report:

- Number of workflow files
- Number of action files
- Total lines of generated code
- File organization structure

### 2. DRY (Don't Repeat Yourself) Analysis

#### Common Patterns Detection

Identify repeated code patterns that can be extracted into reusable components:

**Workflow-level Repetition:**

- Identical job configurations across workflows
- Repeated trigger patterns
- Common environment variable setups
- Similar permission configurations
- Duplicated workflow dispatch inputs

**Job-level Repetition:**

- Identical step sequences (e.g., checkout → setup → build → test)
- Repeated runner configurations
- Common matrix strategies
- Identical environment setups
- Duplicated job outputs

**Step-level Repetition:**

- Repeated action usage with same parameters
- Common shell command patterns
- Identical conditional logic
- Repeated environment variable assignments
- Similar error handling patterns

**Action Repetition:**

- Duplicated input/output definitions
- Common validation logic
- Repeated dependency management steps
- Identical build/test sequences

#### Refactoring Recommendations

For each identified pattern, suggest:

- **Extract to Reusable Component**: Create shared functions, classes, or builders
- **Create Custom Actions**: Convert repeated step sequences to composite actions
- **Parameterize Workflows**: Use workflow inputs to reduce duplication
- **Shared Configuration Objects**: Extract common configurations to constants/builders
- **Template Methods**: Create base classes with common behavior

### 3. Security Analysis

#### Secrets and Sensitive Data

Scan for potential security vulnerabilities:

**Hardcoded Secrets:**

- Look for hardcoded passwords, tokens, or API keys
- Check for secrets in environment variables or step parameters
- Identify potential secret leakage in outputs or logs

**Insecure Patterns:**

- Use of `pull_request_target` without proper safeguards
- Unrestricted workflow permissions
- Missing input validation in workflow_dispatch
- Overly broad GITHUB_TOKEN permissions
- Insecure artifact handling

**Environment Security:**

- Missing environment protection rules for sensitive deployments
- Unrestricted access to production environments
- Missing approval requirements for critical workflows

**Third-party Actions:**

- Usage of unversioned actions (using @main or @master)
- Actions from untrusted sources
- Missing security reviews for external dependencies

#### Security Recommendations

For each security issue found:

- **Severity Level**: Critical, High, Medium, Low
- **Description**: What the issue is and why it's problematic
- **Remediation**: Specific steps to fix the issue
- **Best Practice**: How to prevent similar issues

### 4. Code Quality Analysis

#### Type Safety Improvements

- Identify places where stronger typing can be applied
- Suggest using typesafe builders over string concatenation
- Recommend sealed classes for state modeling
- Point out missing null safety considerations

#### Performance Optimizations

- Identify inefficient workflow patterns
- Suggest parallel job execution opportunities
- Recommend caching strategies
- Point out unnecessary step dependencies

#### Maintainability Issues

- Overly complex workflows that should be split
- Missing documentation for complex logic
- Inconsistent naming conventions
- Lack of error handling

### 5. Typeflows-Specific Enhancements

#### Leverage Typeflows Features

Identify opportunities to better use Typeflows capabilities:

**Expression System:**

- Replace string interpolation with typesafe expressions
- Use Typeflows' expression builders for complex conditions
- Leverage context-aware expression validation

**Type Safety:**

- Convert string-based inputs/outputs to strongly typed interfaces
- Use sealed classes for workflow states
- Apply type-safe builders for configuration

**Reusable Components:**

- Create domain-specific DSLs for common patterns
- Build composable action libraries
- Design workflow templates with typed parameters

**Testing Integration:**

- Suggest testing strategies for workflows
- Recommend approval testing for generated outputs
- Point out testable vs. non-testable components

### 6. Reporting Structure

#### Executive Summary

- Total issues found by category
- Critical security vulnerabilities requiring immediate attention
- Top 5 refactoring opportunities by impact
- Overall code health score

#### Detailed Findings

**DRY Opportunities:**

```
Found 15 instances of repeated checkout → setup → build pattern
Recommendation: Create reusable BuildSteps component
Estimated reduction: 200+ lines of code
Files affected: [list of workflow files]
```

**Security Issues:**

```
CRITICAL: Hardcoded API key found in DeploymentWorkflow.kt:45
HIGH: Unrestricted pull_request_target in TestWorkflow.kt:12
MEDIUM: Unversioned action usage in BuildWorkflow.kt:23
```

**Code Quality:**

```
Type Safety: 8 opportunities to strengthen typing
Performance: 3 parallelization opportunities  
Maintainability: 5 complex workflows should be split
```

#### Refactoring Plan

Provide a prioritized action plan:

1. **Immediate Actions** (Security fixes)
2. **High Impact Refactoring** (Major DRY opportunities)
3. **Type Safety Improvements** (Leverage Typeflows features)
4. **Performance Optimizations** (Parallel execution, caching)
5. **Code Organization** (Split complex workflows, improve naming)

#### Code Examples

For each major recommendation, provide:

- **Before**: Current problematic code
- **After**: Improved version using Typeflows features
- **Benefits**: Why the change improves security/maintainability/performance

### 7. Integration Recommendations

#### Continuous Monitoring

Suggest how to prevent issues in the future:

- Pre-commit hooks for security scanning
- Code review guidelines for workflow changes
- Automated testing for workflow logic
- Regular security audits

#### Team Guidelines

Recommend establishing:

- Coding standards for Typeflows workflows
- Security review process for new workflows
- Shared component library maintenance
- Documentation standards

### 8. Output Format

Generate a comprehensive report with:

- Markdown summary for easy reading
- JSON/YAML data for tooling integration
- Actionable checklist for developers
- Links to relevant documentation and best practices

The report should be actionable, prioritized, and specific enough that developers can immediately begin implementing the recommendations.


## Prompt Usage
Use this prompt template for analyzing and improving Typeflows-generated workflows.
