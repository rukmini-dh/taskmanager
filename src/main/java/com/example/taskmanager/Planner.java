package com.example.taskmanager;

import com.example.taskmanager.knowledgebase.TemplateRepository;
import com.example.taskmanager.user.User;
import com.example.taskmanager.user.UserPreferenceModel;
import com.example.taskmanager.user.UserPreferenceModelRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import com.example.taskmanager.knowledgebase.Template;
import org.springframework.stereotype.Service;

import com.example.taskmanager.knowledgebase.ConceptDTO;
import com.example.taskmanager.knowledgebase.ConcernDTO;
import com.example.taskmanager.knowledgebase.ExperienceRepository;

import ch.qos.logback.core.boolex.Matcher;

@Service
public class Planner {
    private final TemplateRepository templateRepository;
    private final ConceptRepository conceptRepository;
private final ConcernRepository concernRepository;
private final ExperienceRepository experienceRepository;
private final ConceptConcernAssociationRepository  conceptConcernAssociationRepository ;
 private final TaskRepository taskRepository;
    private final UserPreferenceModelRepository userPreferenceModelRepository ;
public Planner (ConceptRepository conceptRepository,ConcernRepository concernRepository,ConceptConcernAssociationRepository  conceptConcernAssociationRepository,ExperienceRepository experienceRepository, TemplateRepository templateRepository,UserPreferenceModelRepository userPreferenceModelRepository,
    TaskRepository taskRepository){
    this.concernRepository= concernRepository;
    this.conceptRepository=conceptRepository;
    this.conceptConcernAssociationRepository=conceptConcernAssociationRepository; 
    this.experienceRepository=experienceRepository;
    this.templateRepository = templateRepository;
    this.taskRepository=taskRepository;
    this.userPreferenceModelRepository=userPreferenceModelRepository;
    
  
   }
    
   /*  List<String> matchedKeywords = new ArrayList<>(); 
    Set<String> selectedSteps = new LinkedHashSet<>();  */
    Intent loginIntent = new Intent(
        "LOGIN",
        List.of(
            "login",
            "authentication",
            "signin",
            "sign in",
            "credentials"
        ),
        List.of(
            "Create login UI",
            "Validate credentials",
            "Show login error",
            "Test authentication"
        ),
        List.of(
            "Test authentication"
           
        ),List.of(
            "Review authentication"
           
        )
    );

    Intent dashboardIntent = new Intent(
        "DASHBOARD",
        List.of(
            "dashboard",
            "metrics",
            "statistics"
        ),
        List.of(
            "Create dashboard layout",
            "Display metrics",
            "Test dashboard"
        ),
        List.of(
            
            "Test dashboard"
        ),
        List.of(
          "Review  dashboard layout"
        )
    );
    List<Intent> intents = List.of(
        loginIntent,
        dashboardIntent
       
    );

    
    public AIAnalysisResponseDTO analyse(String title) {
       AIAnalysisResponseDTO dto= new AIAnalysisResponseDTO();
      
        title=title.toLowerCase();
        java.util.regex.Matcher matcher =
        Pattern.compile("\\b\\d{2}-\\d{2}-\\d{4}\\b")
               .matcher(title);
    
    if (matcher.find()) {
    
        String dateText = matcher.group();
   
        LocalDate date =
            LocalDate.parse(
                dateText,
                DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    
        dto.setExtractedDate(date);
      
    } 
    if (dto.getExtractedDate() == null) {
        String dayWord=""; 
        if(title.contains("today") || title.contains("Today")){dayWord="today";}
        if(title.contains("tomorrow") || title.contains("Tomorrow")){dayWord="tomorrow";}
        if(title.contains("monday") || title.contains("Monday")){dayWord="monday";}
        if(title.contains("tuesday") || title.contains("Tuesday")){dayWord="tuesday";}
        if(title.contains("wednesday") || title.contains("Wednesday")){dayWord="wednesday";}
        if(title.contains("thursday") || title.contains("Thursday")){dayWord="thursday";}
        if(title.contains("friday") || title.contains("Friday")){dayWord="friday";}
        if(title.contains("saturday") || title.contains("Saturday")){dayWord="saturday";}
        switch(dayWord) {

    case "today":
        dto.setExtractedDate(LocalDate.now());
     
        break;

    case "tomorrow":
        dto.setExtractedDate(LocalDate.now().plusDays(1));
       
        break;

    case "monday":
        dto.setExtractedDate(
            LocalDate.now().with(
                TemporalAdjusters.nextOrSame(
                    DayOfWeek.MONDAY)));
       
        break;
        case "tuesday":
            dto.setExtractedDate(
                LocalDate.now().with(
                    TemporalAdjusters.nextOrSame(
                        DayOfWeek.TUESDAY)));
            
            break;
            case "wednesday":
        dto.setExtractedDate(
            LocalDate.now().with(
                TemporalAdjusters.nextOrSame(
                    DayOfWeek.WEDNESDAY)));
       
        break;
        case "thursday":
        dto.setExtractedDate(
            LocalDate.now().with(
                TemporalAdjusters.nextOrSame(
                    DayOfWeek.THURSDAY)));
       
        break;
    case "friday":
        dto.setExtractedDate(
            LocalDate.now().with(
                TemporalAdjusters.nextOrSame(
                    DayOfWeek.FRIDAY)));
                
        break;
        case "saturday":
        dto.setExtractedDate(
            LocalDate.now().with(
                TemporalAdjusters.nextOrSame(
                    DayOfWeek.SATURDAY)));
        
        break;
}
}
      
        if (title.contains("urgent")
            ||
        title.contains("high priority")) {

        dto.setExtractedPriority(
            Priority.HIGH
        );
       }
        
        if (title.contains("medium")
            ||
        title.contains("medium priority")) {

        dto.setExtractedPriority(
            Priority.MEDIUM
        );
        }
        if (title.contains("low")
            ||
        title.contains("low priority")) {

        dto.setExtractedPriority(
            Priority.LOW
        );
      }
      if (dto.getExtractedPriority() == null) {dto.setExtractedPriority(Priority.MEDIUM);}

        for (Intent intent : intents) {

            for (String keyword : intent.getKeywords()) {
        
                if (title.contains(keyword)) {
        
                    dto.getMatchedKeywords()
                           .add(keyword);

                   
                               
                    dto.getMatchedIntents()
                           .add(intent.getName());
                         
                    


                  
                    break;
                }
            }
        }
        return dto;
    }

    
       
       public  AIPlanResponseDTO generateSubTasks(String title,Long id)
       {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));

        User user = task.getUser();

        UserPreferenceModel preference = userPreferenceModelRepository.findByUser(user).orElse(null);

        boolean hasLearnedPreferences = preference != null;
        List<SubTaskDTO> dtoList = new ArrayList<>();
        
        String[] tokens = title.split("\\s+");
        System.out.println("========== generateSubTasks ==========");
        System.out.println("TITLE RECEIVED = [" + title + "]");
    

        for(String token:tokens)
        {
            System.out.println("LOOKING FOR CONCEPT = [" + token + "]");
           Concept conceptEntity =
           conceptRepository
           .findByName(token)
           .orElse(null);

           if (conceptEntity == null) {
            System.out.println(
                "!!! CONCEPT NOT FOUND = [" + token + "]"
            );
               continue;
               
           }
           System.out.println(
            "CONCEPT FOUND = "
            + conceptEntity.getName()
        );
           
          

for (ConceptConcernAssociation assoc :
    conceptEntity.getConceptConcernAssociations()) {

Concern concern = assoc.getConcern();
System.out.println(
    "===== CONCERN: " + concern.getName()  );
    System.out.println(
        "TEMPLATES = " + concern.getTemplates()
    );

    System.out.println(
        "TEMPLATE COUNT = " +
        (concern.getTemplates() == null
            ? "NULL"
            : concern.getTemplates().size())
    );





//************
Template bestTemplate = null;

if (hasLearnedPreferences) {

    double bestSimilarity = -1;

    for (Template template : concern.getTemplates()) {

        double similarity =
            calculateCosineSimilarity(preference, template);

        System.out.println(
            "Template " + template.getId()
            + " similarity = " + similarity
        );

        if (similarity > bestSimilarity) {
            bestSimilarity = similarity;
            bestTemplate = template;
        }
    }

    System.out.println(
        "BEST TEMPLATE = "
        + bestTemplate.getId()
        + " | similarity = "
        + bestSimilarity
    );
}
//***************      

 // now create the subtaskdto
 //************
 for (Template template : concern.getTemplates()) {

    System.out.println(
        "TEMPLATE FOUND = "
        + template.getId()
        + " | "
        + template.getText()
    );

    SubTaskDTO dto = new SubTaskDTO();

    dto.setDescription(template.getText());
    dto.setTemplateId(template.getId());
    dto.setTitle(concern.getName());
    dto.setSource(Source.AI);

    boolean recommended =
        hasLearnedPreferences
        && bestTemplate != null
        && template.getId().equals(bestTemplate.getId());
        System.out.println(
            "RECOMMENDED = "
            +         hasLearnedPreferences

            + " | "
            + recommended
        );
  
    dto.setRecommended(recommended);

    dtoList.add(dto);
}
    }}
 


    AIPlanResponseDTO response =
            new AIPlanResponseDTO();

    response.setSteps(dtoList);
    System.out.println(
        "TOTAL SUBTASKS GENERATED = "
        + dtoList.size()
    );
    System.out.println(
        "Exiting generate plan" );

    return response;
}
private double calculateCosineSimilarity(
    UserPreferenceModel userPreference,
    Template template) {

double userSpecificity =
    userPreference.getSpecificityPreference();

double userActionability =
    userPreference.getActionabilityPreference();

double userComplexity =
    userPreference.getComplexityPreference();

double templateSpecificity =
    template.getSpecificity();

double templateActionability =
    template.getActionability();

double templateComplexity =
    template.getComplexity();

double dotProduct =
    userSpecificity * templateSpecificity
    + userActionability * templateActionability
    + userComplexity * templateComplexity;

double userMagnitude =
    Math.sqrt(
        userSpecificity * userSpecificity
        + userActionability * userActionability
        + userComplexity * userComplexity
    );

double templateMagnitude =
    Math.sqrt(
        templateSpecificity * templateSpecificity
        + templateActionability * templateActionability
        + templateComplexity * templateComplexity
    );

if (userMagnitude == 0 || templateMagnitude == 0) {
    return 0;
}

return dotProduct / (userMagnitude * templateMagnitude);
}
    
           



   
        
 
}