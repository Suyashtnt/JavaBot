package commandHandler

import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.jagrosh.jdautilities.doc.standard.CommandInfo
import com.mongodb.client.MongoClient
import dev.minn.jda.ktx.await
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.reflections8.Reflections
import java.lang.reflect.Constructor
import java.util.*

class Handler constructor(locationOfCommands: String, private var prefix: String, waiter: EventWaiter, mongoClient: MongoClient) {
    private var path: String = ""
    private var cmds: ArrayList<Command> = ArrayList()
    private var reflections: Reflections = Reflections(path)
    private var timer: Timer = Timer()
    var classes: Set<Class<out Command>> = reflections.getSubTypesOf(Command::class.java)
    var cooldownTimes: HashMap<String?, String?> = object : HashMap<String?, String?>() {}

    suspend fun messageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) return
        println("We received a message that contains: " + event.message.contentDisplay)
        for (cmd: Command in cmds) {
            for (alias: String in cmd.javaClass.getAnnotation(CommandInfo::class.java).name) {
                val split: Array<String> = event.message.contentDisplay.trim { it <= ' ' }.split(" ").toTypedArray()
                if ((split[0] == prefix + alias)) {

                    if (cmd.botPerms.isNotEmpty()) {
                        if (!event.guild.selfMember.hasPermission(*cmd.botPerms)) {
                            val perms: ArrayList<String> = ArrayList()
                            for (perm: Permission? in cmd.botPerms) perms.add(perm!!.getName())
                            event.channel.sendMessage("the bot does not have the permissions it needs to run this. It needs: `" + java.lang.String.join(", ", perms) + "`").await()
                            return
                        }
                    }

                    if (cmd.clientPerms.isNotEmpty()) {
                        if (!event.guild.selfMember.hasPermission(*cmd.clientPerms)) {
                            val perms: ArrayList<String> = ArrayList()
                            for (perm: Permission? in cmd.clientPerms) perms.add(perm!!.getName())
                            event.channel.sendMessage("You don't have the permissions you need to run this. You need: `" + java.lang.String.join(", ", perms) + "`").await()
                            return
                        }
                    }

                    if (cooldownTimes.containsKey(event.author.id) && cooldownTimes.containsValue((cmd.javaClass.getAnnotation(CommandInfo::class.java).name)[0])) {
                        event.channel.sendMessage("sorry but you are on time cooldown").await()
                        return
                    }

                    val args: ArrayList<String> = ArrayList()
                    Collections.addAll(args, *split.copyOfRange(1, split.size))
                    cooldownTimes[event.author.id] = (cmd.javaClass.getAnnotation(CommandInfo::class.java).name)[0]
                    cmd.execute(event, args)
                    timer.schedule(object : TimerTask() {
                        override fun run() {
                            cooldownTimes.remove(event.author.id, (cmd.javaClass.getAnnotation(CommandInfo::class.java).name)[0])
                        }
                    }, cmd.cooldown.toLong())
                    break
                }
            }
        }
    }

    init {
        path = locationOfCommands
        for (aClass: Class<out Command> in classes) {
            try {
                println("loading " + aClass.name)
                val commandConstructor: Constructor<out Command> = aClass.getConstructor(EventWaiter::class.java, MongoClient::class.java)
                val classInstance: Command = commandConstructor.newInstance(waiter, mongoClient)
                cmds.add(classInstance)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}