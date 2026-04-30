package com.example.taskmanager;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
        List<TaskDTO> getAllTasks();
        TaskDTO getTaskById(Long id);
        TaskDTO createTask(TaskDTO taskDTO);
        TaskDTO updateTask(Long id, TaskDTO taskDTO);
        void deleteTaskById(Long id);
        List<TaskDTO> getTasksByPriority(String priority);
        List<TaskDTO> getTasksDueBefore(LocalDate date);
 }

 
   

   