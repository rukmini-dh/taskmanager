package com.example.taskmanager;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
        List<TaskDTO> getAllTasks();
        TaskDTO getTaskById(Integer id);
        TaskDTO createTask(TaskDTO taskDTO);
        TaskDTO updateTask(Integer id, TaskDTO taskDTO);
        void deleteTaskById(Integer id);
        List<TaskDTO> getTasksByPriority(Priority priority);
        List<TaskDTO> getTasksDueBefore(LocalDate date);
 }

 
   

   