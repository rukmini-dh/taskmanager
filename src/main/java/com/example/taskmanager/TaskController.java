package com.example.taskmanager;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
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
    //get all substasks
    @GetMapping("/subtasks/{id}")
    public List<SubTaskDTO> getByTask_Id(@PathVariable Long id) {
    return taskServiceImpl.getByTask_Id(id);
    }

    // Get all tasks
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<TaskDTO> getAllTasks() {
    return taskServiceImpl.getAllTasks();
}
    /* @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskServiceImpl.getAllTasks();
    } */

    // Get task by ID
    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskServiceImpl.getTaskById(id);
    }
    // Get tasks by user
    @GetMapping("/user/{userName}")
    public List<TaskDTO> getTasksByUser( @PathVariable String userName){
        return taskServiceImpl.getTasksByUserName(userName);
    }

    // Create a new task
    @PostMapping
   
public TaskDTO createTask(@RequestBody TaskDTO dto)
                          {
    return taskServiceImpl.createTask(dto);
}
// Create a new taskContext


@PostMapping("/task-context")
public void saveTaskContext(
        @RequestBody CreateTaskContextRequest request) {

     taskServiceImpl.saveTaskContext(request);
}
// Create a new subtask
@PostMapping("/subtask/{id}")

public SubTaskDTO createSubTask(@RequestBody SubTaskDTO dto, @PathVariable long id)
                      {System.out.println("Controller reached");
return taskServiceImpl.createSubTask(dto,id);
}

// update an exising subtask
@PutMapping("/editsubtask/{id}")
public SubTaskDTO updateSubTask(@RequestBody SubTaskDTO dto,@PathVariable long id){
    return taskServiceImpl.updateSubTask(dto,id); 
}

    // Update an existing task
    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Long id, @Valid @RequestBody  TaskDTO taskDTO) {
        return taskServiceImpl.updateTask(id, taskDTO);
    }

    // Delete a task
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskServiceImpl.deleteTask(id);
    }

    // Get tasks by completion status
    @GetMapping("/status/{completed}")
    public List<TaskDTO> getTasksByStatus(@PathVariable boolean completed) {
        return taskServiceImpl.getTasksByStatus(completed);
    }
}