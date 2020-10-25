package commands.fun;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import commandHandler.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import utils.Utils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@CommandInfo(name = "fight", usage = "fight @someone", description = "fight someone")
public class Fight extends Command {
	EventWaiter waiter;
	Utils utils = new Utils();

	public Fight(EventWaiter waiter) {
		super(waiter);
		this.waiter = waiter;
	}

	@Override
	protected void execute(@NotNull MessageReceivedEvent event, ArrayList<String> args) throws Exception {

		event.getChannel().sendMessage(event.getMessage().getMentionedMembers().get(0).getAsMention() + ", do you accept?").queue();

		utils.waitForMessage(e -> !e.getMember().getUser().isBot(), e -> {
			System.out.println(e.getMessage().getContentRaw());
			if (event.getMessage().getMentionedMembers().get(0) == e.getMember() && e.getMessage().getContentRaw().equals("yes")) {
				event.getChannel().sendMessage("3. 2. 1. Fight!").queue();
			}
		}, 30, TimeUnit.SECONDS, new Runnable() {
			@Override
			public void run() {
				event.getChannel().sendMessage("it took to long for them to respond").queue();
			}
		}, waiter);
	}

}
