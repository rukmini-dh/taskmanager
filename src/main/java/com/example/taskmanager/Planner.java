package com.example.taskmanager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.example.taskmanager.knowledgebase.ConceptDTO;
import com.example.taskmanager.knowledgebase.ConcernDTO;

import ch.qos.logback.core.boolex.Matcher;

@Service
public class Planner {
    private final ConceptRepository conceptRepository;
private final ConcernRepository concernRepository;
private final ConceptConcernAssociationRepository  conceptConcernAssociationRepository ;
public Planner (ConceptRepository conceptRepository,ConcernRepository concernRepository,ConceptConcernAssociationRepository  conceptConcernAssociationRepository){
    this.concernRepository= concernRepository;
    this.conceptRepository=conceptRepository;
    this.conceptConcernAssociationRepository=conceptConcernAssociationRepository; 
  
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
    public PlanningContext buildPlanningContext(AIAnalysisResponseDTO dto){
        PlanningContext context =
        new PlanningContext();

context.setMatchedIntents(
        dto.getMatchedIntents());

context.setMatchedKeywords(
        dto.getMatchedKeywords());

context.setExtractedPriority(
        dto.getExtractedPriority());

        return context;
    }
        public AIPlanResponseDTO generateSubTasks(String title)
        {
            List<SubTaskDTO> dtoList = new ArrayList<>();
            String[] tokens = title.split("\\s+");
            ConceptDTO concept=new ConceptDTO();

            for(String token:tokens)
            {
               
               Concept conceptEntity =
               conceptRepository
               .findByName(token)
               .orElse(null);

               if (conceptEntity == null) {
                   continue;
               }
               
               Random random = new Random();

for (ConceptConcernAssociation assoc :
        conceptEntity.getConceptConcernAssociations()) {

    Concern concern = assoc.getConcern();

    SubTaskDTO dto = new SubTaskDTO();

    dto.setTitle(concern.getName());

    List<String> templates =
            concern.getTemplates();
            System.out.println("Templates in Planner ******"+concern.getTemplates());

    if (templates == null || templates.isEmpty()) {

        dto.setDescription("");

    } else {

        int index =
                random.nextInt(templates.size());

        dto.setDescription(
                templates.get(index));
    }

    dtoList.add(dto);

}
            }
            
    AIPlanResponseDTO response =
            new AIPlanResponseDTO();

    response.setSteps(dtoList);

    return response;
}
   
        /* 
                    if (conceptConcernAssociationRepository
                        .findByConceptAndConcern(conceptEntity, concernEntity)
                        .isEmpty()) {
                
                    ConceptConcernAssociation association =
                        new ConceptConcernAssociation(
                            conceptEntity,
                            concernEntity,
                            concern.getTimesSuggested(),
                            concern.getTimesAccepted(),
                            concern.getTimesRejected()
                        );
            }
 */
        
    public AIPlanResponseDTO generatePlan(TaskContext context,PlanningDecision  decision)
      {
      
      
    Set<String> selectedSteps =
            new LinkedHashSet<>();
           

            for (Intent intent : intents) {

               

                    if (context.getMatchedIntents().contains(intent.getName())) {
                
                        selectedSteps.addAll(intent.getBaseSteps());
                
                    }
                    
            }

    List<SubTaskDTO> dtoList =
            selectedSteps.stream()
            .map(step -> {
                SubTaskDTO dto =
                        new SubTaskDTO();

                dto.setTitle(step);
                dto.setCompleted(false);
                dto.setReviewed(false);
                dto.setSource(Source.AI);

                return dto;
            })
            .toList();

    AIPlanResponseDTO response =
            new AIPlanResponseDTO();

    response.setSteps(dtoList);

    return response;
}
}