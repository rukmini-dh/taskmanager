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

    private double specificityPreference;
    private double actionabilityPreference;
    private double complexityPreference;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    public UserPreferenceModel(){}
    
    // getters and setters
    public void setId(Long id){this.id=id;}
    public long getId(){return id;}
    public void setSpecificityPreference(double specificityPreference){this.specificityPreference=specificityPreference;}
    public double getSpecificityPreference(){return specificityPreference;}
    public void setActionabilityPreference(double actionabilityPreference){this.actionabilityPreference=actionabilityPreference;}
    public double getActionabilityPreference(){return  actionabilityPreference;}
    public void setComplexityPreference(double complexityPreference){this.complexityPreference=complexityPreference;}
    public double getComplexityPreference(){return complexityPreference;}
    public void setUser(User user){this.user=user;}
    public User getUser(){return user;}

}

