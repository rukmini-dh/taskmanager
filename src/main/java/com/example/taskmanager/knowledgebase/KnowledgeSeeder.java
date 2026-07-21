
package com.example.taskmanager.knowledgebase;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class KnowledgeSeeder
        implements CommandLineRunner {

               
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
             

     

        // Save Concepts

        // Save Concerns

        // Save Associations
        for (ConceptDTO concept : knowledge.getConcepts()) {

                System.out.println("--------------------------------");
            
                System.out.println("Concept : " + concept.getName());
            
                System.out.println("Description : " + concept.getDescription());
            
                for (ConcernDTO concern : concept.getConcerns()) {
            
                    System.out.println("    Concern : " + concern.getName());
            
                    System.out.println("        Suggested : " + concern.getTimesSuggested());
            
                    System.out.println("        Accepted : " + concern.getTimesAccepted());
            
                    System.out.println("        Rejected : " + concern.getTimesRejected());
            
                }
            }
    }
}