package com.example.taskmanager.knowledgebase;

import java.util.List;

public class KnowledgeDTO {

    private List<ConceptDTO> concepts;

    // getters
    public List<ConceptDTO> getConcepts(){return concepts;}

    // setters
    public void setConcepts(List<ConceptDTO> concepts){this.concepts=concepts;}
}
