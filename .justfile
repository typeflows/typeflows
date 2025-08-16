default:
    @just --list

check:
    ./gradlew check

versions:
    ./gradlew versionCatalogUpdate

rerun:
    ./gradlew clean check
