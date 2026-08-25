package com.example.taskmanager.knowledgebase;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmanager.SubTask;

public interface ExperienceRepository  extends JpaRepository <Experience,Integer> {

    boolean existsBySubTaskId(Long subTaskId);

    long countBySubTaskTemplateId(Long templateId);
    Optional<Experience>  findTopByOrderByIdDesc();

} 
