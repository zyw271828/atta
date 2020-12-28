package com.github.atta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import org.json.JSONArray;

public class Checker {
    public static String check(String inputText, String invTranslatedText) throws IOException {
        final String jarPath = "./lib/SPICE-1.0/spice-1.0.jar".replaceAll("/", File.separator);
        final String inputFilePath = "./input.json".replaceAll("/", File.separator);
        final String outputFilePath = "./output.json".replaceAll("/", File.separator);

        // Assembly data
        HashMap<String, Object> map = new HashMap<>();
        map.put("image_id", 1);
        map.put("test", inputText);
        map.put("refs", new String[] { invTranslatedText });
        Object[] mapArray = new Object[] { map };
        JSONArray inputJSON = new JSONArray(mapArray);
        String data = inputJSON.toString();

        // Write input to file
        String encoding = "UTF-8";
        File inputFile = new File(inputFilePath);
        inputFile.createNewFile();
        if (inputFile.exists() && inputFile.isFile()) {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(inputFile, false),
                    encoding);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(data);
            bufferedWriter.close();
        } else {
            throw new IOException();
        }

        // Run model
        try {
            Process p = Runtime.getRuntime()
                    .exec(new String[] { "java", "-Xmx8G", "-jar", jarPath, inputFilePath, "-out", outputFilePath });
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String s;
            while ((s = stdInput.readLine()) != null) {
                stringBuilder.append(s);
            }
            s = stringBuilder.toString();
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Read results from file
        StringBuilder resultText = new StringBuilder();
        File outputFile = new File(outputFilePath);
        outputFile.createNewFile();
        if (outputFile.exists() && outputFile.isFile()) {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(outputFile), encoding);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                resultText.append(temp);
            }
            bufferedReader.close();
        } else {
            throw new IOException();
        }

        inputFile.delete();
        outputFile.delete();

        JSONArray jsonArray = new JSONArray(resultText.toString());
        String score = jsonArray.getJSONObject(0).getJSONObject("scores").getJSONObject("All").get("f").toString();
        score = String.valueOf(Double.parseDouble(score) * 100.0);
        return score;
    }
}
