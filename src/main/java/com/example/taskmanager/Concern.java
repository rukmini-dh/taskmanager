package com.example.taskmanager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.taskmanager.knowledgebase.Template;

import jakarta.persistence.*;
@Entity
public class Concern {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(
        mappedBy = "concern",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER
    )
    private List<Template> templates = new ArrayList<>();

    @OneToMany(mappedBy = "concern")
    private Set<ConceptConcernAssociation> conceptConcernAssociations = new HashSet<>();

    // constructors, getters, setters...
  
  
     public Concern(){}

     public Concern(String name){
         this.name = name;
     }
     public void setId(Long id){this.id=id;}
     public void setName(String name){this.name=name;}
     public void setConceptConcernAssociations(Set<ConceptConcernAssociation> conceptConcernAssociations){this.conceptConcernAssociations=conceptConcernAssociations;}
     public Long getId(){return id;}
     public String getName(){return name;}
     public Set<ConceptConcernAssociation> getConceptConcernAssociations(){return conceptConcernAssociations;} 
     public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }
    
    public List<Template> getTemplates() {
        return templates;
    }
     
        
     
     
 }