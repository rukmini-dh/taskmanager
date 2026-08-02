package com.example.taskmanager;

import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/ai")
public class AIController {
    private final AIService aiService;
    public AIController( AIService aiService){ this.aiService=aiService;    }
   
    @PostMapping("/generate-plan/{taskId}")
    public AIPlanResponseDTO generatePlan(
            @PathVariable Long taskId) {
    
        return aiService.generatePlan(taskId);
    }
    
    @PostMapping("/generate-SubTasks/{title}")
    public AIPlanResponseDTO generateSubTasks(
            @PathVariable String title) {
    
        return aiService.generateSubTasks(title);
    }
    @PostMapping("/analyseTitle")
public AIAnalysisResponseDTO analyseTitle(
        @RequestBody String title) {

    return aiService.analyseTitle(title);
}

}