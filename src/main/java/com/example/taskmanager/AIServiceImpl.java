package com.example.taskmanager;
import org.springframework.beans.factory.annotation.Value;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AIServiceImpl implements AIService {
      // private final RestTemplate restTemplate;*/
   @Value("${openai.api.key}")
private String apiKey;

   
           @Override
           public AIPlanResponseDTO generatePlan(AIPlanRequestDTO request) {

            Map<String, List<String>> taskTemplates = new HashMap<>();
        
            taskTemplates.put("login", List.of(
                "Create login UI",
                "Validate user input",
                "Verify credentials",
                "Show login error messages",
                "Test login flow"
            ));
        
            taskTemplates.put("survey", List.of(
                "Prepare questionnaire",
                "Collect responses",
                "Analyze responses",
                "Create report",
                "Review findings"
            ));
        
            List<String> selectedSteps = List.of(
                "Review requirements",
                "Gather information",
                "Create deliverables",
                "Validate output",
                "Finalize task"
            ); // fallback
        
            String title = request.getTitle().toLowerCase();
        
            for (String keyword : taskTemplates.keySet()) {
                if (title.contains(keyword)) {
                    selectedSteps = taskTemplates.get(keyword);
                    break;
                }
            }
        
            List<SubTaskDTO> subtaskDTOs = selectedSteps.stream()
                .map(step -> {
                    SubTaskDTO dto = new SubTaskDTO();
                    dto.setTitle(step);
                    dto.setCompleted(false);
                    dto.setReviewed(false);
                    dto.setSource(Source.AI);
                    return dto;
                })
                .toList();
        
            AIPlanResponseDTO response = new AIPlanResponseDTO();
            response.setSteps(subtaskDTOs);
        
            return response;
        }
      }
      