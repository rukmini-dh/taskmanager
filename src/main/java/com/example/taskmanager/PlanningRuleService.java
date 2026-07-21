package com.example.taskmanager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 @Service
public class PlanningRuleService {

   private final PlanningRuleRepository repository;

   public PlanningRuleService(PlanningRuleRepository repository){
    this.repository=repository;

   }

    public List<PlanningRule> getRules(TaskContext context)
    {

        List<PlanningRule> resp= new ArrayList<>();
         return resp;
}
    
}
