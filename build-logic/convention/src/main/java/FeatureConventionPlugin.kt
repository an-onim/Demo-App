
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension


class FeatureConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {

            pluginManager.apply(libs().getPlugin("android-library"))
            pluginManager.apply(libs().getPlugin("compose-compiler"))

            extensions.configure<LibraryExtension> {
                compileSdk = 36

                defaultConfig {
                    minSdk = 30
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                    // add setting only for modules with consumer-rules.pro
                    if (file("consumer-rules.pro").exists()) {
                        consumerProguardFiles("consumer-rules.pro")
                    }

                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }

                buildTypes {
                    release {
                        isMinifyEnabled = true

                        // add setting only for modules with proguard-rules.pro
                        if (file("proguard-rules.pro").exists()) {
                            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                        }
                    }
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }

                buildFeatures {
                    compose = true
                }

            }

            extensions.configure<KotlinAndroidProjectExtension> {
                compilerOptions { jvmTarget.set(JvmTarget.JVM_11) }
            }

            dependencies {
                add("implementation", platform(libs().getLibrary("androidx-compose-bom")))
                add("implementation", libs().getLibrary("androidx-core-ktx"))
            }

        }
    }

}