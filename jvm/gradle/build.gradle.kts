plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.version.catalog.update)

    // Typeflows plugin for GitHub
    alias(libs.plugins.typeflows)
    // or... id("io.typeflows") version "VERSION"
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    // Add dependencies needed to build your Workflows
    typeflowsApi(libs.typeflows.github)
    // or... typeflowsApi("io.typeflows:typeflows-github:VERSION")
}

typeflows {
    typeflowsClass = "com.example.Typeflows" // Defaults to "Typeflows"
}
