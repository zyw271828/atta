package com.github.atta;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class Translator {
    private static final String RAPIDAPI_KEY = "RAPIDAPI_KEY";

    public static String translate(String inputText, String source, String target) throws IOException {
        if (checkNetwork("https://rapidapi.com")) {
            String payload = "q=" + URLEncoder.encode(inputText, StandardCharsets.UTF_8) + "&source=" + source
                    + "&target=" + target;
            JSONObject jsonObj = null;
            String translatedText = "";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://google-translate1.p.rapidapi.com/language/translate/v2"))
                    .header("content-type", "application/x-www-form-urlencoded")
                    .header("accept-encoding", "application/gzip").header("x-rapidapi-key", RAPIDAPI_KEY)
                    .header("x-rapidapi-host", "google-translate1.p.rapidapi.com")
                    .method("POST", HttpRequest.BodyPublishers.ofString(payload)).build();

            try {
                HttpResponse<String> response = HttpClient.newHttpClient().send(request,
                        HttpResponse.BodyHandlers.ofString());
                jsonObj = new JSONObject(response.body());
                translatedText = jsonObj.getJSONObject("data").getJSONArray("translations").getJSONObject(0)
                        .getString("translatedText");
            } catch (Exception e) {
                throw new IOException(jsonObj.toString());
            }

            return translatedText;
        }

        return null;
    }

    private static boolean checkNetwork(String domain) throws IOException {
        try {
            URL url = new URL(domain);
            URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(domain + " is unreachable, please check your network.");
        }
    }
}
