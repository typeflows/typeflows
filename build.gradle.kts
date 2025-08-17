plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.version.catalog.update)

    // Typeflows plugin for GitHub
    alias(libs.plugins.typeflows.github)
    // or... id("io.typeflows.github") version "VERSION"
}

repositories {
    mavenCentral()
}

dependencies {
    // Add dependencies needed to build your Workflows
    typeflowsApi(libs.typeflows.github)
    // or... typeflowsApi("io.typeflows:typeflows-github:VERSION")
}
