package com.example.taskmanager;

import com.example.taskmanager.knowledgebase.TemplateRepository;
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
public Planner (ConceptRepository conceptRepository,ConcernRepository concernRepository,ConceptConcernAssociationRepository  conceptConcernAssociationRepository,ExperienceRepository experienceRepository, TemplateRepository templateRepository){
    this.concernRepository= concernRepository;
    this.conceptRepository=conceptRepository;
    this.conceptConcernAssociationRepository=conceptConcernAssociationRepository; 
    this.experienceRepository=experienceRepository;
    this.templateRepository = templateRepository;
  
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

    private double calculateScore(Template template, List<Template> templates) {

        double score = template.getWeight();
    
        // 1. Acceptance signal
        double acceptanceRate =
            template.getTimesSuggested() == 0
            ? 0
            : (double) template.getTimesAccepted()
              / template.getTimesSuggested();
    
        score += acceptanceRate * 4.0;
    
        // 2. Rejection signal
        double rejectionRate =
            template.getTimesSuggested() == 0
            ? 0
            : (double) template.getTimesRejected()
              / template.getTimesSuggested();
    
        score -= rejectionRate * 5.0;
    
        // 3. Over-exposure penalty
        double averageSuggestions =
            templates.stream()
                     .mapToInt(Template::getTimesSuggested)
                     .average()
                     .orElse(0);
    
        double overExposurePenalty =
            Math.max(
                0,
                template.getTimesSuggested() - averageSuggestions
            ) * 0.5;
    
        score -= overExposurePenalty;
    
        return score;
    }
    private Template chooseByScore(List<Template> templates) {

        Template bestTemplate = null;
        double bestScore = Double.NEGATIVE_INFINITY;
    
        for (Template template : templates) {
    
            double score = calculateScore(template, templates);
    
            System.out.println(
                "Template " + template.getId()
                + " | " + template.getText()
                + " | score = " + score
            );
    
            if (bestTemplate == null || score > bestScore) {
                bestTemplate = template;
                bestScore = score;
            }
        }
    
        return bestTemplate;
    }
        public AIPlanResponseDTO generateSubTasks(String title)
        {
            List<SubTaskDTO> dtoList = new ArrayList<>();
            String[] tokens = title.split("\\s+");
           

            for(String token:tokens)
            {
               
               Concept conceptEntity =
               conceptRepository
               .findByName(token)
               .orElse(null);

               if (conceptEntity == null) {
                   continue;
               }
               
              

for (ConceptConcernAssociation assoc :
        conceptEntity.getConceptConcernAssociations()) {

    Concern concern = assoc.getConcern();

    SubTaskDTO dto = new SubTaskDTO();

    dto.setTitle(concern.getName());
    dto.setSource(Source.AI); 
    List<Template> templates = concern.getTemplates();
    
    if (templates == null || templates.isEmpty()) {
    
        dto.setDescription("");
    
    } else {
    
    System.out.println(
        "===== TEMPLATES FOR CONCERN: " + concern.getName() + " ====="
    );
    for (Template t : templates) {
    System.out.println(
        "Template " + t.getId()
        + " | timesSuggested = " + t.getTimesSuggested()
        + " | " + t.getText()
    );
}
final int MIN_SUGGESTIONS = 3;
final int MAX_CONSECUTIVE_ACCEPTANCES = 5;
final int MAX_CONSECUTIVE_REJECTIONS = 3;

boolean allTemplatesExplored =
    templates.stream()
             .allMatch(t -> t.getTimesSuggested() >= MIN_SUGGESTIONS);

    
    Template selected;
    System.out.println("ALL TEMPLATES SHOWN = " + allTemplatesExplored);

    for (Template t : templates) {
        System.out.println(
            "BEFORE SELECTION: "
            + t.getId()
            + " -> timesSuggested="
            + t.getTimesSuggested()
        );
        
    }

    if (!allTemplatesExplored) {
        
        selected = chooseLeastSuggestedTemplate(templates);
        System.out.println(
            "========== SELECTED TEMPLATE not all = "
            + selected.getId()
            + " | " + selected.getText()
            + " =========="
        );
    } else {
        for (Template t : templates) {

            if (t.getCooldown() > 0) {
                t.setCooldown(t.getCooldown() - 1);
               
            }
   
        }
   
        templateRepository.saveAll(templates);
        List<Template> eligibleTemplates = templates.stream()
        .filter(t -> t.getCooldown() == 0)
        .toList();

        if (eligibleTemplates.isEmpty()) {

            selected = templates.stream()
                .min(Comparator.comparingInt(Template::getCooldown))
                .orElse(null);
        
        } else {
        
          
        selected = chooseByScore(eligibleTemplates);
        System.out.println(
            "========== SELECTED TEMPLATE = "
            + selected.getId()
            + " | " + selected.getText()
            + " =========="
        );
    }
    }

if (selected != null) {
    dto.setDescription(selected.getText());
    dto.setTemplateId(selected.getId());
}
   
}
        
    dtoList.add(dto);
}
   

}
         
            
    AIPlanResponseDTO response =
            new AIPlanResponseDTO();

    response.setSteps(dtoList);

    return response;
}
           

Template  chooseLeastSuggestedTemplate(List<Template> templates){
    
    int minSuggested = templates.stream()
        .mapToInt(Template::getTimesSuggested)
        .min()
        .orElse(0);
        List<Template> candidates = templates.stream()
        .filter(t -> t.getTimesSuggested() == minSuggested)
        .toList();
        Random random = new Random();
Template template =
    candidates.get(random.nextInt(candidates.size()));
        return template;
}

   
        
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