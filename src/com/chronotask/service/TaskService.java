package com.chronotask.service;

import com.chronotask.db.TaskRepository;
import com.chronotask.exception.TaskException;
import com.chronotask.model.Task;

import java.util.List;

public class TaskService {

    private final TaskRepository repository;
    private final ActivityLog activityLog;

    public TaskService(TaskRepository repository,
                       ActivityLog activityLog) {

        this.repository = repository;
        this.activityLog = activityLog;
    }

    // Add task
    public void addTask(Task task) throws TaskException {

        try {

            if (task.getTitle() == null ||
                task.getTitle().isBlank()) {

                throw new TaskException(
                        "Task title cannot be empty.");
            }

            if (task.getDueDate() == null) {
                throw new TaskException(
                        "Please provide a due date.");
            }

            repository.addTask(task);

            activityLog.addActivity(
                    "Added task: " + task.getTitle());

        } catch (TaskException e) {

            throw e;

        } catch (Exception e) {

            throw new TaskException(
                    "Unable to add task.", e);
        }
    }

    // Retrieve tasks
    public List<Task> getAllTasks() throws TaskException {

        try {

            return repository.getAllTasks();

        } catch (Exception e) {

            throw new TaskException(
                    "Unable to retrieve tasks.", e);
        }
    }

    // Update task
    public void updateTask(Task task) throws TaskException {

        try {

            repository.updateTask(task);

            activityLog.addActivity(
                    "Updated task: " + task.getTitle());

        } catch (Exception e) {

            throw new TaskException(
                    "Unable to update task.", e);
        }
    }

    // Delete task
    public void deleteTask(int id) throws TaskException {

        try {

            repository.deleteTask(id);

            activityLog.addActivity(
                    "Deleted task ID: " + id);

        } catch (Exception e) {

            throw new TaskException(
                    "Unable to delete task.", e);
        }
    }

    // Retrieve activity history
    public List<String> getActivities() {

        return activityLog.getActivities();
    }
}