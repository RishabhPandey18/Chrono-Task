package com.chronotask.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityLog {

    private final List<String> activities =
            Collections.synchronizedList(new ArrayList<>());

    // Record an activity
    public void addActivity(String activity) {

        String entry = LocalDateTime.now()
                + " - " + activity;

        activities.add(entry);
    }

    // Retrieve all activities
    public List<String> getActivities() {

        synchronized (activities) {
            return new ArrayList<>(activities);
        }
    }
}