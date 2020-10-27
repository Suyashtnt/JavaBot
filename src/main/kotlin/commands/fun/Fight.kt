package commands.`fun`

import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.doc.standard.CommandInfo
import com.mongodb.client.MongoClient
import commandHandler.Command
import dev.minn.jda.ktx.awaitMessage
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.Utils
import java.util.*

@CommandInfo(name = ["fight"], usage = "fight @someone", description = "fight someone")
class Fight constructor(waiter: EventWaiter, mongoClient: MongoClient) : Command(waiter, mongoClient) {
    var utils: Utils = Utils()

    @Throws(Exception::class)
    override suspend fun execute(event: MessageReceivedEvent, args: ArrayList<String>) {
        event.channel.sendMessage(event.message.mentionedMembers[0].asMention + ", do you accept?").queue()
        val e = event.channel.awaitMessage(event.message.mentionedMembers[0].user)
        println(e.contentRaw)
        if (event.message.mentionedMembers[0] === e.member && (e.contentRaw == "yes")) {
            event.channel.sendMessage("3. 2. 1. Fight!").queue()
        }
    }

    init {

    }
}