package commands.misc

import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.doc.standard.CommandInfo
import com.mongodb.client.MongoClient
import commandHandler.Command
import dev.minn.jda.ktx.await
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.util.*

@CommandInfo(name = ["avatar", "av", "pfp", "profilepic"], usage = "avatar", description = "get your avatar and the url")
class Avatar constructor(waiter: EventWaiter?, mongoClient: MongoClient) : Command(waiter, mongoClient) {
    override suspend fun execute(event: MessageReceivedEvent, args: ArrayList<String>) {
        val eb = EmbedBuilder()
        eb
                .setTitle("**" + event.author.name + "'s avatar**")
                .setDescription("**ID: **" + event.author.id)
                .setImage(event.author.avatarUrl)
                .setColor(0x7289da)
        event.channel.sendMessage(eb.build()).await()
    }

    init {

    }
}