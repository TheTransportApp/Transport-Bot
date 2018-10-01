package com.transportapp.discordbot;

import org.json.JSONObject;
import spark.Spark;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Pierre Schwang
 * @date 09.08.2018
 */
public class GithubWebhookHandler {

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public GithubWebhookHandler() {
        Spark.port(1337);
        Spark.post("/github-webhook", (req, res) -> {
            String eventName = req.headers("X-GitHub-Event");
            JSONObject jsonObject = new JSONObject(req.body());
            Transport.getLogger().info(eventName);
            Transport.getLogger().info(jsonObject.toString());
            return "";
        });
    }
}
