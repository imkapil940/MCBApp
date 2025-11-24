package com.crestech.appium.utils;

import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class PcloudyUtils {

    public static String getSessionVideoLink(String email, String apiKey, String sessionId) {
        String videoUrl = null;
        try {
            URL url = new URL("https://private-poc.pcloudy.com/api/getSessionVideoLink");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject request = new JSONObject();
            request.put("email", email);
            request.put("apiKey", apiKey);
            request.put("session", sessionId);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(request.toString().getBytes());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) response.append(line);

            JSONObject json = new JSONObject(response.toString());
            if (json.getBoolean("result")) {
                videoUrl = json.getString("video_url");
                System.out.println(" Video URL: " + videoUrl);
            } else {
                System.out.println(" Failed to fetch video link: " + json.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return videoUrl;
    }
}
