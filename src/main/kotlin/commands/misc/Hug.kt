package commands.misc

import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.doc.standard.CommandInfo
import com.mongodb.client.MongoClient
import commandHandler.Command
import dev.minn.jda.ktx.await
import kong.unirest.HttpResponse
import kong.unirest.JsonNode
import kong.unirest.Unirest
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.util.*

@CommandInfo(name = ["hug"], usage = "hug @someone", description = "hug someone")
class Hug constructor(waiter: EventWaiter?, mongoClient: MongoClient) : Command(waiter, mongoClient) {
    override suspend fun execute(event: MessageReceivedEvent, args: ArrayList<String>) {
        val eb: EmbedBuilder = EmbedBuilder()
        val httpResponse: HttpResponse<JsonNode> = Unirest.get("https://nekos.life/api/v2/img/hug")
                .asJson()
        val mentioned: Member? = event.message.mentionedMembers[0]
        when {
            mentioned == null -> {
                eb.setTitle("uhhhh")
                        .setDescription("Please mention someone to kiss!")
            }
            mentioned === event.member -> {
                eb.setTitle("uhhhh")
                        .setDescription("Are you _that_ lonely? Please mention someone to hug!")
            }
            else -> {
                eb.setTitle("**Hug \uD83E\uDD17**").setDescription("**" + event.author.name + "** hugged **" + mentioned.effectiveName + "**").setImage(httpResponse.body.getObject().getString("url"))
            }
        }
        event.channel.sendMessage(eb.build()).await()
    }

    init {

    }
}