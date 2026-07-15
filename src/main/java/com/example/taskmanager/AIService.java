package com.example.taskmanager;

public interface AIService {

    AIPlanResponseDTO generatePlan(Long id);
    AIAnalysisResponseDTO analyseTitle(String title);

}