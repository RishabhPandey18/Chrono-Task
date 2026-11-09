package com.chronotask.db;

import com.chronotask.model.Task;
import com.chronotask.model.Priority;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SQLiteTaskRepository implements TaskRepository {

    // Add a new task
    @Override
    public synchronized void addTask(Task task) throws SQLException {

        String sql = """
                INSERT INTO tasks (title, description, due_date, priority, completed)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getDueDate().toString());
            statement.setString(4, task.getPriority().name());
            statement.setInt(5, task.isCompleted() ? 1 : 0);

            statement.executeUpdate();
        }
    }

    // Retrieve all tasks
    @Override
    public synchronized List<Task> getAllTasks() throws SQLException {

        List<Task> tasks = new ArrayList<>();

        String sql = "SELECT * FROM tasks";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                String dateValue = resultSet.getString("due_date");

                LocalDate dueDate = (dateValue == null || dateValue.isBlank())
                        ? null : LocalDate.parse(dateValue);

                Task task = new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        dueDate,
                        Priority.valueOf(resultSet.getString("priority")),
                        resultSet.getInt("completed") == 1
                );

                tasks.add(task);
            }
        }

        return tasks;
    }

    // Update task status and priority
    @Override
    public synchronized void updateTask(Task task) throws SQLException {

        String sql = """
                UPDATE tasks
                SET completed = ?, priority = ?
                WHERE id = ?
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, task.isCompleted() ? 1 : 0);
            statement.setString(2, task.getPriority().name());
            statement.setInt(3, task.getId());

            statement.executeUpdate();
        }
    }

    // Delete a task
    @Override
    public synchronized void deleteTask(int id) throws SQLException {

        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeUpdate();
        }
    }
}