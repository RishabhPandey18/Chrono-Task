package com.chronotask;

import com.chronotask.db.DatabaseManager;
import com.chronotask.db.TaskRepository;
import com.chronotask.db.SQLiteTaskRepository;

import com.chronotask.service.ActivityLog;
import com.chronotask.service.TaskService;

import com.chronotask.ui.ChronoTaskFrame;

import javax.swing.*;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        try {

            // Step 1: Initialize database
            DatabaseManager.initializeDatabase();

            // Step 2: Create repository
            TaskRepository repository =
                    new SQLiteTaskRepository();

            // Step 3: Create activity log
            ActivityLog activityLog =
                    new ActivityLog();

            // Step 4: Create service
            TaskService taskService =
                    new TaskService(repository, activityLog);

            // Step 5: Launch GUI
            SwingUtilities.invokeLater(() -> {

                ChronoTaskFrame frame =
                        new ChronoTaskFrame(taskService);

                frame.setVisible(true);
            });

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Database connection failed: " + e.getMessage(),
                    "Startup Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }
}