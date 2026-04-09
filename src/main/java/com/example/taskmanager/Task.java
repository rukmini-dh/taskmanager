package com.example.taskmanager;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;      // primary 
    
    @NotBlank(message="Title cannot be empty")//validation for title
    private String title;    // task title
    private boolean completed;  // task status
    public String Description;
    public LocalDate dueDate;
    @Enumerated(EnumType.STRING)
    private Priority priority;//High,Medium,Low

    


    // Default constructor (required by JPA)
    public Task() {}

    // Constructor with fields
    public Task(String title, boolean completed,LocalDate dueDate, Priority priority,String Description  ) {
        this.title = title;
        this.completed = completed;
        this.dueDate = dueDate;
        this.Description=Description;
        this.priority=priority;
        
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
   
    

    // Optional: toString for debugging
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }
    
}