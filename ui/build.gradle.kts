plugins {
    alias(libs.plugins.convention.feature.library)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "ru.social.core.ui"
}

dependencies {
    implementation(project(":domain"))
//    implementation(project(":core:navigation"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.bundles.coil)

    ksp(libs.android.hilt.compiler)
    implementation(libs.android.hilt)
    implementation(libs.android.hilt.navigation)

    debugImplementation(libs.bundles.compose.debug)

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.android.test)
}