package com.example.taskmanager;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskContextRepository extends JpaRepository<TaskContext, Integer> { 
    Optional<TaskContext> findByTaskId(Long taskId);
   
}
