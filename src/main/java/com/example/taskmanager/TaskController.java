package com.example.taskmanager;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Get all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // Get task by ID
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Integer id) {
        return taskService.getTaskById(id);
    }

    // Create a new task
    @PostMapping
    public Task createTask(@Valid @RequestBody TaskDTO taskDTO) {
        return taskService.createTaskFromDTO(taskDTO);
    }

    // Update an existing task
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Integer id, @Valid @RequestBody  TaskDTO taskDTO) {
        return taskService.updateTaskFromDTO(id, taskDTO);
    }

    // Delete a task
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
    }

    // Get tasks by completion status
    @GetMapping("/status/{completed}")
    public List<Task> getTasksByStatus(@PathVariable boolean completed) {
        return taskService.getTasksByStatus(completed);
    }
}