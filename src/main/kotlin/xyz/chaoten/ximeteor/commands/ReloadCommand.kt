package xyz.chaoten.ximeteor.commands

import com.mojang.brigadier.Command.SINGLE_SUCCESS
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import meteordevelopment.meteorclient.systems.commands.Command
import meteordevelopment.meteorclient.utils.player.ChatUtils
import net.minecraft.command.CommandSource
import xyz.chaoten.ximeteor.plugins.PluginArgumentType
import xyz.chaoten.ximeteor.plugins.PluginManager
import xyz.chaoten.ximeteor.plugins.XiPlugin

object ReloadCommand : Command("reload", "Reload all or a specific Xi-plugin.", "rl") {

    override fun build(builder: LiteralArgumentBuilder<CommandSource>) {
        builder.executes {

            PluginManager.reloadAllPlugins()

            ChatUtils.info("XI", "Reloaded all Xi-plugins.")

            SINGLE_SUCCESS
        }

        builder.then(literal("all")
            .executes {
                PluginManager.reloadAllPlugins()

                ChatUtils.info("XI", "Reloaded all Xi-plugins.")

                SINGLE_SUCCESS
            }
        )

        builder.then(literal("plugin")
            .then(
                argument("plugin", PluginArgumentType())
                .executes { command ->

                    val plugin = command.getArgument("plugin", XiPlugin::class.java)
                    PluginManager.reloadPlugin(plugin)

                    SINGLE_SUCCESS
                }
            )
        )
    }
}
