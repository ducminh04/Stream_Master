package com.example.demo.Service;

import org.json.JSONArray;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
// Bai 1
public class PaginationExample {
      public static JSONArray loadData(String filePath) throws IOException {
          String content  = new String (Files.readAllBytes(Paths.get(filePath)));
          return new JSONArray(content);
    }
    public static void displayPage(JSONArray data, int pageSize, int pageNumber){
          int start = pageNumber * pageSize;
          int end = Math.min(start + pageSize, data.length());
          for(int i = start; i < end; i++){
              System.out.println(data.getJSONObject(i).toString());
          }
    }

    public static void main(String[] args) throws IOException {
        JSONArray data = loadData("src/main/resources/people_0.json");
        int pageSize = 5;
        int pageNumber = 3;
        displayPage(data, pageSize, pageNumber);
    }
}
