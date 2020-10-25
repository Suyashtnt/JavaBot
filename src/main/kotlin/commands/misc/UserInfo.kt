package commands.misc

import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.doc.standard.CommandInfo
import commandHandler.Command
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@CommandInfo(name = ["userinfo"], description = "get your users info", usage = "userinfo")
class UserInfo constructor(waiter: EventWaiter?) : Command(waiter) {
    override fun execute(event: MessageReceivedEvent, args: ArrayList<String>) {
        val eb = EmbedBuilder()
        val time: OffsetDateTime? = event.member!!.timeBoosted
        eb
                .setTitle(event.author.name + "'s Info")
                .setThumbnail(event.author.avatarUrl)
                .setDescription(("**ID: `" + event.author.id + "`**\n" +
                        "        **Discrim:** #" + event.author.discriminator + "\n" +
                        "        **Guild Nickname:** " + (event.member!!.nickname ?: "no nick") + "\n" +
                        "        **Joined Server:** " + event.member!!.timeJoined.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n" +
                        "        **Avatar URL:** [Click](" + event.author.avatarUrl + ")\n" +
                        "        **Started Boosting: **" + (if (time != null) time.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) else "not boosted yet")))
        event.channel.sendMessage(eb.build()).queue()
    }
}