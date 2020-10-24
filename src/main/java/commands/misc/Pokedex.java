package commands.misc;

import commandHandler.Command;
import kong.unirest.Unirest;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pokedex extends Command {
    public Pokedex() {
        commandName = "pokedex";
    }

    @Override
    protected void runs(@NotNull MessageReceivedEvent event, ArrayList<String> args) {

        EmbedBuilder eb = new EmbedBuilder();
        JSONObject httpResponse = Unirest.get("https://some-random-api.ml/pokedex?pokemon=" + args.get(0))
                .asJson().getBody().getObject();
        String mainName = httpResponse.getString("name");
        StringBuffer buffer = new StringBuffer(mainName);
        Pattern pattern = Pattern.compile("(^|\\.)\\s*(\\w)");
        Matcher matcher = pattern.matcher(buffer);
        while (matcher.find()) {
            buffer.replace(matcher.end() - 1, matcher.end(), matcher.group(2).toUpperCase());
        }

        Object prevEvo = "n/a";
        Object nextEvo = "n/a";
        try {
            prevEvo = httpResponse.getJSONObject("family").getJSONArray("evolutionLine").get(httpResponse.getJSONObject("family").getInt("evolutionStage") - 2);

        } catch (JSONException | IndexOutOfBoundsException ignored) {
            System.out.println("error");
        }
        try {
            nextEvo = httpResponse.getJSONObject("family").getJSONArray("evolutionLine").get(httpResponse.getJSONObject("family").getInt("evolutionStage"));

        } catch (JSONException | IndexOutOfBoundsException ignored) {
            System.out.println("error");
        }
        eb
                .setTitle("**" + buffer + "**")
                .setDescription("     *" + httpResponse.getString("description") + "*\n\n" +
                        "          **ID:** " + httpResponse.getString("id") + "\n" +
                        "          **Base Experience:** " + httpResponse.getString("base_experience") + "\n" +
                        "          **Generation:** " + httpResponse.getString("generation") + "\n" +
                        "          **Type:** " + httpResponse.getJSONArray("type").join(", ") + "\n" +
                        "          **Weight:** " + httpResponse.getString("weight") + "\n" +
                        "          **EggGroup:** " + httpResponse.getJSONArray("egg_groups").join(", ") + "\n" +
                        "          **Previous evolution:** " + prevEvo + "\n" +
                        "          **Next evolution:** " + nextEvo + "\n")

                .setThumbnail("https://i.some-random-api.ml/pokemon/" + args.get(0) + ".gif");
        event.getChannel().sendMessage(eb.build()).queue();
    }

}
//bot starting look in `run` if you can see it dont worry about the errors i dont want to start he logger ohno error