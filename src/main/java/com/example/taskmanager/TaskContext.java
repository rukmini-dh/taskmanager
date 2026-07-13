package com.example.taskmanager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
@Entity
public class TaskContext {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   @ElementCollection
    private Set<String> matchedIntents = new LinkedHashSet<>();

    @ElementCollection
    private Set<String> matchedKeywords = new LinkedHashSet<>();
    @OneToOne(optional = false)
    @JoinColumn(name = "task_id",nullable = false)
    @JsonIgnore
    private Task task;
   
    private int riskScore = 0;
    private Complexity complexity;
    private String estimatedEffort;
   private LocalDateTime createdAt;
   private LocalDate extractedDate;
     private Priority extractedPriority;
    // private String role;

    // getters and setters
    //no arguments constructor
    public TaskContext(){}
    public TaskContext( Set<String> matchedIntents ,Set<String> matchedKeywords ,LocalDateTime createdAt,Priority extractedPriority ){

        this.matchedIntents=matchedIntents;
        this.matchedKeywords=matchedKeywords;
        this.createdAt=createdAt;
        this.extractedPriority=extractedPriority;
}
public void setMatchedIntents(Set<String> matchedIntents){this.matchedIntents=matchedIntents;}
public Set<String> getMatchedIntents(){return matchedIntents;}
public void setMatchedKeywords(Set<String> matchedKeywords){this.matchedKeywords=matchedKeywords;}
public Set<String> getMatchedKeywords(){return matchedKeywords;}

public LocalDateTime getCreatedAt(){return createdAt; }
public Priority getExtractedPriority() {
    return extractedPriority;
}
public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}
public void  setExtractedPriority(Priority extractedPriority){this.extractedPriority=extractedPriority; }
public Task getTask(){return task;}
public void setTask(Task task){this.task=task;}
public void setExtractedDate(LocalDate extractedDate){this.extractedDate=extractedDate;}
public LocalDate getExtractedDate(){return extractedDate;}
public void setComplexity(Complexity complexity){this.complexity=complexity;}
public void setRiskScore(int riskScore){this.riskScore=riskScore;}
public void setEstimatedEffort(String estimatedEffort){this.estimatedEffort=estimatedEffort;}
public Complexity getComplexity(){return complexity;}
public String getEstimatedEffort(){return estimatedEffort;}
public int getRiskScore(){return riskScore;}
 


}


