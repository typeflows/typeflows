# Security Policy

## Supported Versions

Use this section to tell people about which versions of your project are
currently being supported with security updates.

| SDK | Version | Supported          |
| --- | ------- | ------------------ |
| JVM | 0.x.x   | :white_check_mark: |

## Reporting a Vulnerability

We take security vulnerabilities seriously and appreciate your efforts to responsibly disclose them.

### How to Report

**Please do not report security vulnerabilities through public GitHub issues.**

Instead, please use one of the following methods:

1. **GitHub Security Advisories** (preferred): Go to the [Security tab](https://github.com/typeflows/typeflows/security/advisories) of our repository and click "Report a vulnerability"

2. **Email**: Send an email to security@typeflows.io with:
   - A clear description of the vulnerability
   - Steps to reproduce the issue
   - Potential impact assessment
   - Any suggested fixes (if available)

### What to Expect

- **Acknowledgement**: We will acknowledge receipt of your report within 48 hours
- **Initial Assessment**: We will provide an initial assessment within 5 business days
- **Updates**: You can expect regular updates on our progress at least every 7 days
- **Resolution**: We aim to resolve critical vulnerabilities within 30 days

### Disclosure Timeline

- **Accepted vulnerabilities**: We will work with you to coordinate disclosure after a fix is available
- **Declined reports**: We will explain our reasoning and may suggest alternative reporting channels if appropriate
- **Public disclosure**: Security advisories will be published after fixes are deployed

### Scope

This security policy applies to:

- The core TypeFlows SDK and libraries for all languages published through organisational accounts:
   - JVM: Maven Central group: `io.typeflows`
   - Python: PyPI organisation: `typeflows`
   - Typescript: NPM organisation: `typeflows`
- Official TypeFlows CLI tools
- The documentation website ([https://typeflows.io])

### Recognition

We believe in recognising security researchers who help make TypeFlows more secure:

- Researchers who report valid vulnerabilities will be credited in our security advisories (with their permission)
- We maintain a [Hall of Fame](https://github.com/typeflows/typeflows/security/advisories) for contributors

### Bug Bounty

Currently, we do not offer a paid bug bounty programme. However, we deeply appreciate responsible disclosure and will acknowledge your contribution publicly.

## Security Best Practices

When using TypeFlows:

1. **Keep Updated**: Always use the latest version of TypeFlows
2. **Review Generated Code**: Inspect generated workflows and repository contents before merging into main repository branches
3. **Secrets Management**: Use GitHub secrets appropriately - never hardcode sensitive values
4. **Permissions**: Follow the principle of least privilege for workflow permissions
5. **Dependencies**: Regularly audit and update your dependencies

## Questions?

If you have questions about this security policy, please contact us at security@typeflows.io.
