package com.example.taskmanager;
import org.springframework.beans.factory.annotation.Value;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AIServiceImpl implements AIService {
    private final Planner planner;

public AIServiceImpl(Planner planner) {
    this.planner = planner;
}
      // private final RestTemplate restTemplate;*/
   @Value("${openai.api.key}")
private String apiKey;

   
           @Override
           public AIPlanResponseDTO generatePlan(AIPlanRequestDTO request) {
          // Planner planner = new Planner();
           
           
            PlanningContext context =planner.analyse(request.getTitle()) ;
           
          
             
            List<SubTaskDTO> subtaskDTOs = context.getSelectedSteps().stream()
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
    /*    Map<String, List<String>> taskTemplates = new HashMap<>();
    Map<String, String> aliases = new HashMap<>();
   List<String> matchedKeywords = new ArrayList<>(); 
   Set<String> selectedSteps = new LinkedHashSet<>();
*/
/* aliases.put("sign in", "login");
aliases.put("signin", "login");
aliases.put("authentication", "login");
aliases.put("questionnaire", "survey");
 */
   /*  taskTemplates.put("login", List.of(
        "Create login UI",
        "Validate user input",
        "Verify credentials",
        "Show login error messages",
        "Test login flow"
    )); */

     
          /*  for (String keyword : taskTemplates.keySet()) {
                if (title.contains(keyword)) {
                    matchedKeywords.add(keyword);
                    selectedSteps.addAll(taskTemplates.get(keyword));
                }    
            }
            
                for (String alias : aliases.keySet()) {
                    if (title.contains(alias)) {
                        matchedKeywords.add(alias);
                        String templateKey = aliases.get(alias);
                        selectedSteps.addAll(taskTemplates.get(templateKey));                     
                       
                    }
                }
            if (selectedSteps.isEmpty()) {
                selectedSteps.addAll(List.of(
                    "Review requirements",
                    "Gather information",
                    "Create deliverables",
                    "Validate output",
                    "Finalize task"
                ));
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
            response.setMatchedKeywords(
                new ArrayList<>(matchedKeywords)
            );
        
            return response; */
          
      