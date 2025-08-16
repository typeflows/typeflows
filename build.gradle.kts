plugins {
    kotlin("jvm") version "2.2.0"
    alias(libs.plugins.version.catalog.update)
    id("io.typeflows.github") version "0.0.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    typeflowsApi("io.typeflows:typeflows-github:0.0.1.0")
}

typeflows {
}
