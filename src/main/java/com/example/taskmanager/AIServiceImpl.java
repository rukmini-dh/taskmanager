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

import com.example.taskmanager.user.User;
import com.example.taskmanager.user.UserPreferenceModel;
import com.example.taskmanager.user.UserPreferenceModelRepository;

@Service
public class AIServiceImpl implements AIService {
    private final Planner planner;
   
    public AIServiceImpl(
            Planner planner,UserPreferenceModelRepository userPreferenceModelRepository,
            TaskRepository taskRepository,TaskContextRepository taskContextRepository ){
    
        this.planner = planner;
       
    }
      @Override
      public AIPlanResponseDTO  generateSubTasks(String title,Long id)
      {
        
        AIPlanResponseDTO response = new  AIPlanResponseDTO();
        response=planner.generateSubTasks(title,id);  
        return response; 
      }
           @Override
           public AIPlanResponseDTO generatePlan(Long taskid) {
      
          // PlanningContext context= new PlanningContext();
           PlanningDecision decision = new PlanningDecision();
           System.out.println("Entered generatePlan with taskId = " + taskid);
     
           AIPlanResponseDTO response =new AIPlanResponseDTO();
          
return response;
         
     
            
        }
        public AIAnalysisResponseDTO analyseTitle(String title ){
            AIAnalysisResponseDTO dto = new AIAnalysisResponseDTO();
            dto = planner.analyse(title);
            return dto;
        } 

       
    }
    

      