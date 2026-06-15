package com.example.taskmanager;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

import com.example.taskmanager.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "userName",nullable = false)
    private User user;

    @OneToMany(mappedBy = "task")
    @JsonIgnore
    private List<SubTask> subtasks;
    
    private String title;

    private String description;

    private boolean completed=false;
    boolean     deleted=false;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private LocalDate dueDate;

    // Getters and Setters
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    // Default constructor (required by JPA)
    public Task() {}

    // Constructor with fields
    public Task(String title, boolean completed,LocalDate dueDate, Priority priority,String description ,boolean deleted ) {
        this.title = title;
        this.completed = completed;
        this.dueDate = dueDate;
        this.description=description;
        this.priority=priority;
        this.deleted=deleted; 
        
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
        public void setCompleted(boolean completed) {
            this.completed = completed;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
   
    

   /*  // Optional: toString for debugging
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }
     */
}