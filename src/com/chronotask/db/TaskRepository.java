package com.chronotask.db;

import com.chronotask.model.Task;
import java.util.List;

public interface TaskRepository {

    void addTask(Task task) throws Exception;

    List<Task> getAllTasks() throws Exception;

    void updateTask(Task task) throws Exception;

    void deleteTask(int id) throws Exception;
}