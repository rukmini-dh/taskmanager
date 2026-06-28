package com.example.taskmanager;

import java.util.List;

public class AIPlanResponseDTO {
    private List<SubTaskDTO> steps;
    private List<String> matchedKeywords;

    public List<SubTaskDTO> getSteps(){return steps;}
    // constructor
    public AIPlanResponseDTO(){}
    public AIPlanResponseDTO(List<SubTaskDTO> steps,List<String> matchedKeywords){this.steps=steps; this.matchedKeywords=matchedKeywords;}
    public void setSteps(List<SubTaskDTO> steps){this.steps=steps;}
    public void setMatchedKeywords(List<String> matchedKeywords){this.matchedKeywords=matchedKeywords;}
   
    public List<String> getMatchedKeywords(){return matchedKeywords;}
}
