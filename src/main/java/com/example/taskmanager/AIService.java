package com.example.taskmanager;

public interface AIService {

    AIPlanResponseDTO generatePlan(GeneratePlanRequest request);
    AIAnalysisResponseDTO analyseTitle(String title);

}