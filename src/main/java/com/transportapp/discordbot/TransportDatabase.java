package com.transportapp.discordbot;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

/**
 * @author Pierre Schwang
 * @date 07.08.2018
 */

@Getter
public class TransportDatabase {

    private HikariDataSource hikariDataSource;

    /**
     * Constructor initializes the default {@link HikariConfig HikariConfig}.
     */

    public TransportDatabase() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC&useSSL=false",
                Transport.getInstance().getTransportConfig().get("MySQL-host"),
                Transport.getInstance().getTransportConfig().get("MySQL-port"),
                Transport.getInstance().getTransportConfig().get("MySQL-database")));
        hikariConfig.setUsername(Transport.getInstance().getTransportConfig().get("MySQL-username"));
        hikariConfig.setPassword(Transport.getInstance().getTransportConfig().get("MySQL-password"));
        hikariDataSource = new HikariDataSource(hikariConfig);
    }

    /**
     * Checks if the parameters are valid and if the user is a valid beta tester.
     * @param name Name of the user
     * @param email Email address assigned to the user (name)
     * @param validBetaUser {@link Consumer consumer} which holds the boolean if the user is valid.
     */

    public void isValidBetaUser(String name, String email, Consumer<Boolean> validBetaUser) {
        if (name == null || email == null || name.equals("") || email.equals("") || !email.contains("@")) {
            validBetaUser.accept(false);
            return;
        }
        try (final Connection connection = Transport.getInstance().getTransportDatabase().getHikariDataSource().getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM beta_test WHERE `email` = ? and `name` = ?")) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, name);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                validBetaUser.accept(resultSet.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
