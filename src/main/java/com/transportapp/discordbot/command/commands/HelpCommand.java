package com.transportapp.discordbot.command.commands;

import com.transportapp.discordbot.Transport;
import com.transportapp.discordbot.command.Command;
import net.dv8tion.jda.core.entities.Message;

import java.awt.*;
import java.util.StringJoiner;

/**
 * @author Pierre Schwang
 * @date 03.08.2018
 */

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help",
                "",
                "Listet alle Kommandos auf",
                false,
                new String[]{"hilfe"});
    }

    @Override
    public void execute(Message message, String[] args) {
        if(!message.getChannel().getName().toLowerCase().contains("bot")) return;
        StringJoiner stringJoiner = new StringJoiner("\n");
        Transport.getInstance().getCommandRegistry().getCommands().forEach(command -> stringJoiner.add("!" + command.getCommand() + " - " + command.getDescription()));
        message.getChannel().sendMessage(buildMessage(
                "Hilfe",
                stringJoiner.toString(),
                new Color(14, 119, 255)
        )).queue();
    }

}
