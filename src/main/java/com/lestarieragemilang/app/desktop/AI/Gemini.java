package com.lestarieragemilang.app.desktop.AI;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.text.TextContentRenderer;

public class Gemini {
    private static final Dotenv dotenv = Dotenv.load();

    private static final String API_KEY = dotenv.get("GEMINI_API_KEY");
    private static final String ENDPOINT_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-pro-latest:generateContent?key="
            + API_KEY;
    private static final Gson gson = new Gson();

    private static final String PROMPT = "What is the capital of France?";

    public static void main(String[] args) {
        try {
            String response = sendRequest(PROMPT);
            String result = processResponse(response);
            System.out.println("Response: " + result);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    protected static String sendRequest(String prompt) throws Exception {
        URI uri = new URI(ENDPOINT_URL);
        URL url = uri.toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        String jsonInputString = createRequestBody(prompt);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        }
    }

    private static String createRequestBody(String prompt) {
        JsonObject requestBody = new JsonObject();
        JsonObject content = new JsonObject();
        JsonObject part = new JsonObject();

        part.addProperty("text", prompt);
        JsonArray parts = new JsonArray();
        parts.add(part);
        content.add("parts", parts);
        content.addProperty("role", "user");
        JsonArray contents = new JsonArray();
        contents.add(content);
        requestBody.add("contents", contents);

        JsonObject generationConfig = new JsonObject();
        generationConfig.addProperty("temperature", 1);
        generationConfig.addProperty("topK", 0);
        generationConfig.addProperty("topP", 0.95);
        generationConfig.addProperty("maxOutputTokens", 8192);
        requestBody.add("generationConfig", generationConfig);

        JsonObject safetySettings = new JsonObject();
        safetySettings.addProperty("category", "HARM_CATEGORY_HARASSMENT");
        safetySettings.addProperty("threshold", "BLOCK_MEDIUM_AND_ABOVE");
        JsonArray safetySettingsArray = new JsonArray();
        safetySettingsArray.add(safetySettings);
        requestBody.add("safetySettings", safetySettingsArray);

        return gson.toJson(requestBody);
    }

    protected static String processResponse(String jsonResponse) {
        JsonObject responseObject = gson.fromJson(jsonResponse, JsonObject.class);
        JsonArray candidatesArray = responseObject.getAsJsonArray("candidates");

        StringBuilder result = new StringBuilder();
        Parser parser = Parser.builder().build();
        TextContentRenderer renderer = TextContentRenderer.builder().build();

        for (int i = 0; i < candidatesArray.size(); i++) {
            JsonObject candidate = candidatesArray.get(i).getAsJsonObject();
            JsonObject content = candidate.getAsJsonObject("content");
            JsonArray parts = content.getAsJsonArray("parts");

            for (int j = 0; j < parts.size(); j++) {
                String markdownText = parts.get(j).getAsJsonObject().get("text").getAsString();

                Node document = parser.parse(markdownText);

                String plainText = renderer.render(document);
                result.append(plainText);
            }
        }
        return result.toString();
    }
}