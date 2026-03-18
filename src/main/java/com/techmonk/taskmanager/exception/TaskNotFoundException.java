package com.techmonk.taskmanager.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("TASK WITH ID: " + id + " NOT FOUND");
    }
}
