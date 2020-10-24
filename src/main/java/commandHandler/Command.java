package commandHandler;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Command {

    protected String commandName;
    protected ArrayList<String> commandAliases = new ArrayList<>();

    protected void runs(@NotNull MessageReceivedEvent event, ArrayList<String> args) throws Exception {
        System.out.println("running " + commandName);
    }

}

