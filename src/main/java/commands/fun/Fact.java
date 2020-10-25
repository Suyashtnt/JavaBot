package commands.fun;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import commandHandler.Command;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import utils.Utils;

import java.util.ArrayList;

@CommandInfo(name = "fact", usage = "fact", description = "get a random fact")
public class Fact extends Command {

    public Fact(EventWaiter waiter) {
        super(waiter);
    }

    @Override
    protected void execute(@NotNull MessageReceivedEvent event, ArrayList<String> args) {
        JSONObject obj = new Utils().getObject("https://no-api-key.com/api/v1/facts");
        event.getChannel().sendMessage(obj.getString("fact")).queue();
    }
}
