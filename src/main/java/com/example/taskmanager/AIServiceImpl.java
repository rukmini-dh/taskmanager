package com.example.taskmanager;
import org.springframework.beans.factory.annotation.Value;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    private final TaskContextRepository taskContextRepository;
    public AIServiceImpl(
            Planner planner,
            ReasoningEngine reasoningEngine,TaskContextRepository taskContextRepository ){
    
        this.planner = planner;
        this.reasoningEngine = reasoningEngine;
        this.taskContextRepository= taskContextRepository;
    }
      
           @Override
           public AIPlanResponseDTO generatePlan(Long taskid) {
      
          // PlanningContext context= new PlanningContext();
           PlanningDecision decision = new PlanningDecision();
           System.out.println("Entered generatePlan with taskId = " + taskid);
     
           AIPlanResponseDTO response =new AIPlanResponseDTO();
           Optional<TaskContext> optionalcontext = taskContextRepository.findByTaskId(taskid);
           
           TaskContext context = optionalcontext  .orElseThrow(() ->
           new TaskContextNotFoundException(
               "TaskContext not found for task " + taskid
           )
       );
       Optional<TaskContext> optionalContext =
        taskContextRepository.findByTaskId(taskid);

System.out.println("Optionla"+optionalContext.isPresent());
       
           
             
   
       
        //    context = planner.buildPlanningContext(request.getAnalysis());  
            decision=reasoningEngine.reason(context)  ;
           response = planner.generatePlan(context, decision);    
         
return response;
         
     
            
        }
        public AIAnalysisResponseDTO analyseTitle(String title ){
            AIAnalysisResponseDTO dto = new AIAnalysisResponseDTO();
            dto = planner.analyse(title);
            return dto;
        } 

       
    }
    

      