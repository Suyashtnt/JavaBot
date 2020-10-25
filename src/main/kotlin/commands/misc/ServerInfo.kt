package commands.misc

import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.doc.standard.CommandInfo
import commandHandler.Command
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.util.*

@CommandInfo(name = ["serverinfo"], usage = "serverinfo", description = "get this servers info")
class ServerInfo constructor(waiter: EventWaiter?) : Command(waiter) {
    override fun execute(event: MessageReceivedEvent, args: ArrayList<String>) {
        val eb = EmbedBuilder()
        val guild: Guild = event.guild
        eb
                .setTitle("**" + guild.name + "**'s Stats")
                .setThumbnail(guild.iconUrl)
                .setDescription(("     **ID: `" + guild.id + "`**\n" +
                        "        **Owner: `" + guild.ownerId + "`**\n" +
                        "        **Members:** " + guild.members.size + "\n" +
                        "        **Channels:** " + guild.channels.size + "\n" +
                        "        **Roles:** " + guild.roles.size + "\n" +
                        "        **Region: `" + guild.region.getName() + "`**\n" +
                        "        **Large Server?: `Not implemented`**"))
        event.channel.sendMessage(eb.build()).queue()
    }
}