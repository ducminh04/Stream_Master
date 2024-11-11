package com.example.demo.Service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

public class CountryCount {
    public static void main(String[] args) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("src/main/resources/combine.json")));
        Map<String, Integer> countryCount = new TreeMap<>();
        if (content.trim().startsWith("[")) {
            JSONArray data = new JSONArray(content);
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = data.getJSONObject(i);
                if (obj.has("country")) {
                    String country = obj.getString("country");
                    countryCount.put(country, countryCount.getOrDefault(country, 0) + 1);
                }
            }
        } else {
            JSONObject data = new JSONObject(content);
            if (data.has("country")) {
                String country = data.getString("country");
                countryCount.put(country, countryCount.getOrDefault(country, 0) + 1);
            }
        }
        countryCount.forEach((country, count) -> System.out.println(country + ": " + count));
    }
}
