package commands.`fun`

import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.doc.standard.CommandInfo
import commandHandler.Command
import kong.unirest.json.JSONObject
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.Utils
import java.util.*

@CommandInfo(name = ["dog"], description = "get a dog image and a fact", usage = "dog")
class Dog constructor(waiter: EventWaiter?) : Command(waiter) {
    override fun execute(event: MessageReceivedEvent, args: ArrayList<String>) {
        val obj: JSONObject? = Utils().getObject("https://no-api-key.com/api/v1/animals/dog")
        val eb = EmbedBuilder()
        eb
                .setTitle("dog " + String(Character.toChars(0x1F436)))
                .setDescription("fact: " + obj!!.getString("fact"))
                .setImage(obj.getString("image"))
                .setColor(0x6df7ff)
        event.channel.sendMessage(eb.build()).queue()
    }
}