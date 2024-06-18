import com.diffplug.gradle.spotless.SpotlessExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.androidTest) apply false
    alias(libs.plugins.androidx.baselineprofile) apply false
    alias(libs.plugins.com.diffplug.spotless) apply false
    alias(libs.plugins.compose) apply false
}

subprojects {
    apply(plugin = "com.diffplug.spotless")
    extensions.configure<SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            ktlint(libs.ktlint.get().version)
                .setEditorConfigPath("${project.rootDir}/config/.editorconfig")
            licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
        }
    }
}
