package com.example.taskmanager;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
@Entity
public class Concept {

  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @OneToMany(mappedBy = "concept")
    private Set<ConceptConcernAssociation> conceptConcernAssociations= new HashSet<>();
 
    public Concept(){}

    public Concept(String name){
        this.name = name;
    }
    public void setId(Long id){this.id=id;}
    public void setName(String name){this.name=name;}
    public void setConceptConcernAssociation(Set<ConceptConcernAssociation> conceptConcernAssociations){this.conceptConcernAssociations=conceptConcernAssociations;}
    public Long getId(){return id;}
    public String getName(){return name;}
    public Set<ConceptConcernAssociation> getConceptConcernAssociation(){return conceptConcernAssociations;} 
       
    
}
