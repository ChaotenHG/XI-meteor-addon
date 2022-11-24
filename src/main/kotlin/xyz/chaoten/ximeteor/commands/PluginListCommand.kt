package xyz.chaoten.ximeteor.commands

import com.mojang.brigadier.Command.SINGLE_SUCCESS
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import meteordevelopment.meteorclient.MeteorClient
import meteordevelopment.meteorclient.systems.commands.Command
import meteordevelopment.meteorclient.utils.player.ChatUtils
import net.minecraft.command.CommandSource
import xyz.chaoten.ximeteor.plugins.PluginManager

object PluginListCommand : Command("pluginlist", "Reload all or a specific Xi-plugin.", "pl", "plugin") {

    override fun build(builder: LiteralArgumentBuilder<CommandSource>) {
        builder.executes {

            PluginManager.plugins.forEach {
                ChatUtils.info("Xi","${it.name} - ${it.authors.joinToString()}")
            }

            SINGLE_SUCCESS
        }
    }
}
