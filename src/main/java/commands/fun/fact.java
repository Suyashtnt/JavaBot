package commands.fun;

import commandHandler.Command;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import utils.Utils;

import java.util.ArrayList;

public class fact extends Command {
    public fact() {
        commandName = "fact";
    }

    @Override
    protected void execute(@NotNull MessageReceivedEvent event, ArrayList<String> args) {
        JSONObject obj = new Utils().getObject("https://no-api-key.com/api/v1/facts");
        event.getChannel().sendMessage(obj.getString("fact")).queue();
    }
}
