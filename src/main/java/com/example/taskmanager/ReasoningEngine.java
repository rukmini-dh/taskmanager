package com.example.taskmanager;
import org.springframework.stereotype.Service;

@Service
public class ReasoningEngine {

    public PlanningDecision reason(TaskContext context) {

        PlanningDecision decision = new PlanningDecision();
      if(context.getExtractedPriority()==Priority.HIGH){
        decision.setNeedsTesting(true);

        decision.setNeedsCodeReview(true);}
     

        // reasoning goes here
       
        return decision;
    }
}