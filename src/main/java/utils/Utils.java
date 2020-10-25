package utils;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Utils {
    public JSONObject getObject(String URL) {
        HttpResponse<JsonNode> yes = Unirest.get(URL)
                .asJson();
        if (yes.isSuccess()) {
            return yes
                    .getBody()
                    .getObject();
        } else {
            throw new NullPointerException();
        }
    }

    public <T extends Event> void waitForMessage(Predicate<MessageReceivedEvent> condition, Consumer<MessageReceivedEvent> action,
                                                 long timeout, TimeUnit unit, Runnable timeoutAction, EventWaiter waiter) {
        waiter.waitForEvent(MessageReceivedEvent.class, ev -> true, ev -> {
            waiter.waitForEvent(MessageReceivedEvent.class, evv -> true, evv -> {
                waiter.waitForEvent(MessageReceivedEvent.class, condition, action, timeout, unit, timeoutAction);
            }, 30, TimeUnit.SECONDS, new Runnable() {
                @Override
                public void run() {

                }
            });
        }, 30, TimeUnit.SECONDS, new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
