package commands.fun;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import commandHandler.Command;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import utils.Utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CommandInfo(name = "8ball", description = "are you lucky?", usage = "8ball am i cool?")
public class EightBall extends Command {

    public EightBall(EventWaiter waiter) {
        super(waiter);
    }

    @Override
    protected void execute(@NotNull MessageReceivedEvent event, ArrayList<String> args) {
        JSONObject obj = new Utils().getObject("https://no-api-key.com/api/v1/magic8ball");

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
