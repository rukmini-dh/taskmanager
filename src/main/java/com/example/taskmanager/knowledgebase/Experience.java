package com.example.taskmanager.knowledgebase;

import java.time.LocalDateTime;

import com.example.taskmanager.Concept;
import com.example.taskmanager.Concern;
import com.example.taskmanager.SubTask;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"sub_task_id"}
    )
)
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "sub_task_id", nullable = false,unique = true)
    private SubTask subTask;

    private LocalDateTime createdAt;

    public Experience() {
    }

    // getters/setters
    public void setId(long id){this.id=id;}
    public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}
    public void setSubTask(SubTask subTask){this.subTask=subTask;}
    public Long getId(){return id;}
    public SubTask getSubTask(){return subTask;}
    public LocalDateTime getCreatedAt(){return createdAt;}

    }
