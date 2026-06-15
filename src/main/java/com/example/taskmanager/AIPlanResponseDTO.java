package com.example.taskmanager;

import java.util.List;

public class AIPlanResponseDTO {
    private List<SubTaskDTO> steps;
    public List<SubTaskDTO> getSteps(){return steps;}
    // constructor
    public AIPlanResponseDTO(){}
    public AIPlanResponseDTO(List<SubTaskDTO> steps){this.steps=steps;}
public void setSteps(List<SubTaskDTO> steps){this.steps=steps;}
}
