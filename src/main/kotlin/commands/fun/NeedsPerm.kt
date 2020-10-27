package commands.`fun`

import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.doc.standard.CommandInfo
import com.mongodb.client.MongoClient
import commandHandler.Command
import dev.minn.jda.ktx.await
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.util.*

@CommandInfo(name = ["kick"], description = "fake kick someone", usage = "kick")
class NeedsPerm constructor(waiter: EventWaiter?, mongoClient: MongoClient) : Command(waiter, mongoClient) {
    override suspend fun execute(event: MessageReceivedEvent, args: ArrayList<String>) {
        event.channel.sendMessage("pretending to kick").await()
    }

    init {
        botPerms = arrayOf(Permission.KICK_MEMBERS)
    }
}