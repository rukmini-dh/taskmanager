package com.example.taskmanager;
public class GeneratePlanRequest {

    private AIPlanRequestDTO task;

    private AIAnalysisResponseDTO analysis;

    // getters and setters
    public void setTask(AIPlanRequestDTO task){this.task=task;}
    public void setAnalysis(AIAnalysisResponseDTO analysis){this.analysis=analysis;}
    public AIPlanRequestDTO getTask(){return task;}
    public AIAnalysisResponseDTO getAnalysis(){return analysis;}

}