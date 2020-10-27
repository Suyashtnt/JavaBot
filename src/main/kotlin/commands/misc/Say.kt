package commands.misc

import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.doc.standard.CommandInfo
import com.mongodb.client.MongoClient
import commandHandler.Command
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.util.*

@CommandInfo(name = ["say"], description = "the bot will say what you type", usage = "say something to say")
class Say constructor(waiter: EventWaiter?, mongoClient: MongoClient) : Command(waiter, mongoClient) {
    override suspend fun execute(event: MessageReceivedEvent, args: ArrayList<String>) {
        event.channel.sendMessage(java.lang.String.join(" ", args)).queue()
    }

    init {

    }
}