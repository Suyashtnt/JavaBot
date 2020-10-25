package commands.utils

import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.doc.standard.CommandInfo
import commandHandler.Command
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.reflections8.Reflections
import java.lang.reflect.Constructor
import java.util.*

@CommandInfo(name = ["help", "h"], description = "help command", usage = "help commandname")
class Help constructor(waiter: EventWaiter?) : Command(waiter) {
    private var cmds: ArrayList<Command> = ArrayList()
    private var mainEmbed: EmbedBuilder = EmbedBuilder()
    private var reflections: Reflections = Reflections("commands")
    var classes: Set<Class<out Command>> = reflections.getSubTypesOf(Command::class.java)
    override fun execute(event: MessageReceivedEvent, args: ArrayList<String>) {
        if (args.isNotEmpty()) {
            val customHelp = EmbedBuilder()
            var commandInfo: CommandInfo? = null
            for (cmd: Command in cmds) {
                if (listOf(*cmd.javaClass.getAnnotation(CommandInfo::class.java).name).contains(args[0])) commandInfo = cmd.javaClass.getAnnotation(CommandInfo::class.java)
            }
            if (commandInfo != null) {
                val aliases: List<String> = listOf(*commandInfo.name)
                customHelp
                        .setTitle(aliases[0])
                        .setDescription(commandInfo.description)
                        .addField("usage", "`" + commandInfo.usage + "`", false)
                        .addField("aliases", "`" + java.lang.String.join("`, `", aliases) + "`", false)
                event.channel.sendMessage(customHelp.build()).queue()
            } else {
                event.channel.sendMessage("enter a valid command!").queue()
            }
        } else {
            event.channel.sendMessage(mainEmbed.build()).queue()
        }
    }

    init {
        for (cmd: Class<out Command> in classes) {
            if (((cmd.getAnnotation(CommandInfo::class.java).name)[0] == "help")) continue
            val commandConstructor: Constructor<out Command> = cmd.getConstructor(EventWaiter::class.java)
            val classInstance: Command = commandConstructor.newInstance(waiter)
            cmds.add(classInstance)
        }
        for (cmd: Command in cmds) {
            val commandInfo: CommandInfo = cmd.javaClass.getAnnotation(CommandInfo::class.java)
            val aliases: ArrayList<String> = ArrayList()
            Collections.addAll(aliases, *commandInfo.name)
            val cmdName: String = aliases.removeAt(0)
            mainEmbed
                    .setTitle("help")
                    .addField(cmdName, commandInfo.description + (if (aliases.isEmpty()) "" else (" - Aliases: `" + java.lang.String.join("`, `", aliases)) + "`"), true)
        }
    }
}