package com.example.taskmanager;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskServiceImpl taskServiceImpl;

    public TaskController(TaskServiceImpl taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }
    @GetMapping("/priority/{priority}")
    public List<TaskDTO> getTasksByPriority(@PathVariable Priority priority) {
        return taskServiceImpl.getTasksByPriority(priority);
    }

    @GetMapping("/due-before/{dueDate}")
    public List<TaskDTO> getTasksDueBefore(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate) {
        return taskServiceImpl.getTasksDueBefore(dueDate);
    }

    // Get all tasks
    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskServiceImpl.getAllTasks();
    }

    // Get task by ID
    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Integer id) {
        return taskServiceImpl.getTaskById(id);
    }

    // Create a new task
    @PostMapping
    public TaskDTO createTask(@Valid @RequestBody TaskDTO taskDTO) {
        return taskServiceImpl.createTask(taskDTO);
    }

    // Update an existing task
    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Integer id, @Valid @RequestBody  TaskDTO taskDTO) {
        return taskServiceImpl.updateTask(id, taskDTO);
    }

    // Delete a task
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Integer id) {
        taskServiceImpl.deleteTask(id);
    }

    // Get tasks by completion status
    @GetMapping("/status/{completed}")
    public List<TaskDTO> getTasksByStatus(@PathVariable boolean completed) {
        return taskServiceImpl.getTasksByStatus(completed);
    }
}