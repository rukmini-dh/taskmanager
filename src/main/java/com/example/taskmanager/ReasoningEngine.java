package com.example.taskmanager;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ReasoningEngine {
    private final PlanningRuleService planningRuleService;
    public ReasoningEngine(PlanningRuleService planningRuleService) {
        this.planningRuleService=planningRuleService;
    }
    public PlanningDecision reason(TaskContext context) {

        PlanningDecision decision = new PlanningDecision();
        List<PlanningRule> rules = planningRuleService.getRules(context);

        for(PlanningRule rule:rules){

            decision.getActions().add(rule.getAction());

}
       
        return decision;
    }
}