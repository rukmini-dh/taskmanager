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
    }public void setTimesSuggested(int timesSuggested){this.timesSuggested=timesSuggested;}
    public void setTimesRejected(int timesRejected){this.timesRejected =timesRejected;}
    public void setTimesAccepted(int timesAccepted){this.timesAccepted=timesAccepted;}
    public int getTimesRejected(){return timesRejected;}
    public int getTimesAccepted(){return timesAccepted;}
    public  int getTimesSuggested(){return timesSuggested;} 
}
