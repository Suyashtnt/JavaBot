package commandHandler;

import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class Command {

    public Permission[] botPerms = {};
    public Permission[] clientPerms = {};

    protected void execute(@NotNull MessageReceivedEvent event, ArrayList<String> args) throws Exception {
        System.out.println("running " + Arrays.toString(this.getClass().getAnnotation(CommandInfo.class).name()));
    }

}

