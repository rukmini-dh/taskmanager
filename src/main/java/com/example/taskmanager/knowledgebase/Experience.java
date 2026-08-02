package com.example.taskmanager.knowledgebase;

import java.time.LocalDateTime;

import com.example.taskmanager.Concept;
import com.example.taskmanager.Concern;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Experience {

    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskTitle;

    @ManyToOne
    private Concept concept;

    @ManyToOne
    private Concern concern;

    @Enumerated(EnumType.STRING)
    private FeedbackType feedback;

    private LocalDateTime createdAt;

    public Experience() {
    }
}
