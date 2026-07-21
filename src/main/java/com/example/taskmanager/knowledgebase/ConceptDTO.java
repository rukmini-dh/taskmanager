package com.example.taskmanager.knowledgebase;

import java.util.List;

public class ConceptDTO {

    private String name;

    private String description;

    private List<ConcernDTO> concerns;
     public void setName(String name){this.name=name;} 
     public void setDescription(String  description){this.description=description;}
     public void setConcerns(List<ConcernDTO> concerns){this.concerns=concerns;}
     public String getName(){return name;}
     public String getDescription(){return description;}
     public List<ConcernDTO> getConcerns(){return concerns; }


}
