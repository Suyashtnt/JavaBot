package commands.`fun`

import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.doc.standard.CommandInfo
import commandHandler.Command
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.util.*

@CommandInfo(name = ["kick"], description = "fake kick someone", usage = "kick")
class NeedsPerm constructor(waiter: EventWaiter?) : Command(waiter) {
    override fun execute(event: MessageReceivedEvent, args: ArrayList<String>) {
        event.channel.sendMessage("pretending to kick").queue()
    }

    init {
        botPerms = arrayOf(Permission.KICK_MEMBERS)
    }
}