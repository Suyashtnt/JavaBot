package commands.`fun`

import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.doc.standard.CommandInfo
import commandHandler.Command
import kong.unirest.json.JSONObject
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.Utils
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

@CommandInfo(name = ["8ball"], description = "are you lucky?", usage = "8ball am i cool?")
class EightBall constructor(waiter: EventWaiter?) : Command(waiter) {
    override fun execute(event: MessageReceivedEvent, args: ArrayList<String>) {
        val obj: JSONObject? = Utils().getObject("https://no-api-key.com/api/v1/magic8ball")
        val mainName: String = java.lang.String.join(" ", args)
        val buffer = StringBuffer(mainName)
        val pattern: Pattern = Pattern.compile("(^|\\.)\\s*(\\w)")
        val matcher: Matcher = pattern.matcher(buffer)
        while (matcher.find()) {
            buffer.replace(matcher.end() - 1, matcher.end(), matcher.group(2).toUpperCase())
        }
        val eb: EmbedBuilder = EmbedBuilder()
        eb.setTitle(if (args.isEmpty()) "something" else buffer.toString())
                .setDescription(obj!!.getString("response"))
                .setThumbnail("https://cdn.discordapp.com/attachments/727924236785549345/769546900881014815/8-ball.png")
        event.channel.sendMessage(eb.build()).queue()
    }
}