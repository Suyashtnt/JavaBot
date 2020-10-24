package commands.fun;

import commandHandler.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class needsPerm extends Command {
    public needsPerm() {
        botPerms = new Permission[]{Permission.KICK_MEMBERS};
        commandName = "kick";
    }

    @Override
    protected void execute(@NotNull MessageReceivedEvent event, ArrayList<String> args) {
        event.getChannel().sendMessage("pretending to kick").queue();
    }
}
