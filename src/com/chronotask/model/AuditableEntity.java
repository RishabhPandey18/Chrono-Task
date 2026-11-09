package com.chronotask.model;

import java.time.LocalDateTime;

public abstract class AuditableEntity {

    private LocalDateTime createdAt;

    public AuditableEntity() {
        this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public abstract String getEntityType();
}