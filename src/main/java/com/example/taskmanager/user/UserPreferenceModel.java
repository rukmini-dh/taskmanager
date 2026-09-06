package com.example.taskmanager.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;


@Entity
public class UserPreferenceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double specificityWeight   = 0.50;
    private double actionabilityWeight = 0.50;
    private double complexityWeight    = 0.50;

  

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    public UserPreferenceModel(){}
    
    // getters and setters
    public void setId(Long id){this.id=id;}
    public long getId(){return id;}
    public void setSpecificityWeight(double specificityWeight){this.specificityWeight=specificityWeight;}
    public double getSpecificityWeight(){return specificityWeight;}
    public void setActionabilityWeight(double actionabilityWeight){this.actionabilityWeight=actionabilityWeight;}
    public double getActionabilityWeight(){return  actionabilityWeight;}
    public void setComplexityWeight(double complexityWeight){this.complexityWeight=complexityWeight;}
    public double getComplexityWeight(){return complexityWeight;}
    public void setUser(User user){this.user=user;}
    public User getUser(){return user;}

}

