package com.example;

import java.sql.*;

public class ChatDB implements AutoCloseable {


    private static ChatDB instance;
    private static Connection connection;

    private static PreparedStatement findByLoginAndPassword;


    private ChatDB() {

    }

    public static ChatDB getInstance() {
        if (instance == null) {
            loadDriverAndOpenConnection();
            createPreparedStatements();
            instance = new ChatDB();
        }
        return instance;
    }
    private static void loadDriverAndOpenConnection()  {
        try {Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:chatDB.db");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Соединение с базой данных не установлено");
            e.printStackTrace();

        }
    }

    private static void createPreparedStatements() {
    try {
        findByLoginAndPassword = connection.prepareStatement("SELECT * FROM users (login, password) VALUES (?, ?)");
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    }

    public String findByLoginAndPassword(String login, String password) {
        ResultSet resultSet = null;

        try {
            findByLoginAndPassword.setString(1, login);
            findByLoginAndPassword.setString(2, password);
            resultSet = findByLoginAndPassword.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("Nickname");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
        }
        return null;
    }





    private void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() throws Exception {
    try {
        findByLoginAndPassword.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
  }
}
