package com.transportapp.discordbot.command;

import lombok.Data;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;

/**
 * @author Pierre Schwang
 * @date 03.08.2018
 */

@Data
public abstract class Command {

    private final String command, requiredRole, description;
    private final boolean privateChat;
    private final String[] aliases;

    /**
     * This method is called as soon as a message corresponds to a command or command alias.
     *
     * @param message   The {@link Message} object from the JDA api.
     * @param args      The arguments passed. If no arguments were passed, the size of the array is 0.
     */

    public abstract void execute(Message message, String[] args);

    /**
     * Helper method to easily create {@link MessageEmbed}s.
     *
     * @param title     The title of the embed
     * @param content   The content of the embed
     * @return          The final {@link MessageEmbed}
     */

    public MessageEmbed buildMessage(String title, String content) {
        return buildMessage(title, content, Color.BLUE);
    }

    /**
     * Helper method to easily create {@link MessageEmbed}s.
     *
     * @param title     The title of the embed
     * @param content   The content of the embed
     * @param color     The color of the embed
     * @return          The final {@link MessageEmbed}
     */

    public MessageEmbed buildMessage(String title, String content, Color color) {
        return new EmbedBuilder().setTitle(title).setDescription(content).setColor(color).build();
    }

}
