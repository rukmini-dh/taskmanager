package com.example.taskmanager;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

import com.example.taskmanager.security.SecurityUtil;
import com.example.taskmanager.user.User;
import com.example.taskmanager.user.UserNotFoundException;
import com.example.taskmanager.user.UserRepository;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository,UserRepository userRepository) {

    this.taskRepository = taskRepository;
    this.userRepository = userRepository;
}


   
    
    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findByDeletedFalse()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // Delete task
    public void deleteTask(Long id) {
              taskRepository.deleteById(id);
    }
    public List<TaskDTO> getTasksDueBefore(LocalDate dueDate){
        
            return (taskRepository.findByDueDateBefore(dueDate)).stream()
            .map(this::convertToDTO).toList();
        }

    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        return convertToDTO(task);
    }
    // find tasks by users
    public List<TaskDTO> getTasksByUserName(String username) {

        User user = userRepository
            .findByUserName(username)
            .orElseThrow(() ->
                new UserNotFoundException(
                    "User not found"
                )
            );
    
        return taskRepository.findByUser(user)
            .stream()
            .map(this::convertToDTO)
            .toList();
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
   
     // Update task
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
        if (task.getTitle() != null) task.setTitle(taskDTO.getTitle());
        task.setCompleted(taskDTO.isCompleted());
        task.setPriority(taskDTO.getPriority());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        
        task.setDeleted(taskDTO.isDeleted());
        return convertToDTO(taskRepository.save(task));
    }
              
    // Create new task
    public TaskDTO createTask(TaskDTO dto) {

        String username = SecurityUtil.getCurrentUsername();
        System.out.println(
            SecurityUtil.getCurrentUsername()
        );
        
        User user = userRepository.findByUserName(username)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setUser(user); // ALWAYS SAFE NOW
        task.setDueDate(dto.getDueDate());
        task.setCompleted(dto.isCompleted());
        task.setPriority(dto.getPriority());
        task.setDeleted(false);
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
        dto.setDeleted(task.isDeleted());
        dto.setUserName(
            task.getUser() != null
                ? task.getUser().getUserName()
                : null
        );
        return dto;
    }

    @Override
    public void deleteTaskById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteTaskById'");
    }
    
}
