package commandHandler

import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.doc.standard.CommandInfo
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.util.*

open class Command constructor(waiter: EventWaiter?) {
    var botPerms: Array<Permission> = arrayOf()
    var clientPerms: Array<Permission> = arrayOf()
    var cooldown: Int = 0

    @Throws(Exception::class)
    open fun execute(event: MessageReceivedEvent, args: ArrayList<String>) {
        println("running " + this.javaClass.getAnnotation(CommandInfo::class.java).name)
    }
}