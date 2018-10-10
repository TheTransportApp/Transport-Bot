package com.transportapp.discordbot;

import com.transportapp.discordbot.command.CommandRegistry;
import com.transportapp.discordbot.listener.chat.GuildMessageReceivedListener;
import io.sentry.Sentry;
import lombok.Getter;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

/**
 * @author Pierre Schwang
 * @date 03.08.2018
 */

@Getter
public class Transport {

    private final String VERSION = "1.0-SNAPSHOT";

    @Getter
    private static Transport instance;
    @Getter
    private static final Logger logger = LoggerFactory.getLogger(Transport.class);

    private JDA jda;

    private TransportConfig transportConfig;
    private TransportDatabase transportDatabase;
    private final CommandRegistry commandRegistry;

    private Transport() {
        instance = this;
        initTransportConfig();
        Sentry.init(String.valueOf(transportConfig.get("SentryDSN")));
        logger.info("Starting discord bot...");
        transportDatabase = new TransportDatabase();
        commandRegistry = new CommandRegistry();
        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setStatus(OnlineStatus.ONLINE)
                    .setGame(Game.playing("Transport-App.com"))
                    .setAutoReconnect(true)
                    .setEnableShutdownHook(true)
                    .setToken(String.valueOf(transportConfig.get("DiscordToken")))
                    .build();
            jda.addEventListener(new GuildMessageReceivedListener());

            new Thread(GithubWebhookHandler::new);

            logger.info("Discord bot is online!");
        } catch (LoginException e) {
            Sentry.capture(e);
        }
    }

    private void initTransportConfig() {
        transportConfig = new TransportConfig();
        transportConfig.setDefault("SentryDSN", "");
        transportConfig.setDefault("DiscordToken", "");

        transportConfig.setDefault("MySQL-host", "");
        transportConfig.setDefault("MySQL-port", "3306");
        transportConfig.setDefault("MySQL-database", "");
        transportConfig.setDefault("MySQL-username", "");
        transportConfig.setDefault("MySQL-password", "");
    }

    public static void main(String[] args) {
        new Transport();
    }

}