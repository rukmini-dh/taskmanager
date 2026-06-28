package com.example.taskmanager;

import java.util.List;

public class Intent {

    private String name;
    private List<String> keywords;
    private List<String> subtasks;

    public Intent() {}

    public Intent(
            String name,
            List<String> keywords,
            List<String> subtasks) {

        this.name = name;
        this.keywords = keywords;
        this.subtasks = subtasks;
    }

    // getters and setters
    public void setName(String name){this.name=name;}
    public void setKeywords(List<String> keywords){ this.keywords = keywords;}
    public void setSubtasks(List<String> subtasks){ this.subtasks = subtasks;}

    public String getName(){return name;}
    public List<String> getKeywords(){return keywords;}
    public List<String> getSubtasks(){return subtasks;}

}