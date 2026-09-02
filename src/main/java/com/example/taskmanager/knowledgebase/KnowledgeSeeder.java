
package com.example.taskmanager.knowledgebase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.example.taskmanager.Concept;
import com.example.taskmanager.ConceptConcernAssociation;
import com.example.taskmanager.ConceptConcernAssociationRepository;
import com.example.taskmanager.ConceptRepository;
import com.example.taskmanager.Concern;
import com.example.taskmanager.ConcernRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class KnowledgeSeeder
        implements CommandLineRunner {
private final ConceptRepository conceptRepository;
private final ConcernRepository concernRepository;
private final ConceptConcernAssociationRepository  conceptConcernAssociationRepository ;
String conceptName="";
String concerName="";


public KnowledgeSeeder(ConceptRepository conceptRepository,ConcernRepository concernRepository,ConceptConcernAssociationRepository  conceptConcernAssociationRepository){
        this.concernRepository= concernRepository;
        this.conceptRepository=conceptRepository;
        this.conceptConcernAssociationRepository=conceptConcernAssociationRepository; 
      
       }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("KnowledgeSeeder started...");

        // Read JSON
        Resource resource =
        new ClassPathResource("knowledge.json");
        // Convert JSON to DTO

ObjectMapper mapper =
        new ObjectMapper();
        String test = """
{
  "text":"Hello",
  "weight":10
}
""";
System.out.println("PATH****"+resource.getURL());
TemplateDTO dto =
        mapper.readValue(test, TemplateDTO.class);

System.out.println(dto.getText());
System.out.println(dto.getWeight());

KnowledgeDTO knowledge =
        mapper.readValue(
                resource.getInputStream(),
                KnowledgeDTO.class);
             
                System.out.println(
                        mapper.writerWithDefaultPrettyPrinter()
                              .writeValueAsString(knowledge)
                    );

     
        for (ConceptDTO concept : knowledge.getConcepts()) {

               conceptName= concept.getName();
               

               Concept conceptEntity =
               conceptRepository
               .findByName(concept.getName())
               .orElseGet(() -> {
                   Concept c = toEntity(concept);
                   return conceptRepository.save(c);
               });
                for (ConcernDTO concern : concept.getConcerns()) {
                     concerName=concern.getName();
                     System.out.println("Concern name in seeder"+concern.getName());
                        System.out.println("Template in seeder"+concern.getTemplates());
                        System.out.println("Looking for concern: " + concern.getName());
                     Concern concernEntity =concernRepository.findByName(concern.getName()).orElseGet(() -> {
                        System.out.println("Creating concern: " + concern.getName());
                        Concern c = toEntity(concern);
                        System.out.println("About to save: " + c.getTemplates());
                        return concernRepository.save(c);
                    });
                    System.out.println(
                        "ASSOCIATING CONCEPT = " + concept.getName()
                        + " WITH CONCERN = " + concern.getName()
                    );
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
                
                    conceptConcernAssociationRepository.save(association);
                    System.out.println(
                        "ASSOCIATION CREATED = "
                        + conceptEntity.getName()
                        + " -> "
                        + concernEntity.getName()
                    );
                }
        }
                     

                }
                
                
            
             
            }
// helper methods
private Concept toEntity(ConceptDTO dto){
        Concept concept = new Concept();
        concept.setName(dto.getName());
        return concept;

}
private Concern toEntity(ConcernDTO dto){
        Concern concern = new Concern();
        concern.setName(dto.getName());
        List<Template> templateEntities = new ArrayList<>();

for (TemplateDTO dtoTemplate : dto.getTemplates()) {

    Template template = new Template();

    template.setText(dtoTemplate.getText());
    template.setWeight(dtoTemplate.getWeight());
    template.setConcern(concern);
    template.setSpecificity(dtoTemplate.getSpecificity());
    template.setActionability(dtoTemplate.getActionability());
    template.setComplexity(dtoTemplate.getComplexity());
    templateEntities.add(template);
}

concern.setTemplates(templateEntities);


      /*  List<Template> templateTexts = new ArrayList<>();

for (TemplateDTO template : dto.getTemplates()) {
        System.out.println("DTO Templates******"+dto.getTemplates());
    templateTexts.add(template.getText());
    
}

concern.setTemplates(templateTexts); */
        System.out.println("Templates******"+concern.getTemplates());
        return concern;

}

        }
