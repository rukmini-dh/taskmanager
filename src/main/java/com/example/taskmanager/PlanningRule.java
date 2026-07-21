package com.example.taskmanager;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

 @Entity
public class PlanningRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String intent;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
   private PlanningAction action;

    private boolean enabled;

    // getters/setters
    public void setIntent(String intent){this.intent=intent;}
    public String getIntent(){return intent;}
    public void setAction(PlanningAction action){this.action=action;} 
    public PlanningAction getAction(){return action;}
    public void setEnabled(boolean enabled){this.enabled=enabled;}
    public boolean IsEnabled(){return enabled;}
} 

