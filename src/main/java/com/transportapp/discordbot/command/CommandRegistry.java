package com.transportapp.discordbot.command;

import com.transportapp.discordbot.Transport;
import lombok.Getter;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Pierre Schwang
 * @date 03.08.2018
 */

@Getter
public class CommandRegistry {

    private List<Command> commands;

    /**
     * Default constructor
     *  -> Registers all commands.
     */

    public CommandRegistry() {
        this.commands = new ArrayList<>();
        final Reflections reflections = new Reflections("com.transportapp.discordbot.command.commands");
        reflections.getSubTypesOf(Command.class).forEach(this::registerCommand);
    }

    /**
     * Registers a command with a command object.
     *
     * @param command   The command object.
     */

    private void registerCommand(Command command) {
        if (commands.stream().anyMatch(command1 -> command1.getCommand().equals(command.getCommand())))
            return;
        commands.add(command);
        Transport.getLogger().info("[+] Command: ." + command.getCommand());
    }

    /**
     * Registers a command with a command class.
     *
     * @param commandClass  The command class.
     */

    private void registerCommand(Class<? extends Command> commandClass) {
        try {
            registerCommand(commandClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
