package commands.fun;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import commandHandler.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@CommandInfo(name = "kick", description = "fake kick someone", usage = "kick")
public class NeedsPerm extends Command {
	public NeedsPerm(EventWaiter waiter) {
		super(waiter);
		botPerms = new Permission[]{Permission.KICK_MEMBERS};
	}

	@Override
	protected void execute(@NotNull MessageReceivedEvent event, ArrayList<String> args) {
		event.getChannel().sendMessage("pretending to kick").queue();
	}
}
