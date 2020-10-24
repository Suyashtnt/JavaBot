package commandHandler;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class Handler {
    public String prefix;
    public String path = "";
    public ArrayList<Command> cmds = new ArrayList<>();
    Reflections reflections = new Reflections(path);

    public Handler(String locationOfCommands, String commandPrefix) throws InstantiationException, IllegalAccessException {
        prefix = commandPrefix;
        path = locationOfCommands;
        Set<Class<? extends Command>> classes = reflections.getSubTypesOf(Command.class);
        for (Class<? extends Command> aClass : classes) {
            System.out.println("loading " + aClass.getName());
            Command classInstance = aClass.newInstance();
            cmds.add(classInstance);
        }
    }

    public void messageReceived(@NotNull MessageReceivedEvent event) throws Exception {
        if (event.getAuthor().isBot()) return;

        System.out.println("We received a message that contains: " + event.getMessage().getContentDisplay());

        for (Command cmd : cmds) {

            if (event.getMessage().getContentDisplay().startsWith(prefix + cmd.commandName)) {

                if (cmd.botPerms.length != 0) {
                    if (!event.getGuild().getSelfMember().hasPermission(cmd.botPerms)) {
                        ArrayList<String> perms = new ArrayList<>();
                        for (Permission perm : cmd.botPerms) perms.add(perm.getName());
                        event.getChannel().sendMessage("the bot does not have the permissions it needs to run this. It needs: `" + String.join(", ", perms) + "`").queue();
                        return;
                    }
                }
                if (cmd.clientPerms.length != 0) {
                    if (!event.getGuild().getSelfMember().hasPermission(cmd.clientPerms)) {
                        ArrayList<String> perms = new ArrayList<>();
                        for (Permission perm : cmd.clientPerms) perms.add(perm.getName());
                        event.getChannel().sendMessage("You don't have the permissions you need to run this. You need: `" + String.join(", ", perms) + "`").queue();
                        return;
                    }
                }

                System.out.println("running " + cmd.commandName);
                ArrayList<String> split = new ArrayList<>();
                Collections.addAll(split, event.getMessage().getContentRaw().split(" "));
                split.remove(0);

                cmd.execute(event, split);
            } else if (cmd.commandAliases != null) {
                for (String alias : cmd.commandAliases) {

                    if (event.getMessage().getContentDisplay().startsWith(prefix + alias)) {

                        if (cmd.botPerms.length != 0) {
                            if (!event.getGuild().getSelfMember().hasPermission(cmd.botPerms)) {
                                ArrayList<String> perms = new ArrayList<>();
                                for (Permission perm : cmd.botPerms) perms.add(perm.getName());
                                event.getChannel().sendMessage("the bot does not have the permissions it needs to run this. It needs: `" + String.join(", ", perms) + "`").queue();
                                return;
                            }
                        }
                        if (cmd.clientPerms.length != 0) {
                            if (!event.getGuild().getSelfMember().hasPermission(cmd.clientPerms)) {
                                ArrayList<String> perms = new ArrayList<>();
                                for (Permission perm : cmd.clientPerms) perms.add(perm.getName());
                                event.getChannel().sendMessage("You don't have the permissions you need to run this. You need: `" + String.join(", ", perms) + "`").queue();
                                return;
                            }
                        }

                        ArrayList<String> split = new ArrayList<>();
                        Collections.addAll(split, event.getMessage().getContentRaw().split(" "));
                        split.remove(0);

                        cmd.execute(event, split);
                    }
                }
            }
        }
    }

}
