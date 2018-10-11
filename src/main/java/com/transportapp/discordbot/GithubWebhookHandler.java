package com.transportapp.discordbot;

import org.json.JSONObject;
import spark.Spark;

/**
 * @author Pierre Schwang
 * @date 09.08.2018
 */
public class GithubWebhookHandler {

    public GithubWebhookHandler() {
        System.out.println("GitHub Webhook receiver running on :1337");
        Spark.port(1337);
        Spark.post("/github-webhook", (req, res) -> {
            String eventName = req.headers("X-GitHub-Event");
            JSONObject jsonObject = new JSONObject(req.body());
            Transport.getLogger().info(eventName);
            Transport.getLogger().info(jsonObject.toString());
            return "";
        });
        Spark.get("/*", (request, response) -> {
            response.header("Content-Type", "application/json");
            return "{\"message\": \"What are you doing here?\"}";
        });
    }
}
