package com.example.taskmanager;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskContextRepository extends JpaRepository<TaskContext, Integer> { 
}
