plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.version.catalog.update)
    alias(libs.plugins.typeflows)
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    typeflowsApi(libs.typeflows.github)
    typeflowsApi(libs.typeflows.github.marketplace)
    typeflowsApi(libs.typeflows.github.project.standards)
}

