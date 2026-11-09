package com.chronotask.model;

import java.time.LocalDate;

public class Task extends AuditableEntity {

    private int id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
    private boolean completed;

    public Task(int id, String title, String description,
                LocalDate dueDate, Priority priority, boolean completed) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completed = completed;
    }

    @Override
    public String getEntityType() {
        return "Task";
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return title + " | " + priority + " | " +
               (completed ? "Completed" : "Pending");
    }
}