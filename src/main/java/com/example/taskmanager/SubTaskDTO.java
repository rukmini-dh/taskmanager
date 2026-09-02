package com.example.taskmanager;

import com.example.taskmanager.knowledgebase.FeedbackType;

public class SubTaskDTO {
    private String title;
    private Long id;
    private Long templateId;
    private boolean completed;
    private Source source;
    private String description;
    private FeedbackType feedback;
    private boolean edited ;
    private boolean deleted= false;
    private boolean recommended;

// default contructor
public  SubTaskDTO(){}
//arguments constructor
public SubTaskDTO(String title,boolean completed,Source source,String description,Long templateId,FeedbackType feedback,boolean edited,boolean deleted)  {
    this.title=title;
    this.completed=completed;
    this.source=source; 
   this.description=description;
    this.templateId=templateId;
    this.edited=edited;
    this.feedback=feedback;
    this.deleted = deleted;
}
public String getTitle() {
    return title;
}
public Long getId() {
    return id;
}
public boolean isEdited() {
    return edited;
}

public void setEdited(boolean edited) {
    this.edited = edited;
}

public void setTemplateId(Long templateId) {
    this.templateId = templateId;
}
public Long getTemplateId() {
    return templateId;
}

public void setId(Long id) {
    this.id = id;
}
public void setTitle(String title) {
    this.title = title;
}
public boolean isCompleted(){return completed;}
public void setCompleted(boolean completed){this.completed=completed;}
public boolean isDeleted() {
    return deleted;
}

public void setDeleted(boolean deleted) {
    this.deleted=deleted;
}
public FeedbackType getFeedback(){return feedback;}
public void setFeedback(FeedbackType feedback){this.feedback=feedback;}
public Source getSource(){return source;}
public void setSource(Source source){this.source=source;}

public void setDescription(String  description){this.description=description;}
public String  getDescription(){return description;}
public boolean isRecommended() {
    return recommended;
}

public void setRecommended(boolean recommended) {
    this.recommended = recommended;
}
}

