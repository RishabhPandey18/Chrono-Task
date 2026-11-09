package com.chronotask.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:chronotask.db";

    // Establish database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Create the tasks table if it does not exist
    public static void initializeDatabase() throws SQLException {

        String sql = """
                CREATE TABLE IF NOT EXISTS tasks (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    description TEXT,
                    due_date TEXT,
                    priority TEXT,
                    completed INTEGER DEFAULT 0
                )
                """;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);

            System.out.println("Database initialized successfully!");
        }
    }
}