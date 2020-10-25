package commands;

import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import commandHandler.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@CommandInfo(name = "hi", description = "says hi", usage = "hi")
public class Hi extends Command {
    @Override
    protected void execute(@NotNull MessageReceivedEvent event, ArrayList<String> args) {
        event.getChannel().sendMessage("hello, " + event.getAuthor().getAsTag()).queue();
    }

}
