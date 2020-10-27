package commands.utils

import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.doc.standard.CommandInfo
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import commandHandler.Command
import dev.minn.jda.ktx.await
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.bson.Document
import java.util.*

@CommandInfo(name = ["rickrollremove", "rre", "rickrollexception", "rrr"], description = "removes you from the rickRoll list", usage = "rre remove")
class RickRollException(waiter: EventWaiter?, mongoClient: MongoClient) : Command(waiter, mongoClient) {
    private val db: MongoDatabase = mongoClient.getDatabase("javaBot")
    private val collection = db.getCollection("rickRollExclude")
    override suspend fun execute(event: MessageReceivedEvent, args: ArrayList<String>) {
        if (args.isNotEmpty()) {
            if (args[0] == "remove") {
                collection.deleteOne(eq("userID", event.author.id))
                event.channel.sendMessage("removed").await()
                return
            }
        }
        val doc = Document("userID", event.author.id)
        collection.insertOne(doc)
        event.channel.sendMessage("added exception").await()
    }

    init {

    }
}