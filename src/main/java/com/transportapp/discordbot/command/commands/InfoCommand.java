package com.transportapp.discordbot.command.commands;

import com.transportapp.discordbot.Transport;
import com.transportapp.discordbot.command.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;

public class InfoCommand extends Command {

    public InfoCommand() {
        super("info", "", "Informationen über den Bot und den Server", false, new String[]{});
    }

    @Override
    public void execute(Message message, String[] args) {
        if (!message.getChannel().getName().toLowerCase().contains("bot")) return;
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.addField("Version", Transport.getInstance().getVERSION(), true);
        embedBuilder.addField("Autor", "Pierre Maurice Schwang", true);
        embedBuilder.addField("Source", "https://github.com/TheTransportApp/Transport-Bot", true);

        embedBuilder.addField("Mitglieder (Discord)", String.valueOf(Transport.getInstance().getJda().getGuildById(455039767151902722L).getMembers().size()), true);
        embedBuilder.addField("Beta Mitglieder (Discord)", String.valueOf((int) Transport.getInstance().getJda().getGuildById(455039767151902722L).getMembers().stream().filter(member -> member.getRoles().stream().anyMatch(role -> role.getName().equals("Beta"))).count()), true);
        message.getChannel().sendMessage(embedBuilder.build()).queue();
    }

}