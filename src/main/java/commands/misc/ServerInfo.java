package commands.misc;

import commandHandler.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ServerInfo extends Command {
    public ServerInfo() {
        commandName = "serverinfo";
    }

    @Override
    protected void execute(@NotNull MessageReceivedEvent event, ArrayList<String> args) {
        EmbedBuilder eb = new EmbedBuilder();
        Guild guild = event.getGuild();
        eb
                .setTitle("**" + guild.getName() + "**'s Stats")
                .setThumbnail(guild.getIconUrl())
                .setDescription("     **ID: `" + guild.getId() + "`**\n" +
                        "        **Owner: `" + guild.getOwnerId() + "`**\n" +
                        "        **Members:** " + guild.getMembers().size() + "\n" +
                        "        **Channels:** " + guild.getChannels().size() + "\n" +
                        "        **Roles:** " + guild.getRoles().size() + "\n" +
                        "        **Region: `" + guild.getRegion().getName() + "`**\n" +
                        "        **Large Server?: `Not implemented`**");
        event.getChannel().sendMessage(eb.build()).queue();
    }
}
