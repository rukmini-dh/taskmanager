package com.example.taskmanager;

public class SubTaskNotFoundException extends RuntimeException {

    public SubTaskNotFoundException(String message) {
        super(message);
    }
}
