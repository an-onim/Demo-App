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
    implementation(project(":core:base"))
    implementation(project(":ui"))
    implementation(project(":data"))
//    implementation(project(":core:navigation"))

    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.bundles.compose)
}