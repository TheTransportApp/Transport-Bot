package com.transportapp.discordbot.command.commands;

import com.transportapp.discordbot.command.Command;
import net.dv8tion.jda.core.entities.Message;

/**
 * @author Pierre Schwang
 * @date 06.08.2018
 */

public class BetaCommand extends Command {

    public BetaCommand() {
        super("beta", "", "", true, new String[]{});
    }

    @Override
    public void execute(Message message, String[] args) {

    }

}