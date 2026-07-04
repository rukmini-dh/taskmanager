package com.example.taskmanager;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class Planner {
    
   /*  List<String> matchedKeywords = new ArrayList<>(); 
    Set<String> selectedSteps = new LinkedHashSet<>();  */
    Intent loginIntent = new Intent(
        "LOGIN",
        List.of(
            "login",
            "authentication",
            "signin",
            "sign in",
            "credentials"
        ),
        List.of(
            "Create login UI",
            "Validate credentials",
            "Show login error",
            "Test authentication"
        )
    );

    Intent dashboardIntent = new Intent(
        "DASHBOARD",
        List.of(
            "dashboard",
            "metrics",
            "statistics"
        ),
        List.of(
            "Create dashboard layout",
            "Display metrics",
            "Test dashboard"
        )
    );
    List<Intent> intents = List.of(
        loginIntent,
        dashboardIntent
       
    );

    
    public AIAnalysisResponseDTO analyse(String title) {
       AIAnalysisResponseDTO dto= new AIAnalysisResponseDTO();

        title=title.toLowerCase();
      
        if (title.contains("urgent")
            ||
        title.contains("high priority")) {

        dto.setExtractedPriority(
            Priority.HIGH
        );}
        for (Intent intent : intents) {

            for (String keyword : intent.getKeywords()) {
        
                if (title.contains(keyword)) {
        
                    dto.getMatchedKeywords()
                           .add(keyword);
        
                    dto.getMatchedIntents()
                           .add(intent.getName());
        
                    break;
                }
            }
        }
        return dto;
    }
        
    public AIPlanResponseDTO generateSteps(
      String title) {
        title=title.toLowerCase();
    Set<String> selectedSteps =
            new LinkedHashSet<>();

    for (Intent intent : intents) {

        if (title.contains(intent.getName())) {

            selectedSteps.addAll(
                    intent.getSubtasks());
        }
    }

    List<SubTaskDTO> dtoList =
            selectedSteps.stream()
            .map(step -> {
                SubTaskDTO dto =
                        new SubTaskDTO();

                dto.setTitle(step);
                dto.setCompleted(false);
                dto.setReviewed(false);
                dto.setSource(Source.AI);

                return dto;
            })
            .toList();

    AIPlanResponseDTO response =
            new AIPlanResponseDTO();

    response.setSteps(dtoList);

    return response;
}
}