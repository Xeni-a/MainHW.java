package com.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");

        Connection connection = DriverManager.getConnection("jdbc:sqlite:StudentsDB.db");

       PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO students(name, score) VALUES (?, ?)");


        for (int i = 1; i < 10; i++) {
            Savepoint savepoint = connection.setSavepoint();
            preparedStatement.setString(1, "Bob" + i);
            preparedStatement.setInt(2, 20 * i);
            preparedStatement.executeUpdate();
        }

        try {Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int id = resultSet.getInt("id");
                int score = resultSet.getInt("score");

                System.out.println(id + " " + name + " " + score);
            }
        } catch (Exception e) {

        }


        connection.close();
        }

    }

