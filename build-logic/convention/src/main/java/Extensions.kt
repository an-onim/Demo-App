import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

/**
 * Get access to `libs.versions.toml`
 */
internal fun Project.libs() =
    extensions.getByType<VersionCatalogsExtension>().named("libs")

/**
 * @param name name of plugin in `libs.versions.toml`
 * @return plugin id
 */
internal fun VersionCatalog.getPlugin(name: String)
    = this.findPlugin(name).get().get().pluginId

/**
 * @param name name of bundle in `libs.versions.toml`
 * @return provider of the bundle
 */
internal fun VersionCatalog.getBundle(name: String) = this.findBundle(name).get()

/**
 * @param name name of library in `libs.versions.toml`
 * @return library string
 */
internal fun VersionCatalog.getLibrary(name: String) = this.findLibrary(name).get()