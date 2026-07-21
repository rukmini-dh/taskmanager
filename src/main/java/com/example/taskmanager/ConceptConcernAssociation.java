package com.example.taskmanager;

import jakarta.persistence.*;

@Entity
@Table(
    uniqueConstraints =
        @UniqueConstraint(
            columnNames = {
                "concept_id",
                "concern_id"
            }
        )
)
public class ConceptConcernAssociation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "concern_id")
    private Concern concern;

    @ManyToOne(optional = false)
    @JoinColumn(name = "concept_id")
    private Concept concept;

private int timesAccepted=0;

private int  timesRejected=0;
private int  timesSuggested=0;

   
    public ConceptConcernAssociation(){}
    public ConceptConcernAssociation(
        Concept concept,
        Concern concern,
        int suggested,
        int accepted,
        int rejected) {

    this.concept = concept;
    this.concern = concern;
    this.timesSuggested = suggested;
    this.timesAccepted = accepted;
    this.timesRejected = rejected;
}

    public ConceptConcernAssociation(Concern concern, Concept concept, int timesAccepted,int timesRejected,int timesSuggested){
        this.concern = concern;
        this.concept = concept;
        this.timesAccepted=timesAccepted;
        this.timesRejected=timesRejected;
        this.timesSuggested=timesSuggested; 
    }

    public void setId(Long id){
        this.id=id;
    }

    public void setConcern(Concern concern){
        this.concern=concern;
    }

    public void setConcept(Concept concept){
        this.concept=concept;
    }

    public void setTimesRejected(int timesRejected){
        this.timesRejected=timesRejected;
    }
    public void setTimesSuggested(int timesSuggested){
        this.timesSuggested=timesSuggested;
    }
    public void setTimesAccepted(int timesAccepted){
        this.timesAccepted=timesAccepted;
    }

    public Long getId(){
        return id;
    }

    public Concern getConcern(){
        return concern;
    }

    public Concept getConcept(){
        return concept;
    }

    public int  getTimesSuggested(){
        return timesSuggested;
    }
    public int  getTimesAccepted(){
        return timesAccepted;
    }
    public int  getTimesRejected(){
        return timesRejected;
    }
    @Transient
public double getWeight() {

    if(timesSuggested==0)
        return 0;

    return (double)timesAccepted/timesSuggested;
}
}