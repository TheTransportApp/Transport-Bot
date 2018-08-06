package com.transportapp.discordbot.listener.chat;

import com.transportapp.discordbot.Transport;
import com.transportapp.discordbot.command.Command;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * @author Pierre Schwang
 * @date 03.08.2018
 */

public class GuildMessageReceivedListener extends ListenerAdapter {

    private final Pattern COMMAND_PATTERN = Pattern.compile("!([A-Z,a-z])\\w+");

    /*
     * This method listens for all messages that are received. If the message matches the syntax of a command, it is executed
     */

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        //Validate command syntax
        if (!event.getMessage().getContentRaw().startsWith("!"))
            return;
        String[] splitMessage = event.getMessage().getContentRaw().trim().contains(" ") ? event.getMessage().getContentRaw().trim().split(" ") : new String[]{event.getMessage().getContentRaw().trim()};
        if (!COMMAND_PATTERN.matcher(splitMessage[0]).matches())
            return;

        //Local variables
        String inputCommand = splitMessage[0].substring(1);
        String[] args = Arrays.copyOfRange(splitMessage, 1, splitMessage.length);

        //A command object is created. To do this, the system searches for a command in the registry and matches it to the message
        Command command = Transport.getInstance().getCommandRegistry().getCommands().stream().filter(command1 -> command1.getCommand().equalsIgnoreCase(inputCommand)).findFirst().orElse(null);

        //If no command is found (by comparing the registry with commands), an attempt is made to find a suitable alias in the registry.
        if (command == null) {
            command = Transport.getInstance().getCommandRegistry().getCommands().stream().filter(command1 -> command1.getAliases() != null).filter(command1 -> Arrays.stream(command1.getAliases()).anyMatch(s -> s.equalsIgnoreCase(inputCommand))).findFirst().orElse(null);
        }

        //If a command is found (command != null), it is executed if the user has the correct role.
        if (command != null) {
            if (event.getChannelType() == ChannelType.PRIVATE) {
                if (!command.isPrivateChat()) return;
                command.execute(event.getMessage(), args);
                return;
            }
            final String requiredRole = command.getRequiredRole();
            if (requiredRole.equals("") || event.getMember().getRoles().stream().anyMatch(role -> role.getName().equals(requiredRole))) {
                command.execute(event.getMessage(), args);
                return;
            }
        }
        //unknown command
    }
}
