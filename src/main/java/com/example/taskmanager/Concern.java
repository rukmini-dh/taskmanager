package com.example.taskmanager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
@Entity
public class Concern {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
 
     private String name;
     @OneToMany(mappedBy="concern")

   /*   @OneToMany(mappedBy = "concern", cascade = CascadeType.ALL)
    private List<Template> templates = new ArrayList<>(); */
 
     Set<ConceptConcernAssociation> conceptConcernAssociations= new HashSet<>();
     @ElementCollection(fetch = FetchType.EAGER)
    private List<String> templates = new ArrayList<>();
    
  
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
     public void setTemplates(List<String> templates){this.templates=templates;}
     public List<String> getTemplates(){return templates;}
     
        
     
     
 }