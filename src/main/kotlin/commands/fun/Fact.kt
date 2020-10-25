package commands.`fun`

import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.doc.standard.CommandInfo
import commandHandler.Command
import kong.unirest.json.JSONObject
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.Utils
import java.util.*

@CommandInfo(name = ["fact"], usage = "fact", description = "get a random fact")
class Fact constructor(waiter: EventWaiter?) : Command(waiter) {
    override fun execute(event: MessageReceivedEvent, args: ArrayList<String>) {
        val obj: JSONObject? = Utils().getObject("https://no-api-key.com/api/v1/facts")
        event.channel.sendMessage(obj!!.getString("fact")).queue()
    }
}