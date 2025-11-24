package com.crestech.base;

import java.util.Properties;

public class TeamsConfig {

    private static String webhook;

    static {
        try {
            Properties props = new Properties();
            props.load(TeamsConfig.class.getClassLoader().getResourceAsStream("teams.properties"));
            webhook = props.getProperty("teams.webhook.url");
        } catch (Exception e) {
            System.out.println("❌ Could not load teams.properties");
        }
    }

    public static String getWebhookUrl() {
        return webhook;
    }
}

