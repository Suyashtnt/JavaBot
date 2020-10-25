package commands.misc;

import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import commandHandler.Command;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@CommandInfo(name = "hug", usage = "hug @someone", description = "hug someone")
public class Hug extends Command {

    @Override
    protected void execute(@NotNull MessageReceivedEvent event, ArrayList<String> args) throws Exception {
        EmbedBuilder eb = new EmbedBuilder();
        HttpResponse<JsonNode> httpResponse = Unirest.get("https://nekos.life/api/v2/img/hug")
                .asJson();

        Member Mentioned = event.getMessage().getMentionedMembers().get(0);

        if (Mentioned == null) {
            eb.setTitle("uhhhh")
                    .setDescription("Please mention someone to kiss!");
        } else if (Mentioned == event.getMember()) {
            eb.setTitle("uhhhh")
                    .setDescription("Are you _that_ lonely? Please mention someone to hug!");
        } else {
            eb.setTitle("**Hug \uD83E\uDD17**").setDescription("**" + event.getAuthor().getName() + "** hugged **" + Mentioned.getEffectiveName() + "**").setImage(httpResponse.getBody().getObject().getString("url"));
        }
        event.getChannel().sendMessage(eb.build()).queue();
    }
}
