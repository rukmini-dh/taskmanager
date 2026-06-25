package com.example.taskmanager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.taskmanager.user.DuplicateUserException;

@RestControllerAdvice
public class GlobalExceptionHandler {

   
@ExceptionHandler(TaskNotFoundException.class)
public ResponseEntity<Map<String, Object>> handleTaskNotFound(TaskNotFoundException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("status", 404);
    error.put("message", ex.getMessage());
   
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}
@ExceptionHandler(SubTaskNotFoundException.class)
public ResponseEntity<Map<String, Object>> handleSubTaskNotFound(SubTaskNotFoundException ex) {
    Map<String, Object> error = new HashMap<>();
    error.put("status", 404);
    error.put("message", ex.getMessage());
   
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}
@ExceptionHandler(IncorrectPasswordException.class)
public ResponseEntity<String>handleIncorrectPassword(IncorrectPasswordException ex) {

    return ResponseEntity
            .badRequest()
            .body(ex.getMessage());
}

}