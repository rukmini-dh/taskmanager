package com.example.taskmanager.user;

public class DuplicateUserException
        extends RuntimeException {

    public DuplicateUserException(
            String message) {

        super(message);
    }
}