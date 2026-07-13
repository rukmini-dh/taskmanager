package com.example.taskmanager;

public class CreateTaskContextRequest {

    private Long taskId;

    private AIAnalysisResponseDTO analysis;

    // getters/setters
    public CreateTaskContextRequest(){}
    public CreateTaskContextRequest(Long taskId, AIAnalysisResponseDTO analysis){
        this.taskId=taskId;
        this.analysis=analysis;

    }
    public void setTaskId(Long taskId){this.taskId=taskId;}
    public long getTaskId(){return taskId;}
    public void setAnalysis(AIAnalysisResponseDTO analysis){this.analysis=analysis;}
    public AIAnalysisResponseDTO getAnalysis(){return analysis;}
}