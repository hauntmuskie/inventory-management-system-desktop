package com.lestarieragemilang.app.desktop.AI;

import com.cohere.api.Cohere;
import com.cohere.api.core.ApiError;
import com.cohere.api.requests.ChatRequest;
import com.cohere.api.types.NonStreamedChatResponse;
import io.github.cdimascio.dotenv.Dotenv;

public class CohereAI {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("COHERE_API_KEY");

    public static void main(String[] args) {
        String message = "hai, apa kabar?";
        try {
            String responseText = getChatResponse(message);
            System.out.println(responseText);
        } catch (Exception e) {
            // Consider logging the error or rethrowing a custom exception
            e.printStackTrace();
        }
    }

    /**
     * Fetches the chat response for a given message.
     * 
     * @param message The message to send.
     * @return The response text.
     * @throws Exception If an error occurs during the chat request.
     */
    public static String getChatResponse(String message) {
        try {
            Cohere cohere = Cohere.builder().token(API_KEY).clientName("snippet").build();
            NonStreamedChatResponse response = cohere.chat(
                    ChatRequest.builder()
                            .message(message)
                            .build());
            return response.getText();
        } catch (ApiError error) {
            System.out.println(error.body());
            System.out.println(error.statusCode());
            return "An error occurred while fetching the chat response.";
        }
    }
}
