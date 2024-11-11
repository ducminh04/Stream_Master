package com.example.demo.Service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImportToDB {
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=importDB2";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "123456";

    public static void main(String[] args) throws IOException {
        JSONArray data = new JSONArray(new String(Files.readAllBytes(Paths.get("src/main/resources/combine.json"))));
        importToDB(data);
    }

    public static void importToDB(JSONArray data) {
        String sql = "INSERT INTO employees (id, first_name, last_name, email, gender, ip_address, avatar, country, job, company, salary, username, password, slogan) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < data.length(); i++) {
                JSONObject employee = data.getJSONObject(i);
                String id = employee.getString("id");
                String firstName = employee.getString("first_name");
                String lastName = employee.getString("last_name");
                String email = employee.getString("email");
                String gender = employee.getString("gender");
                String ipAddress = employee.getString("ip_address");
                String avatar = employee.getString("avatar");
                String country = employee.getString("country");
                String job = employee.getString("job");
                String company = employee.getString("company");
                String salaryStr = employee.getString("salary");
                double salary = parseSalary(salaryStr);
                String username = employee.getString("username");
                String password = employee.getString("password");
                String slogan = employee.getString("slogan");

                pstmt.setObject(1, java.util.UUID.fromString(id));
                pstmt.setString(2, firstName);
                pstmt.setString(3, lastName);
                pstmt.setString(4, email);
                pstmt.setString(5, gender);
                pstmt.setString(6, ipAddress);
                pstmt.setString(7, avatar);
                pstmt.setString(8, country);
                pstmt.setString(9, job);
                pstmt.setString(10, company);
                pstmt.setDouble(11, salary);
                pstmt.setString(12, username);
                pstmt.setString(13, password);
                pstmt.setString(14, slogan);
                pstmt.executeUpdate();
            }
            System.out.println("Dữ liệu đã được nhập vào cơ sở dữ liệu thành công.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static double parseSalary(String salaryStr) {
        salaryStr = salaryStr.replaceAll("[\\$,]", "").trim().toUpperCase();

        double multiplier = 1;
        if (salaryStr.endsWith("K")) {
            multiplier = 1000;
            salaryStr = salaryStr.substring(0, salaryStr.length() - 1);
        } else if (salaryStr.endsWith("M")) {
            multiplier = 1000000;
            salaryStr = salaryStr.substring(0, salaryStr.length() - 1);
        } else if (salaryStr.endsWith("B")) {
            multiplier = 1000000000;
            salaryStr = salaryStr.substring(0, salaryStr.length() - 1);
        }
        try {
            return Double.parseDouble(salaryStr) * multiplier;
        } catch (NumberFormatException e) {
            System.err.println("Invalid salary format: " + salaryStr);
            return 0;
        }
    }
}
