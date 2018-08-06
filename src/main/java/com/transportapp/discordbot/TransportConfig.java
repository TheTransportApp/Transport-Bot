package com.transportapp.discordbot;

import java.io.*;
import java.util.Properties;

/**
 * @author Pierre Schwang
 * @date 06.08.2018
 */

public class TransportConfig {

    private final File configFile;
    private final Properties properties;

    public TransportConfig() {
        configFile = new File(".", "config.properties");
        if (configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        properties = new Properties();
        try (final FileReader fileReader = new FileReader(configFile)) {
            properties.load(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefault(String key, String value) {
        this.properties.putIfAbsent(key, value);
        try (final FileWriter fileWriter = new FileWriter(configFile)) {
            this.properties.store(fileWriter, "Tranport-Bot by Pierre Schwang");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(Object key) {
        return (String) this.properties.get(key);
    }

}
