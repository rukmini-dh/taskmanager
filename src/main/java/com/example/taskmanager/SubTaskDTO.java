package com.example.taskmanager;

public class SubTaskDTO {
    private String title;
    private Long id;
private boolean completed;
private boolean reviewed;
private Source source;
private String description;
// default contructor
public  SubTaskDTO(){}
//arguments constructor
public SubTaskDTO(String title,boolean completed,Source source,boolean reviewed,String description)  {
    this.title=title;
    this.completed=completed;
    this.source=source; 
    this.reviewed=reviewed;
    this.description=description;
}
public String getTitle() {
    return title;
}
public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}
public void setTitle(String title) {
    this.title = title;
}
public boolean isCompleted(){return completed;}
public void setCompleted(boolean completed){this.completed=completed;}
public boolean isReviewed(){return reviewed;}
public void setReviewed(boolean reviewed){this.reviewed=reviewed;}

public Source getSource(){return source;}
public void setSource(Source source){this.source=source;}

public void setDescription(String  description){this.description=description;}
public String  getDescription(){return description;}
}
