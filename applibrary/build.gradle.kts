plugins {
    alias(libs.plugins.convention.app.library)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "ru.social.library"

    defaultConfig {
        applicationId = "ru.social.library"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.bundles.compose)
}