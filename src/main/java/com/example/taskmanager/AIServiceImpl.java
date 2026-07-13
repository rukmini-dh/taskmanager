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
    private final ReasoningEngine reasoningEngine;
    
    public AIServiceImpl(
            Planner planner,
            ReasoningEngine reasoningEngine) {
    
        this.planner = planner;
        this.reasoningEngine = reasoningEngine;
    }
      
           @Override
           public AIPlanResponseDTO generatePlan(GeneratePlanRequest request) {
      
           PlanningContext context= new PlanningContext();
           PlanningDecision decision = new PlanningDecision();
     
          
            context = planner.buildPlanningContext(request.getAnalysis());  
            decision=reasoningEngine.reason(context)  ;
            return planner.generateSteps(
                context,
                decision);
            
        }
        public AIAnalysisResponseDTO analyseTitle(String title ){
            AIAnalysisResponseDTO dto = new AIAnalysisResponseDTO();
            dto = planner.analyse(title);
            return dto;
        } 

       
    }
    

      