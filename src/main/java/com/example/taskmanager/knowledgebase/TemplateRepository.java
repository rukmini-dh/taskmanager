package com.example.taskmanager.knowledgebase;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
public interface  TemplateRepository  extends JpaRepository<Template,Integer> {
    Optional<Template> findById(Long id);
}
    

