package com.example.demo.Service;

import org.json.JSONArray;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class WordFrequency {
    public static void main(String[] args) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("src/main/resources/combine.json")), StandardCharsets.UTF_8);
            JSONArray data = new JSONArray(content);

            Map<String, Integer> wordCount = new HashMap<>();
            for (int i = 0; i < data.length(); i++) {
                String slogan = data.getJSONObject(i).getString("slogan");
                String[] words = slogan.split("\\s+");
                for (String word : words) {
                    word = word.toLowerCase();
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }
            }
            wordCount.entrySet().stream()
                    .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                    .forEach(entry -> System.out.println(entry.getKey() + ":" + entry.getValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
