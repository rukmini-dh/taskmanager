package com.example.taskmanager;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConceptConcernAssociationRepository extends JpaRepository<ConceptConcernAssociation,Long>{
    Optional<ConceptConcernAssociation> findByConceptAndConcern(
        Concept concept,
        Concern concern
    );
    
} 