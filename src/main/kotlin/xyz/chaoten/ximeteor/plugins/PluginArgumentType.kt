package xyz.chaoten.ximeteor.plugins

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import java.util.concurrent.CompletableFuture

class PluginArgumentType : ArgumentType<XiPlugin> {

    override fun parse(reader: StringReader): XiPlugin {
        val pluginName = reader.readUnquotedString()
        return PluginManager.plugins.first { it.name.equals(pluginName, true) }
    }

    override fun <S : Any?> listSuggestions(
        context: CommandContext<S>?,
        builder: SuggestionsBuilder?
    ): CompletableFuture<Suggestions> {
        PluginManager.plugins.forEach { xiPlugin ->
            builder?.suggest(xiPlugin.name)
        }

        return builder?.buildFuture()!!
    }
}
