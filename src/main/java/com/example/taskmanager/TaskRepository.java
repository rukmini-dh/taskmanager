package com.example.taskmanager;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByCompleted(boolean completed);
    List<Task> findByPriority(String priority);
    List<Task> findByDueDateBefore(LocalDate dueDate);
    Optional<Task> findById(Long id);
    Void deleteById(Long id);
    
}
    


