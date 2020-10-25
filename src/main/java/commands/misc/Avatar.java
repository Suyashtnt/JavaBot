package commands.misc;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import commandHandler.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@CommandInfo(name = {"avatar", "av", "pfp", "profilepic"}, usage = "avatar", description = "get your avatar and the url")
public class Avatar extends Command {

	public Avatar(EventWaiter waiter) {
		super(waiter);
	}

	@Override
	protected void execute(@NotNull MessageReceivedEvent event, ArrayList<String> args) {
		EmbedBuilder eb = new EmbedBuilder();
		eb
				.setTitle("**" + event.getAuthor().getName() + "'s avatar**")
				.setDescription("**ID: **" + event.getAuthor().getId())
				.setImage(event.getAuthor().getAvatarUrl())
				.setColor(0x7289da);
		event.getChannel().sendMessage(eb.build()).queue();
	}
}
