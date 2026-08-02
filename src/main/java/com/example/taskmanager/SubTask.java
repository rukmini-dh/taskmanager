package com.example.taskmanager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.*;


@Entity
public class SubTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private boolean completed;

    @Enumerated(EnumType.STRING)
    private Source source;

    private LocalDateTime createdAt;
    private boolean reviewed;
    private String  description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    //default constructor
    public SubTask(){}
    
    public Long getId() {
        return id;
    }
public void setId(Long id){this.id=id;}
public String getTitle()
{return title;}
public void setTitle(String title){this.title=title;}
public void setReviewed(boolean reviewed){this.reviewed=reviewed;}
public boolean isReviewed(){return reviewed;}

public boolean isCompleted(){return completed;}
public void setCompleted(boolean completed){this.completed=completed;}

public Source getSource(){return source;}
public void setSource(Source source){this.source=source;}

public LocalDateTime getCreatedAt(){return createdAt;}
public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}

public Task getTask(){return task;}
public void setTask(Task task){this.task=task;}
public void setDescription(String  description){this.description=description;}
public String  getDescription(){return description;}
}
