package commands.`fun`

import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.doc.standard.CommandInfo
import com.mongodb.client.MongoClient
import commandHandler.Command
import dev.minn.jda.ktx.await
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import utils.Utils
import java.util.*
import java.util.stream.Collectors

@CommandInfo(name = ["rickroll"], description = "sends the lyrics of never gonna give you up to a random person on this server", usage = "rickroll")
class Rickroll constructor(waiter: EventWaiter?, mongoClient: MongoClient) : Command(waiter, mongoClient) {

    @Throws(Exception::class)
    override suspend fun execute(event: MessageReceivedEvent, args: ArrayList<String>) {

        val collection = mongoClient.getDatabase("javaBot").getCollection("rickRollExclude")
        val user: Member? = Utils().getRandomListElement(event.guild.members.stream().filter { p: Member -> !p.user.isBot && collection.find().none { e -> e.containsValue(p.id) } }.collect(Collectors.toList()))

        println(user!!.effectiveName)
        try {
            val channel = user.user.openPrivateChannel().await()
            channel.sendMessage(("We're no strangers to love\n" +
                    "You know the rules and so do I\n" +
                    "A full commitment's what I'm thinking of\n" +
                    "You wouldn't get this from any other guy\n" +
                    "\n" +
                    "I just wanna tell you how I'm feeling\n" +
                    "Gotta make you understand\n" +
                    "\n" +
                    "Never gonna give you up\n" +
                    "Never gonna let you down\n" +
                    "Never gonna run around and desert you\n" +
                    "Never gonna make you cry\n" +
                    "Never gonna say goodbye\n" +
                    "Never gonna tell a lie and hurt you\n" +
                    "\n" +
                    "We've known each other for so long\n" +
                    "Your heart's been aching, but\n" +
                    "You're too shy to say it\n" +
                    "Inside, we both know what's been going on\n" +
                    "We know the game and we're gonna play it\n" +
                    "\n" +
                    "And if you ask me how I'm feeling\n" +
                    "Don't tell me you're too blind to see\n" +
                    "\n" +
                    "Never gonna give you up\n" +
                    "Never gonna let you down\n" +
                    "Never gonna run around and desert you\n" +
                    "Never gonna make you cry\n" +
                    "Never gonna say goodbye\n" +
                    "Never gonna tell a lie and hurt you\n" +
                    "\n" +
                    "Never gonna give you up\n" +
                    "Never gonna let you down\n" +
                    "Never gonna run around and desert you\n" +
                    "Never gonna make you cry\n" +
                    "Never gonna say goodbye\n" +
                    "Never gonna tell a lie and hurt you\n" +
                    "\n" +
                    "(Ooh, give you up)\n" +
                    "(Ooh, give you up)\n" +
                    "Never gonna give, never gonna give\n" +
                    "(Give you up)\n" +
                    "Never gonna give, never gonna give\n" +
                    "(Give you up)\n" +
                    "\n" +
                    "We've known each other for so long\n" +
                    "Your heart's been aching, but\n" +
                    "You're too shy to say it\n" +
                    "Inside, we both know what's been going on\n" +
                    "We know the game and we're gonna play it\n" +
                    "\n" +
                    "I just wanna tell you how I'm feeling\n" +
                    "Gotta make you understand\n" +
                    "\n" +
                    "Never gonna give you up\n" +
                    "Never gonna let you down\n" +
                    "Never gonna run around and desert you\n" +
                    "Never gonna make you cry\n" +
                    "Never gonna say goodbye\n" +
                    "Never gonna tell a lie and hurt you\n" +
                    "\n" +
                    "Never gonna give you up\n" +
                    "Never gonna let you down\n" +
                    "Never gonna run around and desert you\n" +
                    "Never gonna make you cry\n" +
                    "Never gonna say goodbye\n" +
                    "Never gonna tell a lie and hurt you\n" +
                    "\n" +
                    "Never gonna give you up\n" +
                    "Never gonna let you down\n" +
                    "Never gonna run around and desert you\n" +
                    "Never gonna make you cry\n" +
                    "Never gonna say goodbye\n" +
                    "Never gonna tell a lie and hurt you\n")).await()
            channel.sendMessage("""sent by ${event.member!!.effectiveName} from ${event.guild.name})""").await()
            event.author.openPrivateChannel().await().sendMessage("""sent to ${user.effectiveName} """).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    init {
        cooldown = 5 * 1000 * 60
    }
}