package com.example.taskmanager;

import java.time.LocalDate;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;

public class TaskDTO {

    @NotBlank(message = "Title cannot be empty")
    private String title;
    private Long id;
   
    private String description;
    private Boolean completed;

    // New fields for tomorrow
    private LocalDate dueDate;
    @Enumerated(EnumType.STRING)
    private String priority;

    // Getters and Setters

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription(){ return description; }
    public void setDescription(String description){ this.description= description;}
    public  void setDueDate(LocalDate dueDate){ this.dueDate=dueDate;} 
    public LocalDate getDueDate(){return dueDate;} 
    public String getPriority(){ return priority; }
    public void setPriority(String priority){ this.priority=priority;}
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}