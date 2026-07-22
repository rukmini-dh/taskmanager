package com.example.taskmanager;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConceptRepository  extends JpaRepository<Concept,Integer>{
    Optional<Concept> findByName(String name);
}
