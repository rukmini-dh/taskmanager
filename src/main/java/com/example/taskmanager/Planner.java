package com.example.taskmanager;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class Planner {
    
   /*  List<String> matchedKeywords = new ArrayList<>(); 
    Set<String> selectedSteps = new LinkedHashSet<>();  */
    Intent loginIntent = new Intent(
        "LOGIN",
        List.of(
            "login",
            "authentication",
            "signin",
            "sign in",
            "credentials"
        ),
        List.of(
            "Create login UI",
            "Validate credentials",
            "Show login error",
            "Test authentication"
        )
    );

    Intent dashboardIntent = new Intent(
        "DASHBOARD",
        List.of(
            "dashboard",
            "metrics",
            "statistics"
        ),
        List.of(
            "Create dashboard layout",
            "Display metrics",
            "Test dashboard"
        )
    );
    List<Intent> intents = List.of(
        loginIntent,
        dashboardIntent
       
    );

    
    public PlanningContext analyse(String title) {

        PlanningContext context = new PlanningContext();
          
        for (Intent intent : intents) {

                for (String keyword : intent.getKeywords()) {
            
                    if (title.contains(keyword)) {
            
                        context.getMatchedKeywords().add(keyword);
                        
                        context.getSelectedSteps().addAll(
                            intent.getSubtasks()
                        );   
                       
            
                        break;
                    }
                }
            }
         
          
        return context;
    }
}