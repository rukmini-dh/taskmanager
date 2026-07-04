package com.example.taskmanager;

public interface AIService {

    AIPlanResponseDTO generatePlan(AIPlanRequestDTO request);
    AIAnalysisResponseDTO analyseTitle(String title);

}