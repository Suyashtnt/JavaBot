package commands.`fun`

import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.doc.standard.CommandInfo
import commandHandler.Command
import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.Utils
import java.util.*
import java.util.concurrent.TimeUnit

@CommandInfo(name = ["fight"], usage = "fight @someone", description = "fight someone")
class Fight constructor(var waiter: EventWaiter) : Command(waiter) {
    var utils: Utils = Utils()

    @Throws(Exception::class)
    override fun execute(event: MessageReceivedEvent, args: ArrayList<String>) {
        event.channel.sendMessage(event.message.mentionedMembers[0].asMention + ", do you accept?").queue()
        utils.waitForMessage<Event>({ true }, { e: MessageReceivedEvent ->
            println(e.message.contentRaw)
            if (event.message.mentionedMembers[0] === e.member && (e.message.contentRaw == "yes")) {
                event.channel.sendMessage("3. 2. 1. Fight!").queue()
            }
        }, 30, TimeUnit.SECONDS, { event.channel.sendMessage("it took to long for them to respond").queue() }, waiter)
    }
}