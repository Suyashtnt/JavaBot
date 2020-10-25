package commands

import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.doc.standard.CommandInfo
import commandHandler.Command
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.util.*

@CommandInfo(name = ["hi"], description = "says hi", usage = "hi")
class Hi constructor(waiter: EventWaiter?) : Command(waiter) {
    override fun execute(event: MessageReceivedEvent, args: ArrayList<String>) {
        event.channel.sendMessage("hello, " + event.author.asTag).queue()
    }
}