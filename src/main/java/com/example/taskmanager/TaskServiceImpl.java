package com.example.taskmanager;

import java.time.LocalDate;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // Delete task
    public void deleteTask(Integer id) {
              taskRepository.deleteById(id);
    }
    public List<TaskDTO> getTasksDueBefore(LocalDate dueDate){
        
            return (taskRepository.findByDueDateBefore(dueDate)).stream()
            .map(this::convertToDTO).toList();
        }

    public TaskDTO getTaskById(Integer id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        return convertToDTO(task);
    }
    // Find tasks by completion status
    public List<TaskDTO> getTasksByStatus(boolean completed) {
        return taskRepository.findByCompleted(completed).stream()
        .map(this::convertToDTO)
        .toList();
    }

    // get task by priority
    public List<TaskDTO> getTasksByPriority(Priority priority) {
        return taskRepository.findByPriority(priority).stream()
        .map(this::convertToDTO)
        .toList();
    }
    // Delete task
    public void deleteTaskById(Integer id) {

              taskRepository.deleteById(id);
    }
     // Update task
    public TaskDTO updateTask(Integer id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
         
        if (task.getTitle() != null) task.setTitle(taskDTO.getTitle());
        task.setCompleted(taskDTO.isCompleted());
        task.setPriority(taskDTO.getPriority());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        return convertToDTO(taskRepository.save(task));
    }
              
    // Create new task
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setCompleted(taskDTO.isCompleted());
        task.setPriority(taskDTO.getPriority());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        return convertToDTO(taskRepository.save(task));
    }              
    // 🔹 Helper method
    private TaskDTO convertToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.isCompleted());
        dto.setPriority(task.getPriority());
        dto.setDueDate(task.getDueDate());
        return dto;
    }
    
}
