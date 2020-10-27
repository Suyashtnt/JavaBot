import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import commandHandler.Handler
import dev.minn.jda.ktx.injectKTX
import dev.minn.jda.ktx.listener
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent
import javax.security.auth.login.LoginException

class Main : ListenerAdapter() {
    companion object {
        private val waiter: EventWaiter = EventWaiter()


        @Throws(LoginException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val mongoClient: MongoClient = MongoClients.create("mongodb+srv://javaBot:${args[1]}@main.rvovx.mongodb.net/javaBot?retryWrites=true&w=majority")
            val handler = Handler("com.tnt_man_inc.JavaBot2.commands", "!", waiter, mongoClient)

            val jda = JDABuilder.create(args[0], GatewayIntent.GUILD_MEMBERS, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES)
                    .addEventListeners(waiter)
                    .injectKTX()
                    .setStatus(OnlineStatus.ONLINE)
                    .setActivity(Activity.watching("My creator figure out what to do with me"))
                    .build()

            jda.listener<MessageReceivedEvent> {
                handler.messageReceived(it)
            }
        }
    }
}