package com.example.taskmanager;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
public interface SubTaskRepository extends JpaRepository<SubTask, Integer> { 
    Optional<SubTask> findById(Long id);
    List<SubTask> findByTask_Id(Long id);
}
