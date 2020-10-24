package commands.fun;

import commandHandler.Command;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class eight_ball extends Command {
    public eight_ball() {
        commandName = "8ball";
    }

    @Override
    protected void runs(@NotNull MessageReceivedEvent event, ArrayList<String> args) throws Exception {
        JSONObject obj = Unirest.get("https://no-api-key.com/api/v1/magic8ball")
                .asJson()
                .getBody()
                .getObject();

        String mainName = String.join(" ", args);
        StringBuffer buffer = new StringBuffer(mainName);
        Pattern pattern = Pattern.compile("(^|\\.)\\s*(\\w)");
        Matcher matcher = pattern.matcher(buffer);
        while (matcher.find()) {
            buffer.replace(matcher.end() - 1, matcher.end(), matcher.group(2).toUpperCase());
        }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(args.isEmpty() ? "something" : buffer.toString())
                .setDescription(obj.getString("response"))
                .setThumbnail("https://cdn.discordapp.com/attachments/727924236785549345/769546900881014815/8-ball.png");
        event.getChannel().sendMessage(eb.build()).queue();

    }
}
