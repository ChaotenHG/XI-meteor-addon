package xyz.chaoten.ximeteor

import com.mojang.logging.LogUtils
import meteordevelopment.meteorclient.addons.MeteorAddon
import meteordevelopment.meteorclient.systems.commands.Commands
import meteordevelopment.meteorclient.systems.hud.HudGroup
import meteordevelopment.meteorclient.systems.modules.Category
import meteordevelopment.meteorclient.systems.modules.Modules
import org.slf4j.Logger
import xyz.chaoten.ximeteor.commands.PluginListCommand
import xyz.chaoten.ximeteor.commands.ReloadCommand
import xyz.chaoten.ximeteor.plugins.PluginManager

class XiAddon : MeteorAddon() {

    override fun onInitialize() {

        PluginManager

        // Commands
        Commands.get().add(ReloadCommand)
        Commands.get().add(PluginListCommand)

        LOG.info("XiAddon initialized")

    }

    override fun onRegisterCategories() {
        Modules.registerCategory(CATEGORY_Movement)
        Modules.registerCategory(CATEGORY_Player)
        Modules.registerCategory(CATEGORY_Combat)
        Modules.registerCategory(CATEGORY_Render)
        Modules.registerCategory(CATEGORY_World)
        Modules.registerCategory(CATEGORY_Misc)

    }

    override fun getPackage() = "xyz.chaoten.ximeteor"

    companion object {
        val LOG: Logger = LogUtils.getLogger()

        val CATEGORY_Movement = Category("Xi-Movement")
        val CATEGORY_Render = Category("Xi-Render")
        val CATEGORY_Player = Category("Xi-Player")
        val CATEGORY_Combat = Category("Xi-Combat")
        val CATEGORY_World = Category("Xi-World")
        val CATEGORY_Misc = Category("Xi-Misc")

        val HUD_GROUP = HudGroup("Xi")
    }
}

