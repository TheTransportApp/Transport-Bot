package com.transportapp.discordbot.command.commands;

import com.transportapp.discordbot.Transport;
import com.transportapp.discordbot.command.Command;
import net.dv8tion.jda.core.entities.Message;

import java.awt.*;
import java.util.StringJoiner;

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
        if (args.length < 2) {
            message.getChannel().sendMessage(buildMessage("Fehler", "!beta <E-Mail> <Name>", Color.RED)).queue();
            return;
        }

        if (!Transport.getInstance().getJda().getGuildById(455039767151902722L).isMember(message.getAuthor())) {
            message.getChannel().sendMessage(buildMessage("Fehler", "Um den Beta-Rang zu erhalten, musst du Mitglied auf unserem Discord-Server sein!", Color.RED)).queue();
            return;
        }

        if (Transport.getInstance().getJda().getGuildById(455039767151902722L).getMember(message.getAuthor()).getRoles().stream().anyMatch(role -> role.getName().equals("Beta"))) {
            message.getChannel().sendMessage(buildMessage("Fehler", "Du besitzt bereits den Beta-Rang!", Color.RED)).queue();
            return;
        }

        StringJoiner stringJoiner = new StringJoiner(" ");
        for (int i = 1; i < args.length; i++) {
            stringJoiner.add(args[i]);
        }

        Transport.getInstance().getTransportDatabase().isValidBetaUser(stringJoiner.toString(), args[0], validBetaUser -> {
            if(!validBetaUser) {
                message.getChannel().sendMessage(buildMessage("Fehler", "Es wurde kein Profil zu deinen Angaben gefunden, hast du dich schon als Beta-Tester registert? Falls nicht, dann mach es einfach hier: https://transport-app.com/", Color.RED)).queue();
                return;
            }
            message.getChannel().sendMessage(buildMessage("Erfolgreich", "Du erhältst in Kürze den Beta-Rang auf unserem Discord Server! \uD83C\uDF89", Color.GREEN)).queue();
            if(Transport.getInstance().getJda().getGuildById(455039767151902722L).getMember(message.getAuthor()).getRoles().stream().anyMatch(role -> role.getName().equals("Beta"))) {
                message.getChannel().sendMessage(buildMessage("Fehler", "Du bist bereits Beta-Mitglied auf dem Discord-Server!", Color.RED)).queue();
                return;
            }
            Transport.getInstance().getJda().getGuildById(455039767151902722L).getController().addRolesToMember(Transport.getInstance().getJda().getGuildById(455039767151902722L).getMember(message.getAuthor()), Transport.getInstance().getJda().getGuildById(455039767151902722L).getRolesByName("Beta", false).get(0)).queue();
        });

    }

}