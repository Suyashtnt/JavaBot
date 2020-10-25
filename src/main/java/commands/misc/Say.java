package commands.misc;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import commandHandler.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@CommandInfo(name = "say", description = "the bot will say what you type", usage = "say something to say")
public class Say extends Command {

	public Say(EventWaiter waiter) {
		super(waiter);
	}

	@Override
	protected void execute(@NotNull MessageReceivedEvent event, ArrayList<String> args) {

		event.getChannel().sendMessage(String.join(" ", args)).queue();
	}
}
