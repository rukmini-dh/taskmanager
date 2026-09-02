package com.example.taskmanager;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.taskmanager.knowledgebase.FeedbackType;
import com.example.taskmanager.knowledgebase.Template;

import jakarta.persistence.*;


@Entity
public class SubTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private boolean completed;
    private boolean edited = false;
    private boolean deleted = false;
    @Enumerated(EnumType.STRING)
    private Source source;

    private LocalDateTime createdAt;
    
    private String  description;
    @ManyToOne
    private Template template;
    private boolean recommended;
    @Enumerated(EnumType.STRING)
    private FeedbackType feedback;

    
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    //default constructor
    public SubTask(){}
    
    public Long getId() {
        return id;
    }
public void setId(Long id){this.id=id;}
public boolean isEdited() {
    return edited;
}

public void setEdited(boolean edited) {
    this.edited = edited;
}
public boolean isDeleted() {
    return deleted;
}

public void setDeleted(boolean deleted) {
    this.deleted=deleted;
}
public String getTitle()
{return title;}
public void setTitle(String title){this.title=title;}


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
public void setTemplate(Template template) {
    this.template = template;
}
public Template getTemplate() {
    return template;
}
public FeedbackType getFeedback(){return feedback;}
public void setFeedback(FeedbackType feedback){this.feedback=feedback;}
public boolean isRecommended() {
    return recommended;
}

public void setRecommended(boolean recommended) {
    this.recommended = recommended;
}
}
