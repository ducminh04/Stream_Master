package com.example.demo.Service;

import org.json.JSONArray;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SortBySalary {
    public static void main(String[] args) throws IOException {
        JSONArray  data = new JSONArray(new String(Files.readAllBytes(Paths.get("src/main/resources/combine.json"))));
        JSONArray sortedData = new JSONArray(IntStream.range(0,data.length())
                .mapToObj(data :: getJSONObject)
                .sorted((o1,o2) -> Double.compare(o2.getDouble("salary"), o1.getDouble("salary")))
                .collect(Collectors.toList()));
        System.out.println(sortedData.toString(2));
    }
}
