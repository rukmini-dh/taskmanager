package com.example.taskmanager;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanningRuleRepository
extends JpaRepository<PlanningRule,Long>{

    List<PlanningRule>findByIntentAndPriority(String intent, Priority priority);

}