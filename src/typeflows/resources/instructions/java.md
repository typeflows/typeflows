# Modern Java Style Guide

## Project Structure

- Use Maven or Gradle as build tools
- Follow standard Maven/Gradle project structure
- Organize packages by feature, not by layer

## Code Style

- Use consistent indentation (4 spaces)
- Follow Java naming conventions
    - Classes: PascalCase (Example: UserService)
    - Methods/Variables: camelCase (Example: getUserById)
    - Constants: UPPER_SNAKE_CASE (Example: MAX_RETRY_COUNT)
- Keep methods focused and small (< 30 lines recommended)
- Use meaningful variable and method names

## Modern Java Features

- Prefer records for DTOs (Java 16+)
- Use text blocks for multiline strings (Java 15+)
- Utilize pattern matching (Java 17+)
- Embrace Optional for null handling
- Use Stream API for collections processing

## Best Practices

- Write immutable classes when possible
- Use constructor injection for dependencies
- Follow SOLID principles
- Add comprehensive documentation
- Include unit tests for all business logic

## Code Organization

- One class per file
- Group related functionality
- Use static imports judiciously
- Keep classes focused (Single Responsibility)

## Error Handling

- Use specific exceptions over generic ones
- Prefer unchecked exceptions
- Include meaningful error messages
- Document exceptions in JavaDoc

## Performance

- Use StringBuilder for string concatenation
- Prefer collection interfaces over implementations
- Consider using primitive collections when appropriate
- Use lazy initialization when needed

## Testing

- Write unit tests using JUnit 5
- Follow AAA pattern (Arrange-Act-Assert)
- Use meaningful test names
- Mock external dependencies

## Tools

- Use an IDE (IntelliJ IDEA, Eclipse, VS Code)
- Implement static code analysis (SonarQube, PMD)
- Apply code formatting (Google Java Style, Spring Style)
- Use version control (Git)

## Dependencies

- Keep dependencies up to date
- Use dependency management
- Minimize external dependencies
- Check for security vulnerabilities
