package commands.utils

import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.doc.standard.CommandInfo
import com.mongodb.client.MongoClient
import commandHandler.Command
import dev.minn.jda.ktx.await
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.reflections8.Reflections
import java.lang.reflect.Constructor
import java.util.*

@CommandInfo(name = ["help", "h"], description = "help command", usage = "help commandname")
class Help constructor(waiter: EventWaiter?, mongoClient: MongoClient) : Command(waiter, mongoClient) {
    private var cmds: ArrayList<Command> = ArrayList()
    private var mainEmbed: EmbedBuilder = EmbedBuilder()
    private var reflections: Reflections = Reflections("commands")
    var classes: Set<Class<out Command>> = reflections.getSubTypesOf(Command::class.java)
    override suspend fun execute(event: MessageReceivedEvent, args: ArrayList<String>) {
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
                event.channel.sendMessage(customHelp.build()).await()
            } else {
                event.channel.sendMessage("enter a valid command!").await()
            }
        } else {
            event.channel.sendMessage(mainEmbed.build()).await()
        }
    }

    init {
        for (cmd: Class<out Command> in classes) {
            if (((cmd.getAnnotation(CommandInfo::class.java).name)[0] == "help")) continue
            val commandConstructor: Constructor<out Command> = cmd.getConstructor(EventWaiter::class.java, MongoClient::class.java)
            val classInstance: Command = commandConstructor.newInstance(waiter, mongoClient)
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