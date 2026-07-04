package com.example.taskmanager;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class PlanningContext {

    private Set<String> matchedIntents =  new LinkedHashSet<>();
    private Set<String> matchedKeywords = new LinkedHashSet<>();
   
     private LocalDate extractedDate;
     private Priority extractedPriority;
    // private String role;

    // getters and setters
    //no arguments constructor
    public PlanningContext(){}
    public PlanningContext( Set<String> matchedIntents ,Set<String> matchedKeywords ,LocalDate extractdDate,Priority extractedPriority ){

        this.matchedIntents=matchedIntents;
        this.matchedKeywords=matchedKeywords;
        this.extractedDate=extractdDate;
        this.extractedPriority=extractedPriority;
}
public void setMatchedIntents(Set<String> matchedIntents){this.matchedIntents=matchedIntents;}
public Set<String> getMatchedIntents(){return matchedIntents;}
public void setMatchedKeywords(Set<String> matchedKeywords){this.matchedKeywords=matchedKeywords;}
public Set<String> getMatchedKeywords(){return matchedKeywords;}

public LocalDate getExtractedDate(){return extractedDate; }
public Priority getExtractedPriority() {
    return extractedPriority;
}
public void setExtractedDate(LocalDate extractedDate){this.extractedDate=extractedDate;}
public void  setExtractedPriority(Priority extractedPriority){this.extractedPriority=extractedPriority; }

}