package commands.utils;

import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import commandHandler.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.reflections8.Reflections;

import java.util.*;

@CommandInfo(name = {"help", "h"}, description = "help command", usage = "help commandname")
public class help extends Command {
    public ArrayList<Command> cmds = new ArrayList<>();
    EmbedBuilder mainEmbed = new EmbedBuilder();
    Reflections reflections = new Reflections("commands");
    Set<Class<? extends Command>> classes = reflections.getSubTypesOf(Command.class);

    public help() throws InstantiationException, IllegalAccessException {
        for (Class<? extends Command> cmd : classes) {
            if ((cmd.getAnnotation(CommandInfo.class).name())[0].equals("help")) continue;
            cmds.add(cmd.newInstance());
        }
        for (Command cmd : cmds) {
            CommandInfo commandInfo = cmd.getClass().getAnnotation(CommandInfo.class);
            ArrayList<String> aliases = new ArrayList<>();
            Collections.addAll(aliases, commandInfo.name());
            String cmdName = aliases.remove(0);
            mainEmbed
                    .setTitle("help")
                    .addField(cmdName, commandInfo.description() + (aliases.isEmpty() ? "" : (" - Aliases: `" + String.join("`, `", aliases))) + "`", true);
        }
    }

    @Override
    protected void execute(@NotNull MessageReceivedEvent event, ArrayList<String> args) throws Exception {
        if (!args.isEmpty()) {
            EmbedBuilder customHelp = new EmbedBuilder();
            CommandInfo commandInfo = null;
            for (Command cmd : cmds) {
                if (Arrays.asList(cmd.getClass().getAnnotation(CommandInfo.class).name()).contains(args.get(0)))
                    commandInfo = cmd.getClass().getAnnotation(CommandInfo.class);
            }
            if (commandInfo != null) {
                List<String> aliases = Arrays.asList(commandInfo.name());
                customHelp
                        .setTitle(aliases.get(0))
                        .setDescription(commandInfo.description())
                        .addField("usage", "`" + commandInfo.usage() + "`", false)
                        .addField("aliases", "`" + String.join("`, `", aliases) + "`", false);
                event.getChannel().sendMessage(customHelp.build()).queue();
            } else {
                event.getChannel().sendMessage("enter a valid command!").queue();
            }
        } else {
            event.getChannel().sendMessage(mainEmbed.build()).queue();
        }
    }
}
