package com.example.taskmanager;

import java.util.LinkedHashSet;
import java.util.Set;

    public class PlanningDecision {

    private Set<PlanningAction> actions =
            new LinkedHashSet<>();
            public void setActions(Set<PlanningAction> actions){this.actions=actions;}
            public Set<PlanningAction> getActions(){return actions;}

}

