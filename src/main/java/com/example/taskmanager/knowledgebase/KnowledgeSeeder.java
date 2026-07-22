
package com.example.taskmanager.knowledgebase;

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

KnowledgeDTO knowledge =
        mapper.readValue(
                resource.getInputStream(),
                KnowledgeDTO.class);
             
 

     
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
                     Concern concernEntity =concernRepository.findByName(concern.getName()).orElseGet(() -> {
                        Concern c = toEntity(concern);
                        return concernRepository.save(c);
                    });
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
        return concern;

}

        }
