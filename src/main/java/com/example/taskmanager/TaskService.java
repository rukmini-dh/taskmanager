package com.example.taskmanager;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
        List<TaskDTO> getAllTasks();
        TaskDTO getTaskById(Long id);
        List<TaskDTO> getTasksByUserName(String userName);
        TaskDTO createTask(TaskDTO taskDTO);
        TaskDTO updateTask(Long id, TaskDTO taskDTO);
        void deleteTaskById(Long id);
        List<TaskDTO> getTasksByPriority(Priority priority);
        List<TaskDTO> getTasksDueBefore(LocalDate date);
 }

 
   

   