package commands;

import commandHandler.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Hi extends Command {
    public Hi() {
        super.commandName = "hi";
    }

    @Override
    protected void runs(@NotNull MessageReceivedEvent event, ArrayList<String> args) {
        event.getChannel().sendMessage("hello, " + event.getAuthor().getAsTag()).queue();
    }

}
