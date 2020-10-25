import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import commandHandler.Handler;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {

    static Handler handler;

    private static final EventWaiter waiter = new EventWaiter();

    static {
        handler = new Handler("com.tnt_man_inc.JavaBot2.commands", "!", waiter);
    }

    public static void main(String[] args) throws LoginException {

	    JDABuilder builder = JDABuilder.create(args[0], GatewayIntent.GUILD_MEMBERS, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES);
        builder.addEventListeners(new Main());
        builder.addEventListeners(waiter);
        builder.build();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        try {
            handler.messageReceived(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}