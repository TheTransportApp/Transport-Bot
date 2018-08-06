package com.transportapp.discordbot;

import com.transportapp.discordbot.command.CommandRegistry;
import com.transportapp.discordbot.listener.chat.GuildMessageReceivedListener;
import io.sentry.Sentry;
import lombok.Getter;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

/**
 * @author Pierre Schwang
 * @date 03.08.2018
 */

@Getter
public class Transport {

    @Getter
    private static Transport instance;
    @Getter
    private static final Logger logger = LoggerFactory.getLogger(Transport.class);

    private JDA jda;

    private TransportConfig transportConfig;
    private final CommandRegistry commandRegistry;

    private Transport() {
        initTransportConfig();
        Sentry.init(transportConfig.get("SentryDSN"));
        logger.info("Starting discord bot...");
        commandRegistry = new CommandRegistry();
        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setStatus(OnlineStatus.ONLINE)
                    .setGame(Game.playing("Transport-App.com"))
                    .setAutoReconnect(true)
                    .setEnableShutdownHook(true)
                    .setToken(transportConfig.get("DiscordToken"))
                    .buildBlocking();
            jda.addEventListener(new GuildMessageReceivedListener());
            logger.info("Discord bot is online!");
        } catch (LoginException | InterruptedException e) {
            Sentry.capture(e);
        }
    }

    private void initTransportConfig() {
        transportConfig = new TransportConfig();
        transportConfig.setDefault("SentryDSN", "");
        transportConfig.setDefault("DiscordToken", "");
    }

    public static void main(String[] args) {
        instance = new Transport();
    }

}