import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.vanniktechMavenPublish)
    alias(libs.plugins.dokka)
}

group = providers.gradleProperty("GROUP").get()
version = providers.gradleProperty("VERSION_NAME").get()

kotlin {
    androidTarget {
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    jvm()

    js {
        outputModuleName = "shared"
        browser()
        binaries.library()
        generateTypeScriptDefinitions()
        compilerOptions {
            target = "es2015"
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.browser)
        }
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.coroutines.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.ktor.client.mock)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)
    signAllPublications()

    pom {
        name.set(providers.gradleProperty("POM_NAME"))
        description.set(providers.gradleProperty("POM_DESCRIPTION"))
        inceptionYear.set(providers.gradleProperty("POM_INCEPTION_YEAR"))
        url.set(providers.gradleProperty("POM_URL"))

        licenses {
            license {
                name.set(providers.gradleProperty("POM_LICENCE_NAME"))
                url.set(providers.gradleProperty("POM_LICENCE_URL"))
                distribution.set(providers.gradleProperty("POM_LICENCE_DIST"))
            }
        }
        developers {
            developer {
                id.set(providers.gradleProperty("POM_DEVELOPER_ID"))
                name.set(providers.gradleProperty("POM_DEVELOPER_NAME"))
                url.set(providers.gradleProperty("POM_DEVELOPER_URL"))
            }
        }
        scm {
            url.set(providers.gradleProperty("POM_SCM_URL"))
            connection.set(providers.gradleProperty("POM_SCM_CONNECTION"))
            developerConnection.set(providers.gradleProperty("POM_SCM_DEV_CONNECTION"))
        }
    }
}

android {
    namespace = "com.nubasu.spotify.webapi.wrapper.shared"
    compileSdk =
        libs.versions.android.compileSdk
            .get()
            .toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk =
            libs.versions.android.minSdk
                .get()
                .toInt()
    }
}

tasks.register("generateApiDocs") {
    group = "documentation"
    description = "Generates HTML API docs for all shared public APIs."
    dependsOn("dokkaHtml")
}

tasks.register("generateJavadoc") {
    group = "documentation"
    description = "Generates Javadoc-style API docs for all shared public APIs."
    dependsOn("dokkaJavadoc")
}
