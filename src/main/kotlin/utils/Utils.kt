package utils

import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import kong.unirest.Unirest
import kong.unirest.json.JSONObject
import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit
import java.util.function.Consumer
import java.util.function.Predicate

class Utils {
    fun getObject(URL: String?): JSONObject {
        val yes = Unirest.get(URL)
                .asJson()
        return if (yes.isSuccess) {
            yes
                    .body
                    .getObject()
        } else {
            throw NullPointerException()
        }
    }

    fun <T : Event?> waitForMessage(condition: Predicate<MessageReceivedEvent>?, action: Consumer<MessageReceivedEvent>?,
                                    timeout: Long, unit: TimeUnit?, timeoutAction: Runnable?, waiter: EventWaiter) {
        waiter.waitForEvent(MessageReceivedEvent::class.java, { true }, { waiter.waitForEvent(MessageReceivedEvent::class.java, { true }, { waiter.waitForEvent(MessageReceivedEvent::class.java, condition, action, timeout, unit, timeoutAction) }, 30, TimeUnit.SECONDS, Runnable { }) }, 30, TimeUnit.SECONDS) { }
    }

    fun <T> getRandomListElement(items: List<T>): T {
        return items[ThreadLocalRandom.current().nextInt(items.size)]
    }
}