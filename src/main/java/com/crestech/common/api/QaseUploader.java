package com.crestech.common.api;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class QaseUploader {

    private static final String BASE_URL = "https://api.qase.io/v1";
    private static final OkHttpClient client = new OkHttpClient();

    // 🔹 Step 1: Create new Qase test run (if not already exists)
    public static String createTestRun(String projectCode, String apiToken) {
        try {
            JSONObject body = new JSONObject();
            body.put("title", "Automated Run - " + LocalDateTime.now());
            body.put("description", "Extent Report Execution from Appium Framework");
            body.put("include_all", true);

            Request request = new Request.Builder()
                    .url(BASE_URL + "/run/" + projectCode)
                    .addHeader("Token", apiToken)
                    .addHeader("Content-Type", "application/json")
                    .post(RequestBody.create(body.toString(), MediaType.parse("application/json")))
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                JSONObject json = new JSONObject(response.body().string());
                String runId = json.getJSONObject("result").get("id").toString();
                System.out.println("✅ Created Qase Run ID: " + runId);
                return runId;
            } else {
                System.err.println("❌ Failed to create run: " + response.code() + " - " + response.message());
            }
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔹 Step 2: Get latest existing run (if you don’t want to create new one)
    public static String getLatestRunId(String projectCode, String apiToken) {
        try {
            Request request = new Request.Builder()
                    .url(BASE_URL + "/run/" + projectCode + "?limit=1&offset=0")
                    .addHeader("Token", apiToken)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                JSONObject json = new JSONObject(response.body().string());
                JSONArray runs = json.getJSONObject("result").getJSONArray("entities");
                if (runs.length() > 0) {
                    JSONObject latestRun = runs.getJSONObject(0);
                    String runId = latestRun.get("id").toString();
                    System.out.println("📦 Latest existing Qase Run ID: " + runId);
                    return runId;
                } else {
                    System.err.println("⚠️ No existing runs found for project " + projectCode);
                }
            } else {
                System.err.println("❌ Failed to fetch runs: " + response.code());
            }
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔹 Step 3: Upload ZIP file to Qase → get file hash
    public static String uploadAttachment(String projectCode, String apiToken, String zipFilePath) {
        try {
            File file = new File(zipFilePath);
            if (!file.exists()) {
                System.err.println("❌ ZIP file not found: " + zipFilePath);
                return null;
            }

            RequestBody fileBody = RequestBody.create(file, MediaType.parse("application/zip"));
            MultipartBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(), fileBody)
                    .build();

            Request request = new Request.Builder()
                    .url(BASE_URL + "/attachment/" + projectCode)
                    .addHeader("Token", apiToken)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                JSONObject json = new JSONObject(response.body().string());
                String hash = json.getJSONObject("result").getString("hash");
                System.out.println("✅ Uploaded ZIP. File Hash: " + hash);
                return hash;
            } else {
                System.err.println("❌ Upload failed: " + response.code() + " - " + response.message());
            }
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔹 Step 4: Attach uploaded file to Qase run
    public static void attachFileToRun(String projectCode, String runId, String apiToken, String fileHash) {
        try {
            JSONObject body = new JSONObject();
            body.put("attachments", new String[]{fileHash});

            Request request = new Request.Builder()
                    .url(BASE_URL + "/run/" + projectCode + "/" + runId + "/attachments")
                    .addHeader("Token", apiToken)
                    .addHeader("Content-Type", "application/json")
                    .post(RequestBody.create(body.toString(), MediaType.parse("application/json")))
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println("✅ Attached ZIP file to Qase run " + runId);
            } else {
                System.err.println("❌ Failed to attach file: " + response.code() + " - " + response.message());
            }
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 Step 5: Mark the run as completed
    public static void closeRun(String projectCode, String runId, String apiToken) {
        try {
            JSONObject body = new JSONObject();
            body.put("status", "completed");

            Request request = new Request.Builder()
                    .url(BASE_URL + "/run/" + projectCode + "/" + runId + "/complete")
                    .addHeader("Token", apiToken)
                    .addHeader("Content-Type", "application/json")
                    .post(RequestBody.create(body.toString(), MediaType.parse("application/json")))
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println("✅ Run " + runId + " marked as completed.");
            } else {
                System.err.println("❌ Failed to close run: " + response.code());
            }
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 Step 6: Smart upload handler (auto-uses latest run or creates new one)
    public static void uploadExtentReportToQase(String projectCode, String apiToken, String reportFolderPath, boolean useExistingRun) {
        try {
            // Zip the Extent Report folder
            String zipPath = ZipUtils.zipFolder(reportFolderPath, reportFolderPath + ".zip");

            // Choose runId source
            String runId;
            if (useExistingRun) {
                runId = getLatestRunId(projectCode, apiToken);
                if (runId == null) {
                    System.out.println("⚠️ No existing run found, creating new one...");
                    runId = createTestRun(projectCode, apiToken);
                }
            } else {
                runId = createTestRun(projectCode, apiToken);
            }

            // Upload and attach ZIP
            String fileHash = uploadAttachment(projectCode, apiToken, zipPath);
            if (fileHash != null && runId != null) {
                attachFileToRun(projectCode, runId, apiToken, fileHash);
                closeRun(projectCode, runId, apiToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void createResultWithAttachment(
            String projectCode,
            String apiToken,
            String runId,
            String caseId,
            String status,
            String comment,
            String attachmentHash) {
        try {
            JSONObject body = new JSONObject();
            body.put("run_id", Integer.parseInt(runId));
            body.put("case_id", Integer.parseInt(caseId));
            body.put("status", status);
            body.put("comment", comment);
            body.put("attachments", new String[]{attachmentHash});

            Request request = new Request.Builder()
                    .url(BASE_URL + "/result/" + projectCode)
                    .addHeader("Token", apiToken)
                    .addHeader("Content-Type", "application/json")
                    .post(RequestBody.create(body.toString(), MediaType.parse("application/json")))
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println("✅ New result created with attachment!");
                System.out.println("Response: " + response.body().string());
            } else {
                System.err.println("❌ Failed to create result: " + response.code());
                System.err.println(response.body().string());
            }
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 // 🔹 Step 7: Update existing test result and attach ExtentReport.zip
    public static void updateResultWithAttachment(
            String projectCode,
            String apiToken,
            String resultId,
            String runId,
            String caseId,
            String status,
            String comment,
            String attachmentHash) {

        try {
            JSONObject body = new JSONObject();
            body.put("run_id", Integer.parseInt(runId));
            body.put("case_id", Integer.parseInt(caseId));
            body.put("status", status);
            body.put("comment", comment);
            body.put("attachments", new String[]{attachmentHash});

            Request request = new Request.Builder()
                    .url(BASE_URL + "/result/" + projectCode + "/" + resultId)
                    .addHeader("Token", apiToken)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .patch(RequestBody.create(body.toString(), MediaType.parse("application/json")))
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                System.out.println("✅ Result " + resultId + " updated with attachment for run " + runId);
                System.out.println("Response: " + response.body().string());
            } else {
                System.err.println("❌ Failed to update result: " + response.code() + " - " + response.message());
                if (response.body() != null) {
                    System.err.println(response.body().string());
                }
            }
            response.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void attachToResult(String projectCode, String resultId, String apiToken, String fileHash) {
        try {
            JSONObject body = new JSONObject();
            body.put("attachments", new JSONArray().put(fileHash));

            Request request = new Request.Builder()
                .url(BASE_URL + "/result/" + projectCode + "/" + resultId)
                .addHeader("Token", apiToken)
                .addHeader("Content-Type", "application/json")
                .patch(RequestBody.create(body.toString(), MediaType.parse("application/json")))
                .build();

            Response response = client.newCall(request).execute();
            System.out.println("📎 Attachment Response: " + response.body().string());
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
