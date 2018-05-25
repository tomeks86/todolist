package edu.tseidler.todo.datamodel;

import java.time.LocalDate;

public class TodoItem {
    private final String shortDescription;
    private final String details;
    private final LocalDate deadline;

    public TodoItem(String shortDescription, String details, LocalDate deadline) {
        this.shortDescription = shortDescription;
        this.details = details;
        this.deadline = deadline;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDetails() {
        return details;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return shortDescription;
    }
}
