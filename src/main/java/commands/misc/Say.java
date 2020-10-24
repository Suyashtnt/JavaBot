package commands.misc;

import commandHandler.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Say extends Command {
    public Say() {
        commandName = "say";
    }

    @Override
    protected void execute(@NotNull MessageReceivedEvent event, ArrayList<String> args) {

        event.getChannel().sendMessage(String.join(" ", args)).queue();
    }
}
