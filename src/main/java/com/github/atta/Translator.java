package com.github.atta;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class Translator {
    private static final String RAPIDAPI_KEY = "RAPIDAPI_KEY";

    public static String translate(String inputText, String source, String target)
            throws IOException, InterruptedException {
        String payload = "q=" + URLEncoder.encode(inputText, StandardCharsets.UTF_8) + "&source=" + source + "&target="
                + target;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://google-translate1.p.rapidapi.com/language/translate/v2"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("accept-encoding", "application/gzip").header("x-rapidapi-key", RAPIDAPI_KEY)
                .header("x-rapidapi-host", "google-translate1.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString(payload)).build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonObj = new JSONObject(response.body());
        String translatedText = jsonObj.getJSONObject("data").getJSONArray("translations").getJSONObject(0)
                .getString("translatedText");

        return translatedText;
    }
}
