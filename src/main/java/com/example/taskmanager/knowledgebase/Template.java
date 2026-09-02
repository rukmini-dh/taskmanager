package com.example.taskmanager.knowledgebase;

import com.example.taskmanager.Concern;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
@Entity
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private int weight;

    private int timesSuggested;
    private int timesAccepted;
    private int timesRejected;
    private int timesAcceptedWithEdit;
    private int consecutiveAcceptances = 0;
    private int consecutiveRejections = 0;
    private int cooldown;
    private double specificity;
    private double actionability;
    private double complexity;  
    @ManyToOne
    private Concern concern;
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    public void setCooldown(int cooldown){this.cooldown= cooldown;}
    public int getCooldown(){return cooldown;}
    public void setTimesRejected(int timesRejected){this.timesRejected =timesRejected;}
    public void setTimesAccepted(int timesAccepted){this.timesAccepted=timesAccepted;}
    public void setTimesAcceptedWithEdit(int timesAcceptedWithEdit){this.timesAcceptedWithEdit=timesAcceptedWithEdit;}
    public int getTimesRejected(){return timesRejected;}
    public int getTimesAcceptedWithEdit(){return timesAcceptedWithEdit;}
    public int getTimesAccepted(){return timesAccepted;}
    public void  setTimesSuggested(int timesSuggested){this.timesSuggested=timesSuggested;}
    public int getTimesSuggested(){return timesSuggested;}
    public Long getId(){return id;} 
    public void setId(Long id){this.id=id;}
    public void setConcern(Concern concern){this.concern=concern;}
    public Concern getConcern(){return concern;}
    public int getConsecutiveAcceptances() {
        return consecutiveAcceptances;
    }
    
    public void setConsecutiveAcceptances(int consecutiveAcceptances) {
        this.consecutiveAcceptances = consecutiveAcceptances;
    }
    
    public int getConsecutiveRejections() {
        return consecutiveRejections;
    }
    
    public void setConsecutiveRejections(int consecutiveRejections) {
        this.consecutiveRejections = consecutiveRejections;
    }
    public void setSpecificity(double specificity){this.specificity=specificity;}
    public void setActionability(double actionability){this.actionability=actionability;}
    public void setComplexity(double complexity){this.complexity=complexity;}
    public double getSpecificity(){return specificity;}
    public double getActionability(){return actionability;}
    public double getComplexity(){return complexity;} 
}
