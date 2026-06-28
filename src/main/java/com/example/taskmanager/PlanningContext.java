package com.example.taskmanager;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class PlanningContext {

    private Set<String> intents = new HashSet<>();
    private Set<String> matchedKeywords = new HashSet<>();
    private Set<String> selectedSteps = new LinkedHashSet<>();

    // later
    // private LocalDate extractedDate;
    // private Priority extractedPriority;
    // private String role;

    // getters and setters
    //no arguments constructor
    public PlanningContext(){}
    public PlanningContext( Set<String> intents ,Set<String> matchedKeywords ,Set<String> selectedSteps ){

        this.intents=intents;
        this.matchedKeywords=matchedKeywords;
        this.selectedSteps=selectedSteps;

}
public void setIntents(Set<String> intents){this.intents=intents;}
public Set<String> getIntents(){return intents;}
public void setMatchedKeywords(Set<String> matchedKeywords){this.matchedKeywords=matchedKeywords;}
public Set<String> getMatchedKeywords(){return matchedKeywords;}
public void setSelectedSteps(Set<String> selectedSteps){this.selectedSteps=selectedSteps;}
public Set<String> getSelectedSteps(){return selectedSteps;}
}