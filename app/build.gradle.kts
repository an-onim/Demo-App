plugins {
    alias(libs.plugins.convention.app.library)
    alias(libs.plugins.gms)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "ru.social.demo"

    defaultConfig {
        applicationId = "ru.social.demo"
    }

    buildFeatures {
        compose = true
    }

//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.1"
//    }
//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }
}

dependencies {
    implementation(project(":core:base"))
    implementation(project(":ui"))
    implementation(project(":data"))
//    implementation(project(":core:navigation"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.appcompat)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.runtime.livedata)
}
