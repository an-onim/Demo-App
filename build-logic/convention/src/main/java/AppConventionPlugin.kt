import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AppConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {

            pluginManager.apply(libs().getPlugin("android-application"))
            pluginManager.apply(libs().getPlugin("kotlin-ksp"))
            pluginManager.apply(libs().getPlugin("hilt-android"))

            extensions.configure<ApplicationExtension> {
                compileSdk = 36

                defaultConfig {
                    minSdk = 30
                    versionCode = 1
                    versionName = "1.0"

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                buildTypes {

                    release {
                        isDebuggable = false
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

            }

            dependencies {
                add("implementation", libs().getLibrary("androidx-core-ktx"))

                add("ksp", libs().getLibrary("android-hilt-compiler"))
                add("implementation", libs().getLibrary("android-hilt"))
                add("implementation", libs().getLibrary("android-hilt-navigation"))
            }

        }
    }

}