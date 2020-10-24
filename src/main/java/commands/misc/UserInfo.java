package commands.misc;

import commandHandler.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UserInfo extends Command {
    public UserInfo() {
        commandName = "userinfo";
    }

    @Override
    protected void runs(@NotNull MessageReceivedEvent event, ArrayList<String> args) throws Exception {
        EmbedBuilder eb = new EmbedBuilder();
        OffsetDateTime time = event.getMember().getTimeBoosted();
        String nick = event.getMember().getNickname();

        eb
                .setTitle(event.getAuthor().getName() + "'s Info")
                .setThumbnail(event.getAuthor().getAvatarUrl())
                .setDescription("**ID: `" + event.getAuthor().getId() + "`**\n" +
                        "        **Discrim:** #" + event.getAuthor().getDiscriminator() + "\n" +
                        "        **Guild Nickname:** " + (nick != null ? nick : "no nick") + "\n" +
                        "        **Joined Server:** " + event.getMember().getTimeJoined().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n" +
                        "        **Avatar URL:** [Click](" + event.getAuthor().getAvatarUrl() + ")\n" +
                        "        **Started Boosting: **" + (time != null ? time.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "not boosted yet"));
        event.getChannel().sendMessage(eb.build()).queue();
    }
}
