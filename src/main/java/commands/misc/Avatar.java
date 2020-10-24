package commands.misc;

import commandHandler.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Avatar extends Command {


    public Avatar() {
        commandName = "avatar";
        commandAliases.add("av");
        commandAliases.add("pfp");
        commandAliases.add("profile");
    }

    @Override
    protected void runs(@NotNull MessageReceivedEvent event, ArrayList<String> args) {
        EmbedBuilder eb = new EmbedBuilder();
        eb
                .setTitle("**" + event.getAuthor().getName() + "'s avatar**")
                .setDescription("**ID: **" + event.getAuthor().getId())
                .setImage(event.getAuthor().getAvatarUrl())
                .setColor(0x7289da);
        event.getChannel().sendMessage(eb.build()).queue();
    }
}
