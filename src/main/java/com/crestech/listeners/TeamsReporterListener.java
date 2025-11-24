package com.crestech.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.crestech.base.TeamsConfig;

import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class TeamsReporterListener implements ITestListener {

    private final HttpClient httpClient;
    private final String webhookUrl;

    // Counters
    int passedCount = 0;
    int failedCount = 0;
    int skippedCount = 0;

    long suiteStartTime;
    long suiteEndTime;
    String testClassName = "";

    public TeamsReporterListener() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.webhookUrl = loadWebhookUrlFromConfig();
    }

    private String loadWebhookUrlFromConfig() {
        try {
            String url;

            url = System.getProperty("teams.webhook.url");
            if (url != null && !url.isBlank()) return url.trim();

            url = System.getenv("TEAMS_WEBHOOK_URL");
            if (url != null && !url.isBlank()) return url.trim();

            try (var is = getClass().getClassLoader().getResourceAsStream("teams.properties")) {
                if (is != null) {
                    Properties props = new Properties();
                    props.load(is);
                    url = props.getProperty("teams.webhook.url");
                    if (url != null && !url.isBlank()) return url.trim();
                }
            } catch (Exception ignored) {}

            Path external = Path.of(System.getProperty("user.home"), ".mcb", "config.properties");
            if (Files.exists(external)) {
                Properties p = new Properties();
                try (var is = Files.newInputStream(external)) {
                    p.load(is);
                    url = p.getProperty("teams.webhook.url");
                    if (url != null && !url.isBlank()) return url.trim();
                }
            }

            System.err.println("Teams webhook not found.");
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static void sendMessageToTeams(String message) {
        try {
            String webhookUrl = TeamsConfig.getWebhookUrl();

            if (webhookUrl == null || webhookUrl.isEmpty()) {
                System.out.println("❌ Teams webhook is empty!");
                return;
            }

            URL url = new URL(webhookUrl);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Only simple single-message JSON
            String payload = "{ \"text\": \"" + message.replace("\"", "'") + "\" }";

            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.getBytes("UTF-8"));
            }

            System.out.println("Teams Response Code: " + conn.getResponseCode());

        } catch (Exception e) {
            System.out.println("❌ Error sending Teams message: " + e.getMessage());
        }
    }

    // 🚫 REMOVE ALL PER-TEST NOTIFICATIONS — ONLY UPDATE COUNTS
    @Override
    public void onTestStart(ITestResult result) {}

    @Override
    public void onTestSuccess(ITestResult result) {
        passedCount++;
    }

    @Override
    public void onTestFailure(ITestResult result) {
        failedCount++;
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        skippedCount++;
    }

    // Start Suite
    @Override
    public void onStart(ITestContext context) {
        suiteStartTime = System.currentTimeMillis();
        testClassName = context.getName();
        System.out.println("🔥 TeamsReporterListener Suite Started: " + testClassName);
    }

    // FINAL SINGLE MESSAGE
    @Override
    public void onFinish(ITestContext context) {

        suiteEndTime = System.currentTimeMillis();
        long duration = suiteEndTime - suiteStartTime;

        int totalTests = passedCount + failedCount + skippedCount;

        String execTime = formatDuration(duration);

        String message =
                "📊 *Automation Run Summary*\n" +
                "-------------------------------------\n" +
                "• Test Suite: *" + testClassName + "*\n" +
                "• Total Testcases: *" + totalTests + "*\n" +
                "• Passed: ✅ *" + passedCount + "*\n" +
                "• Failed: ❌ *" + failedCount + "*\n" +
                "• Skipped: ⚠️ *" + skippedCount + "*\n" +
                "• Execution Time: ⏱️ *" + execTime + "*";

        System.out.println("🔥🔥🔥 Sending FINAL summary to Teams...");
        sendMessageToTeams(message);
    }

    // Format hh:mm:ss
    public static String formatDuration(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;

        return minutes + "m " + remainingSeconds + "s";
    }

    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

}
