package com.crestech.common.api;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.Map;

public class QaseAPIClient {

    private final String baseUrl = "https://api.qase.io/v1";
    private final String token;
    private final String projectCode;
    private int runId;

    public QaseAPIClient(String projectCode, String token) {
        this.projectCode = projectCode;
        this.token = token;
        RestAssured.baseURI = baseUrl;
    }

    // ✅ 1. Create a new Run
    public void createRun(String runName) {
        JSONObject body = new JSONObject();
        body.put("title", runName);

        Response response = given()
                .header("Token", token)
                .header("Content-Type", "application/json")
                .body(body.toString())
                .post("/run/" + projectCode);

        JsonPath json = response.jsonPath();
        if (json.getBoolean("status")) {
            runId = json.getInt("result.id");
            System.out.println("✅ Created Run ID: " + runId);
        } else {
            System.out.println("❌ Failed to create run: " + response.asString());
        }
    }

    // ✅ 2. Add Test Result (Passed / Failed)
    public void addTestResult(int caseId, String status, String comment) {
        if (runId == 0) {
            System.out.println("❌ Run not created yet!");
            return;
        }

        JSONObject body = new JSONObject();
        body.put("case_id", caseId);
        body.put("status", status.toLowerCase());
        body.put("comment", comment);

        Response response = given()
                .header("Token", token)
                .header("Content-Type", "application/json")
                .body(body.toString())
                .post("/result/" + projectCode + "/" + runId);

        System.out.println("🔸 Add Result Response: " + response.asString());
    }

    // ✅ 3. Get Run Summary
    public void getRunSummary(String projectCode, int runId) {
    	try {
            String url = baseUrl + "/run/" + projectCode + "/" + runId;

            Response response = RestAssured.given()
                    .header("Content-Type", "application/json")
                    .header("Token", token)
                    .get(url)
                    .then()
                    .extract()
                    .response();

            if (response.statusCode() != 200) {
                System.out.println("❌ Qase getRunSummary() failed. Status: " + response.statusCode());
                System.out.println("Response: " + response.getBody().asString());
                return;
            }

            JsonPath json = response.jsonPath();
            System.out.println("📊 Run Summary API Response: " + response.asString());

            boolean status = json.getBoolean("status");
            if (status) {
                Map<Object, Object> result = json.getMap("result");
                int total = result.containsKey("total") ? (int) result.get("total") : 0;
                int passed = result.containsKey("passed") ? (int) result.get("passed") : 0;
                int failed = result.containsKey("failed") ? (int) result.get("failed") : 0;

                System.out.println("✅ Run Summary:");
                System.out.println("Total Test Cases: " + total);
                System.out.println("Passed: " + passed);
                System.out.println("Failed: " + failed);
            } else {
                System.out.println("⚠️ Failed to fetch summary: " + json.getString("errorMessage"));
            }
        } catch (Exception e) {
            System.out.println("❌ Error in getRunSummary(): " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void updateTestResult(String projectCode, int runId, String caseId, String status) throws Exception {
        String url = baseUrl + "/result/" + projectCode;

        JSONObject body = new JSONObject();
        body.put("run_id", runId);
        body.put("case_id", caseId);
        body.put("status", status); // "passed", "failed", "blocked", etc.

        RequestSpecification request = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Token", token)
                .body(body.toString());

        Response response = request.post(url);

        if (response.statusCode() == 200 && response.jsonPath().getBoolean("status")) {
            System.out.println("✅ Test case " + caseId + " updated as " + status + " in Qase");
        } else {
            System.err.println("❌ Failed to update " + caseId + " in Qase. Response: " + response.asString());
        }
    }

 // ✅ 4. Complete Run (Mark Run as Finished)
    public void completeRun(String string, int runId) {
        if (runId == 0) {
            System.out.println("❌ Run not created yet!");
            return;
        }

        Response response = given()
                .header("Token", token)
                .post("/run/" + projectCode + "/" + runId + "/complete");

        if (response.statusCode() == 200 && response.jsonPath().getBoolean("status")) {
            System.out.println("Test Run marked as COMPLETED successfully in Qase.");
        } else {
            System.out.println("Failed to complete run: " + response.asString());
        }
    }
    
    public void addTestResultWithTitle(int caseId, String status, String comment, String title) throws IOException {
        String runId = String.valueOf(this.runId);
        String endpoint = String.format("/v1/result/%s/%s", projectCode, runId);

        String jsonPayload = String.format(
            "{\"case_id\": %d, \"status\": \"%s\", \"comment\": \"%s\", \"title\": \"%s\"}",
            caseId, status, comment.replace("\"", "'"), title.replace("\"", "'")
        );

        HttpURLConnection conn = (HttpURLConnection) new URL(baseUrl + endpoint).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Token", token);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (java.io.OutputStream os = conn.getOutputStream()) {
            os.write(jsonPayload.getBytes());
            os.flush();
        }

        int code = conn.getResponseCode();
        System.out.println("Qase title update response: " + code);
        conn.disconnect();
    }

    public void updateTestResultWithTitle(String projectCode, int runId, String caseId, String status, String title) throws IOException {
        String endpoint = String.format("/v1/result/%s/%s", projectCode, runId);

        String jsonPayload = String.format(
            "{\"case_id\": \"%s\", \"status\": \"%s\", \"title\": \"%s\"}",
            caseId, status, title.replace("\"", "'")
        );

        HttpURLConnection conn = (HttpURLConnection) new URL(baseUrl + endpoint).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Token", token);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (java.io.OutputStream os = conn.getOutputStream()) {
            os.write(jsonPayload.getBytes());
            os.flush();
        }

        int code = conn.getResponseCode();
        System.out.println("Qase title update (string ID) response: " + code);
        conn.disconnect();
    }


    public int getRunId() {
        return runId;
    }
}
