import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.compile.JavaCompile

plugins {
    id("com.android.application")
}

android {
    namespace = "dev.ultrareview.fixture"
    compileSdk = 36

    defaultConfig {
        applicationId = "dev.ultrareview.fixture"
        minSdk = 23
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }
}

val fixtureClasses = layout.buildDirectory.dir("fixture-check/classes")

val compileFixtureChecks by tasks.registering(JavaCompile::class) {
    source = fileTree("src/main/java") { include("**/*.java") }
    classpath = files()
    destinationDirectory.set(fixtureClasses)
    options.release.set(17)
}

tasks.register<JavaExec>("fixtureBaselineCheck") {
    dependsOn(compileFixtureChecks)
    classpath = files(fixtureClasses)
    mainClass.set(providers.gradleProperty("fixtureMainClass"))
    args("baseline")
}

tasks.register<JavaExec>("fixtureExcellenceCheck") {
    dependsOn(compileFixtureChecks)
    classpath = files(fixtureClasses)
    mainClass.set(providers.gradleProperty("fixtureMainClass"))
    args("excellence")
}
