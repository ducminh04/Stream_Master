package com.example.demo.Service;

import org.json.JSONArray;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MergerJsonFile {
    public static void main(String[] args) throws IOException {
        JSONArray combineData = new JSONArray();
        String[] filePaths = {"src/main/resources/people_0.json", "src/main/resources/people_1.json",
                "src/main/resources/people_2.json", "src/main/resources/people_3.json",
                "src/main/resources/people_4.json" , "src/main/resources/people_5.json"
                , "src/main/resources/people_6.json", "src/main/resources/people_7.json",
                "src/main/resources/people_8.json", "src/main/resources/people_9.json"};
        for(String path : filePaths){
            String content = new String(Files.readAllBytes(Paths.get(path)));
            JSONArray data = new JSONArray(content);
            for(int i = 0; i < data.length(); i++){
                combineData.put(data.getJSONObject(i));
            }
        }
        Files.write(Paths.get("src/main/resources/combine.json"), combineData.toString().getBytes());

    }
}
