package commands.fun;

import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import commandHandler.Command;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import utils.Utils;

import java.util.ArrayList;

@CommandInfo(name = "cat", description = "get a cat image and a fact", usage = "cat")
public class cat extends Command {

	@Override
	protected void execute(@NotNull MessageReceivedEvent event, ArrayList<String> args) {
		JSONObject obj = new Utils().getObject("https://no-api-key.com/api/v1/animals/cat");
		EmbedBuilder eb = new EmbedBuilder();
		eb
				.setTitle("cat " + new String(Character.toChars(0x1F638)))
				.setDescription("fact: " + obj.getString("fact"))
				.setImage(obj.getString("image"))
				.setColor(0x6df7ff);
		event.getChannel().sendMessage(eb.build()).queue();
	}
}
