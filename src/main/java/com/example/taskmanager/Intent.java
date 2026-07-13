package com.example.taskmanager;

import java.util.List;

public class Intent {

    private String name;
    private List<String> keywords;
 //   private List<String> subtasks;
    List<String> baseSteps;
List<String> testingSteps;
List<String> reviewSteps;

    public Intent() {}

    public Intent(
            String name,
            List<String> keywords,
            List<String> baseSteps,List<String> testingSteps,List<String>reviewSteps) {

        this.name = name;
        this.keywords = keywords;
        this.testingSteps=testingSteps;
        this.reviewSteps=reviewSteps;
        this.baseSteps=baseSteps;
    }

    // getters and setters
    public void setName(String name){this.name=name;}
    public void setKeywords(List<String> keywords){ this.keywords = keywords;}
    public void setReviewSteps(List<String> reviewSteps){ this.reviewSteps = reviewSteps;}
    public void setBaseSteps(List<String> baseSteps){this.baseSteps=baseSteps;}
    public void setTestingSteps(List<String> testingSteps){this.testingSteps=testingSteps;}

    public String getName(){return name;}
    public List<String> getKeywords(){return keywords;}
    public List<String> getTestingSteps(){return testingSteps;}
    public List<String> getReviewSteps(){return reviewSteps;}
    public List<String> getBaseSteps(){return baseSteps;}  

}
