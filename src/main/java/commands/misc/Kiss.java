package commands.misc;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import commandHandler.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@CommandInfo(name = "kiss", description = "kiss someone", usage = "kiss @someone")
public class Kiss extends Command {

	String[] images = {
			"https://media1.tenor.com/images/289ec1f0b0ee5163758122ed5fc1cb20/tenor.gif",
			"https://media1.tenor.com/images/f102a57842e7325873dd980327d39b39/tenor.gif",
			"https://media1.tenor.com/images/ea9a07318bd8400fbfbd658e9f5ecd5d/tenor.gif",
			"https://media1.tenor.com/images/693602b39a071644cebebdce7c459142/tenor.gif",
			"https://media1.tenor.com/images/bc5e143ab33084961904240f431ca0b1/tenor.gif",
			"https://media1.tenor.com/images/b8d0152fbe9ecc061f9ad7ff74533396/tenor.gif",
			"https://media1.tenor.com/images/4b5d5afd747fe053ed79317628aac106/tenor.gif",
			"https://media1.tenor.com/images/e76e640bbbd4161345f551bb42e6eb13/tenor.gif",
			"https://media1.tenor.com/images/621ceac89636fc46ecaf81824f9fee0e/tenor.gif",
			"https://media1.tenor.com/images/02d9cae34993e48ab5bb27763d5ca2fa/tenor.gif",
			"https://media1.tenor.com/images/8e0e0c3970262b0b4b30ee6d9eb04756/tenor.gif",
			"https://media1.tenor.com/images/7fd98defeb5fd901afe6ace0dffce96e/tenor.gif",
			"https://media1.tenor.com/images/ef4a0bcb6e42189dc12ee55e0d479c54/tenor.gif",
			"https://media1.tenor.com/images/1306732d3351afe642c9a7f6d46f548e/tenor.gif",
			"https://media1.tenor.com/images/daf7b144c7caceee3d90dca791a4c790/tenor.gif",
			"https://media1.tenor.com/images/a562410344e8b88fd737dfc9a4b6b1e1/tenor.gif",
			"https://media1.tenor.com/images/632a3db90c6ecd87f1242605f92120c7/tenor.gif",
			"https://media1.tenor.com/images/50aa47434150f8d07c8fa0a6f8a0556e/tenor.gif",
			"https://media1.tenor.com/images/e4fcb11bc3f6585ecc70276cc325aa1c/tenor.gif",
			"https://media1.tenor.com/images/f5167c56b1cca2814f9eca99c4f4fab8/tenor.gif",
			"https://media1.tenor.com/images/a9100d75d9edbc2c17219dddc96d179d/tenor.gif",
			"https://media1.tenor.com/images/a1f7d43752168b3c1dbdfb925bda8a33/tenor.gif"
	};

	public Kiss(EventWaiter waiter) {
		super(waiter);
	}


	@Override
	protected void execute(@NotNull MessageReceivedEvent event, ArrayList<String> args) throws Exception {
		EmbedBuilder eb = new EmbedBuilder();

		int rand = (int) Math.floor(Math.random() * images.length);
		String randomImage = images[rand];

		Member Mentioned = event.getMessage().getMentionedMembers().get(0);
		if (Mentioned == null) {
			eb.setTitle("uhhhh")
					.setDescription("Please mention someone to kiss!");
		} else if (Mentioned == event.getMember()) {
			eb.setTitle("uhhhh")
					.setDescription("Are you _that_ lonely? Please mention someone to kiss!");
		} else {
			eb
					.setTitle("**Kiss 💋**")
					.setDescription("**" + event.getAuthor().getName() + "** kissed **" + Mentioned.getEffectiveName() + "**")
					.setImage(randomImage)
					.setColor(0xe889e0);
		}
		event.getChannel().sendMessage(eb.build()).queue();
	}
}
