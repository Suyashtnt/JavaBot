import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import commandHandler.Handler
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent
import javax.security.auth.login.LoginException

class Main : ListenerAdapter() {
    companion object {
        var handler: Handler? = null
        private val waiter: EventWaiter = EventWaiter()

        @Throws(LoginException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val builder: JDABuilder = JDABuilder.create(args[0], GatewayIntent.GUILD_MEMBERS, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES)
            builder.addEventListeners(Main())
            builder.addEventListeners(waiter)
            builder.build()
        }

        init {
            handler = Handler("com.tnt_man_inc.JavaBot2.commands", "!", waiter)
        }
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        try {
            handler!!.messageReceived(event)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}