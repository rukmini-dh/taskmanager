package com.example.taskmanager;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AIPlanResponseDTO {
    private List<SubTaskDTO> steps;
    private Set<String> matchedKeywords = new LinkedHashSet<>();
    private Priority extractedPriority;
    public List<SubTaskDTO> getSteps(){return steps;}
    // constructor
    public AIPlanResponseDTO(){}
    public AIPlanResponseDTO(List<SubTaskDTO> steps,Set<String> matchedKeywords){this.steps=steps; }
    public void setSteps(List<SubTaskDTO> steps){this.steps=steps;}
    public void setMatchedKeywords(Set<String> matchedKeywords){this.matchedKeywords=matchedKeywords;}
    public void  setExtractedPriority(Priority extractedPriority){this.extractedPriority= extractedPriority; }
    public Priority getExtactedPriority(){return extractedPriority;}
    public  Set<String> getMatchedKeywords(){return matchedKeywords;}

}
