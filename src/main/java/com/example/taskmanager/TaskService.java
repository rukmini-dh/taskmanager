package com.example.taskmanager;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    // Constructor injection
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Get task by ID
    public Task getTaskById(Integer id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    // Create new task
    public Task createTaskFromDTO(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setCompleted(taskDTO.isCompleted());
        return taskRepository.save(task);
    }

    // Update task
    public Task updateTaskFromDTO(Integer id, TaskDTO taskDTO) {
        Task task = getTaskById(id);
        if (task.getTitle() != null) task.setTitle(taskDTO.getTitle());
        task.setCompleted(taskDTO.isCompleted());
        return taskRepository.save(task);
    }

    // Delete task
    public void deleteTask(Integer id) {
              taskRepository.deleteById(id);
    }

    // Find tasks by completion status
    public List<Task> getTasksByStatus(boolean completed) {
        return taskRepository.findByCompleted(completed);
    }
}