package commands.misc

import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.doc.standard.CommandInfo
import commandHandler.Command
import kong.unirest.json.JSONException
import kong.unirest.json.JSONObject
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.Utils
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

@CommandInfo(name = ["pokedex"], usage = "pokedex pikachu", description = "gets a pokemon from the pokedex")
class Pokedex constructor(waiter: EventWaiter?) : Command(waiter) {
    override fun execute(event: MessageReceivedEvent, args: ArrayList<String>) {
        val eb = EmbedBuilder()
        val httpResponse: JSONObject? = Utils().getObject("https://some-random-api.ml/pokedex?pokemon=" + args[0])
        val mainName: String = httpResponse!!.getString("name")
        val buffer = StringBuffer(mainName)
        val pattern: Pattern = Pattern.compile("(^|\\.)\\s*(\\w)")
        val matcher: Matcher = pattern.matcher(buffer)
        while (matcher.find()) {
            buffer.replace(matcher.end() - 1, matcher.end(), matcher.group(2).toUpperCase())
        }
        var prevEvo: Any = "n/a"
        var nextEvo: Any = "n/a"
        try {
            prevEvo = httpResponse.getJSONObject("family").getJSONArray("evolutionLine").get(httpResponse.getJSONObject("family").getInt("evolutionStage") - 2)
        } catch (ignored: JSONException) {
            println("error")
        } catch (ignored: IndexOutOfBoundsException) {
            println("error")
        }
        try {
            nextEvo = httpResponse.getJSONObject("family").getJSONArray("evolutionLine").get(httpResponse.getJSONObject("family").getInt("evolutionStage"))
        } catch (ignored: JSONException) {
            println("error")
        } catch (ignored: IndexOutOfBoundsException) {
            println("error")
        }
        eb
                .setTitle("**$buffer**")
                .setDescription(("""     *${httpResponse.getString("description")}*

          **ID:** ${httpResponse.getString("id")}
          **Base Experience:** ${httpResponse.getString("base_experience")}
          **Generation:** ${httpResponse.getString("generation")}
          **Type:** ${httpResponse.getJSONArray("type").join(", ")}
          **Weight:** ${httpResponse.getString("weight")}
          **EggGroup:** ${httpResponse.getJSONArray("egg_groups").join(", ")}
          **Previous evolution:** $prevEvo
          **Next evolution:** $nextEvo
"""))
                .setThumbnail("https://i.some-random-api.ml/pokemon/" + args[0] + ".gif")
        event.channel.sendMessage(eb.build()).queue()
    }
}