package commands.`fun`

import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.doc.standard.CommandInfo
import commandHandler.Command
import kong.unirest.json.JSONObject
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.Utils
import java.util.*

@CommandInfo(name = ["cat"], description = "get a cat image and a fact", usage = "cat")
class Cat constructor(waiter: EventWaiter?) : Command(waiter) {
    override fun execute(event: MessageReceivedEvent, args: ArrayList<String>) {
        val obj: JSONObject? = Utils().getObject("https://no-api-key.com/api/v1/animals/cat")
        val eb = EmbedBuilder()
        eb
                .setTitle("cat " + String(Character.toChars(0x1F638)))
                .setDescription("fact: " + obj!!.getString("fact"))
                .setImage(obj.getString("image"))
                .setColor(0x6df7ff)
        event.channel.sendMessage(eb.build()).queue()
    }
}