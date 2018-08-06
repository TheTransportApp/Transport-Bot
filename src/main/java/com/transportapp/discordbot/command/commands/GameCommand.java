package com.transportapp.discordbot.command.commands;

import com.transportapp.discordbot.Transport;
import com.transportapp.discordbot.command.Command;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Message;

import java.awt.*;
import java.util.Arrays;
import java.util.StringJoiner;

/**
 * @author Pierre Schwang
 * @date 04.08.2018
 */

public class GameCommand extends Command {

    public GameCommand() {
        super("game",
                "Team",
                "Ändert das Spiel, welches vom Bot ausgeführt wird.",
                false,
                null);
    }

    @Override
    public void execute(Message message, String[] args) {
        if (args.length == 0) {
            message.getChannel().sendMessage(buildMessage("Falscher Syntax", ".game <Text>", Color.RED)).queue();
            return;
        }
        StringJoiner stringJoiner = new StringJoiner(" ");
        Arrays.stream(args).forEach(stringJoiner::add);
        Transport.getInstance().getJda().getPresence().setPresence(Game.playing(stringJoiner.toString()), true);
    }

}