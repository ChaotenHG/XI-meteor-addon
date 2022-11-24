package xyz.chaoten.ximeteor.plugins

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import meteordevelopment.meteorclient.MeteorClient
import java.io.File
import java.net.URLClassLoader
import java.util.jar.JarFile

@Suppress("MemberVisibilityCanBePrivate")
object PluginManager {

    val plugins = mutableListOf<XiPlugin>()
    val pluginDir = File(MeteorClient.FOLDER, "xi-plugins")
    private val pluginFiles = hashMapOf<XiPlugin, File>()

    init {

        loadPluginsFromFiles()

    }

    //<editor-fold desc="Load Plugins Jar for plugin dir and call loadPlugin() method">
    fun loadPluginsFromFiles() {

        if(!pluginDir.exists()) pluginDir.mkdir()

        pluginDir.listFiles()?.forEach { file ->

            loadPluginFromFile(file)

        }
    }

    fun loadPluginFromFile(file : File) {

        val jarFile = JarFile(file)

        val configFile = jarFile.getJarEntry("plugin.json")
            ?: throw Exception("Plugin ${file.name} does not have a plugin.json file")

        val configJson = JsonParser.parseString(
            jarFile.getInputStream(configFile).readAllBytes()
                .toString(Charsets.UTF_8)
        ).asJsonObject

        loadPlugin(configJson, file)

        jarFile.close()

    }

    //</editor-fold>

    fun loadPlugin(pluginConfig : JsonObject, file : File){

        val classloader = URLClassLoader(arrayOf(file.toURI().toURL()), this.javaClass.classLoader)

        val pluginClass = classloader.loadClass(pluginConfig["main"].asString)

        val plugin = pluginClass.asSubclass(XiPlugin::class.java).getDeclaredConstructor().newInstance()

        val nameField = plugin.javaClass.superclass.getDeclaredField("name")
        val authorsField = plugin.javaClass.superclass.getDeclaredField("authors")

        nameField.isAccessible = true
        authorsField.isAccessible = true

        nameField.set(plugin, pluginConfig["name"].asString)
        authorsField.set(plugin, pluginConfig["authors"].asJsonArray.map { it.asString }.toTypedArray())

        nameField.isAccessible = false
        authorsField.isAccessible = false

        plugins.add(plugin)
        pluginFiles[plugin] = file

        plugin.onEnable()
    }

    fun reloadPlugin(plugin: XiPlugin){

        plugin.onReload()

        val file = pluginFiles[plugin]!!

        if (!file.exists()) {
            MeteorClient.LOG.error("File of \"${plugin.name}\" Plugin does not exist.")
            return
        }

        plugins.remove(plugin)

        loadPluginFromFile(file)

    }

    fun reloadAllPlugins() = plugins.forEach { reloadPlugin(it) }


}
