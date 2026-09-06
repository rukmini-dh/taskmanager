package com.example.taskmanager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.taskmanager.knowledgebase.Experience;
import com.example.taskmanager.knowledgebase.ExperienceRepository;
import com.example.taskmanager.knowledgebase.FeedbackType;
import com.example.taskmanager.knowledgebase.Template;
import com.example.taskmanager.knowledgebase.TemplateRepository;
import com.example.taskmanager.security.SecurityUtil;
import com.example.taskmanager.user.User;
import com.example.taskmanager.user.UserNotFoundException;
import com.example.taskmanager.user.UserPreferenceModel;
import com.example.taskmanager.user.UserPreferenceModelRepository;
import com.example.taskmanager.user.UserRepository;



@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final SubTaskRepository subtaskRepository;
    private final UserRepository userRepository;
    private final TaskContextRepository taskContextRepository;
    private final TemplateRepository templateRepository;
    private final ExperienceRepository experienceRepository;
    
    private final UserPreferenceModelRepository userPreferenceModelRepository;
    public TaskServiceImpl(TaskRepository taskRepository,TemplateRepository templateRepository,UserRepository userRepository,SubTaskRepository subtaskRepository,TaskContextRepository taskContextRepository,ExperienceRepository experienceRepository,UserPreferenceModelRepository userPreferenceModelRepository) {

    this.taskRepository = taskRepository;
    this.userRepository = userRepository;
    this.subtaskRepository=subtaskRepository;
    this.taskContextRepository=taskContextRepository;
    this.templateRepository=templateRepository;
    this.experienceRepository=experienceRepository;
    this.userPreferenceModelRepository=userPreferenceModelRepository;
}
private static final Logger log =
        LoggerFactory.getLogger(TaskServiceImpl.class);

   
    
    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findByDeletedFalse()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }
    @Override
    public List<SubTaskDTO> getAllSubTasks() {
        return subtaskRepository.findAll()
                .stream()
                .map(this::convertToSubTaskDTO)
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
    public List<SubTaskDTO> getByTask_Id(Long id) {
        
        return (subtaskRepository.findByTask_Id(id)).stream()
            .map(this::convertToSubTaskDTO).toList();
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
    // Update subtask
    @Transactional
    public SubTaskDTO updateSubTask(SubTaskDTO subtaskDTO, Long id) {

        SubTask subtask = subtaskRepository.findById(id)
            .orElseThrow(() ->
                new SubTaskNotFoundException(
                    "SubTask not found with id: " + id
                )
            );
    
        if (subtask.getTitle() != null) {
            subtask.setTitle(subtaskDTO.getTitle());
        }
    
        subtask.setCompleted(subtaskDTO.isCompleted());
    
        Task task = subtask.getTask();
    
        List<SubTask> subtasks =
            subtaskRepository.findByTask_Id(task.getId());
    
        boolean allCompleted =
            subtasks.stream().allMatch(SubTask::isCompleted);
    
        if (allCompleted) {
            task.setCompleted(true);
            taskRepository.save(task);
        }
    
        subtask.setSource(subtaskDTO.getSource());
        subtask.setDeleted(subtaskDTO.isDeleted());
        subtask.setEdited(subtaskDTO.isEdited());
        subtask.setFeedback(subtaskDTO.getFeedback());
        subtask.setDescription(subtaskDTO.getDescription());
    
    
        // =========================
        // MACHINE LEARNING
        // =========================
        log.info("Feedback received by updateSubTask = [{}]",
         subtaskDTO.getFeedback());
    
        if (subtaskDTO.getFeedback() != null) {
    
            User user = subtask.getTask().getUser();
    
            boolean accepted =
                subtaskDTO.getFeedback() == FeedbackType.ACCEPTED;
    
            double actual = accepted ? 1.0 : 0.0;
    
            UserPreferenceModel preference =
                userPreferenceModelRepository
                    .findByUser(user)
                    .orElse(null);
    
            if (preference == null) {
                preference = new UserPreferenceModel();
                preference.setUser(user);
            }
    
            // Prediction using CURRENT weights
            double prediction =
                calculatePrediction(
                    preference,
                    subtask.getTemplate()
                );
    
            // Error
            double error = prediction - actual;
    
            // Gradients
            double gradientSpecificity =
                error * subtask.getTemplate().getSpecificity();
    
            double gradientActionability =
                error * subtask.getTemplate().getActionability();
    
            double gradientComplexity =
                error * subtask.getTemplate().getComplexity();
    
            double learningRate = 0.1;
    
            // Weight updates
            preference.setSpecificityWeight(
                preference.getSpecificityWeight()
                - learningRate * gradientSpecificity
            );
    
            preference.setActionabilityWeight(
                preference.getActionabilityWeight()
                - learningRate * gradientActionability
            );
    
            preference.setComplexityWeight(
                preference.getComplexityWeight()
                - learningRate * gradientComplexity
            );
    
            userPreferenceModelRepository.save(preference);
            System.out.println("TEMPLATE = " + subtask.getTemplate().getId());
            log.info("PREDICTION = {}", prediction);
            log.info("========== ML UPDATE ==========");
            log.info("SubTask ID = {}", subtask.getId());
            log.info("Template ID = {}", subtask.getTemplate().getId());
            log.info("Feedback = {}", subtaskDTO.getFeedback());
            log.info("Actual = {}", actual);
            log.info("Prediction = {}", prediction);
            log.info("Error = {}", error);
            log.info("Specificity gradient = {}", gradientSpecificity);
            log.info("Actionability gradient = {}", gradientActionability);
            log.info("Complexity gradient = {}", gradientComplexity);
            log.info("New specificity weight = {}", preference.getSpecificityWeight());
            log.info("New actionability weight = {}", preference.getActionabilityWeight());
            log.info("New complexity weight = {}", preference.getComplexityWeight());
            log.info("================================");
        }
    
    
        // =========================
        // SAVE SUBTASK
        // =========================
    
        SubTask saved =
            subtaskRepository.save(subtask);
    
        return convertToSubTaskDTO(saved);
    }
    double calculatePrediction(UserPreferenceModel preference,Template template)
{    double z = template.getSpecificity()*preference.getSpecificityWeight()+template.getActionability()*preference.getActionabilityWeight()+template.getComplexity()*preference.getComplexityWeight();
     return sigmoid(z);
}
private double sigmoid(double z) {
    return 1.0 / (1.0 + Math.exp(-z));
}
/*     private boolean isTerminalAIOutcome(SubTask subTask) {

        if (subTask.getTemplate() == null) {
            return false;
        }
    
        if (subTask.getFeedback() == FeedbackType.REJECTED) {
            return true;
        }
    
        return subTask.getFeedback() == FeedbackType.ACCEPTED
                && subTask.isCompleted();
    }
    private void createExperienceIfNeeded(SubTask subTask) {

    if (experienceRepository.existsBySubTaskId(subTask.getId())) {
        return;
    }

    // --------------------------------------------------
    // 1. Find the PREVIOUS experience
    // --------------------------------------------------

    Optional<Experience> previousExperience =
            experienceRepository.findTopByOrderByIdDesc();

   Long previousTemplateId = null;

    if (previousExperience.isPresent()) {
        SubTask previousSubTask =
        previousExperience.get().getSubTask();

         previousTemplateId =
        previousSubTask.getTemplate().getId();

        if (previousSubTask != null &&
            previousSubTask.getTemplate() != null) {

            previousTemplateId =
                    previousSubTask.getTemplate().getId();
        }
    }

    // --------------------------------------------------
    // 2. Compare previous template with CURRENT template
    // --------------------------------------------------

    Long currentTemplateId =
            subTask.getTemplate().getId();

    boolean sameTemplate =
            previousTemplateId != null &&
            previousTemplateId.equals(currentTemplateId);

    System.out.println(
        "PREVIOUS EXPERIENCE TEMPLATE = "
        + previousTemplateId
    );

    System.out.println(
        "CURRENT TEMPLATE = "
        + currentTemplateId
    );

    System.out.println(
        "SAME TEMPLATE = "
        + sameTemplate
    );

    // --------------------------------------------------
    // 3. Create the CURRENT experience
    // --------------------------------------------------

    Experience experience = new Experience();

    experience.setSubTask(subTask);
    experience.setCreatedAt(LocalDateTime.now());

    // --------------------------------------------------
    // 4. Update template counters
    // --------------------------------------------------

    Template template = templateRepository
            .findById(currentTemplateId)
            .orElseThrow(() ->
                new RuntimeException("Template not found"));

    template.setTimesSuggested(
        template.getTimesSuggested() + 1
    );

    if (subTask.getFeedback() == FeedbackType.ACCEPTED) {

        template.setTimesAccepted(
            template.getTimesAccepted() + 1
        );

        if (sameTemplate) {
            template.setConsecutiveAcceptances(
                template.getConsecutiveAcceptances() + 1
            );
        } else {
            template.setConsecutiveAcceptances(1);
        }

        template.setConsecutiveRejections(0);
        if (template.getConsecutiveAcceptances() >= 5) {
            template.setCooldown(3);
        }

    }
    else if (subTask.getFeedback() == FeedbackType.REJECTED) {

        template.setTimesRejected(
            template.getTimesRejected() + 1
        );

        if (sameTemplate) {
            template.setConsecutiveRejections(
                template.getConsecutiveRejections() + 1
            );
        } else {
            template.setConsecutiveRejections(1);
        }

        template.setConsecutiveAcceptances(0);
      
        if (template.getConsecutiveRejections() >= 3) {
            template.setCooldown(3);
        }
    }

    // --------------------------------------------------
    // 5. NOW save the current Experience
    // --------------------------------------------------

    experienceRepository.save(experience);

    // --------------------------------------------------
    // 6. Save updated Template
    // --------------------------------------------------

    templateRepository.save(template);
} */
    // create a new subtask
    public SubTaskDTO createSubTask(SubTaskDTO dto,long id)   {
        SubTask subtask =new SubTask();
        subtask.setTitle(dto.getTitle());
        subtask.setDescription(dto.getDescription());
        subtask.setSource(dto.getSource());
        subtask.setCompleted(dto.isCompleted());   
        subtask.setDeleted(dto.isDeleted());   
        subtask.setEdited(dto.isEdited());
        subtask.setFeedback(dto.getFeedback());
        subtask.setRecommended(dto.isRecommended());
        if (dto.getTemplateId() != null) {
            Template template = templateRepository
                    .findById(dto.getTemplateId())
                    .orElseThrow(() ->
                        new RuntimeException(
                            "Template not found: " + dto.getTemplateId()
                        )
                    );
        
            subtask.setTemplate(template); 
                }
        
        //subtask.setCreatedAt(LocalDateTime.now());
        Task task = taskRepository
                .findById(id)
                .orElseThrow(() -> new  TaskNotFoundException("Task not found with id: " + id));

            subtask.setTask(task);
            subtaskRepository.save(subtask);
            System.out.printf("Subtask",subtask);
        return convertToSubTaskDTO(subtask);

    }      
     // Create new task
    public TaskDTO createTask(TaskDTO dto) {

        String username = SecurityUtil.getCurrentUsername();
        System.out.println(
            SecurityUtil.getCurrentUsername()
        );
        
        User user = userRepository.findByUserName(username)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
        System.out.println("in Task Service imp*******"+dto);
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
    public void saveTaskContext(CreateTaskContextRequest dto) {

        Task task = taskRepository
        .findById(dto.getTaskId())
        .orElseThrow(() -> new  TaskNotFoundException("Task not found with id: " + dto.getTaskId()));
        TaskContext context = new TaskContext();
        context.setMatchedIntents(
            dto.getAnalysis().getMatchedIntents());
    
    context.setMatchedKeywords(
            dto.getAnalysis().getMatchedKeywords());
    
    context.setExtractedPriority(
            dto.getAnalysis().getExtractedPriority());
    
            context.setExtractedDate(
                dto.getAnalysis().getExtractedDate());
        
        context.setCreatedAt(
                LocalDateTime.now());
    context.setTask(task);     
       taskContextRepository.save(context);
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
    private SubTaskDTO convertToSubTaskDTO(SubTask subtask) {
        SubTaskDTO dto = new SubTaskDTO();
         dto.setSource(subtask.getSource());
         dto.setDescription(subtask.getDescription());
        dto.setTitle(subtask.getTitle());
        dto.setEdited(subtask.isEdited());
        dto.setCompleted(subtask.isCompleted());
        dto.setDeleted(subtask.isDeleted());
        dto.setFeedback(subtask.getFeedback());
        dto.setRecommended(subtask.isRecommended());
        dto.setTemplateId(
            subtask.getTemplate() != null
                ? subtask.getTemplate().getId()
                : null
        );
     

       
        dto.setId(subtask.getId());
        return dto;
    }

    @Override
    public void deleteTaskById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteTaskById'");
    }
    
}
