package com.example.taskmanager;

import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/ai")
public class AIController {
    private final AIService aiService;
    public AIController( AIService aiService){ this.aiService=aiService;    }
    @PostMapping("/generate-plan")
    public AIPlanResponseDTO generatePlan( @RequestBody GeneratePlanRequest dto){
            return aiService.generatePlan(dto);
    }
   
    @PostMapping("/analyseTitle")
public AIAnalysisResponseDTO analyseTitle(
        @RequestBody String title) {

    return aiService.analyseTitle(title);
}

}