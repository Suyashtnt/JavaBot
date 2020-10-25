package commandHandler;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.reflections8.Reflections;

import java.lang.reflect.Constructor;
import java.util.*;

public class Handler {
	public String prefix;
	public String path = "";
	public ArrayList<Command> cmds = new ArrayList<>();
	Reflections reflections = new Reflections(path);

	Timer timer = new Timer();

	Set<Class<? extends Command>> classes = reflections.getSubTypesOf(Command.class);
	HashMap<String, String> cooldownTimes = new HashMap<String, String>() {
		@Override
		public int size() {
			return 50;
		}
	};

	public Handler(@NotNull String locationOfCommands, @NotNull String commandPrefix, @NotNull EventWaiter waiter) {
		prefix = commandPrefix;
		path = locationOfCommands;

		for (Class<? extends Command> aClass : classes) {
			try {
				System.out.println("loading " + aClass.getName());
				Constructor<? extends Command> commandConstructor = aClass.getConstructor(EventWaiter.class);
				Command classInstance = commandConstructor.newInstance(waiter);
				cmds.add(classInstance);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void messageReceived(@NotNull MessageReceivedEvent event) throws Exception {
		if (event.getAuthor().isBot()) return;

		System.out.println("We received a message that contains: " + event.getMessage().getContentDisplay());



		for (Command cmd : cmds) {


			for (String alias : cmd.getClass().getAnnotation(CommandInfo.class).name()) {
				String[] split = event.getMessage().getContentDisplay().trim().split(" ");
				if (split[0].equals(prefix + alias)) {

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
					System.out.println("here");
					if (cooldownTimes.containsKey(event.getAuthor().getId()) && cooldownTimes.containsValue((cmd.getClass().getAnnotation(CommandInfo.class).name())[0])) {
						event.getChannel().sendMessage("sorry but you are on time cooldown").queue();
						return;
					}
					ArrayList<String> args = new ArrayList<>();
					Collections.addAll(args, Arrays.copyOfRange(split, 1, split.length));

					cmd.execute(event, args);
					cooldownTimes.put(event.getAuthor().getId(), (cmd.getClass().getAnnotation(CommandInfo.class).name())[0]);

					timer.schedule(new TimerTask() {
						@Override
						public void run() {
							cooldownTimes.remove(event.getAuthor().getId(), (cmd.getClass().getAnnotation(CommandInfo.class).name())[0]);
						}
					}, cmd.cooldown);
					break;
				}
			}

		}
	}

}
