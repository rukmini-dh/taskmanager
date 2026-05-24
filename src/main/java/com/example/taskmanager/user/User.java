package com.example.taskmanager.user;
import  com.example.taskmanager.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message="Registration number cannot be empty")
    @Column(unique = true)
    private String registrationNumber;
    @Column(unique = true)
    private String userName;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Role role;//Guest,Admin,Supervisor
    public LocalDate createdAt;
    private String password ;
    private boolean enabled;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Task> tasks;
    
    //default constructor
    public User() {}
    // constructor with fields
    public User(String registrationNumber,String password,String userName,String firstName,String lastName,Role role,LocalDate createdAt,boolean enabled){
        this.registrationNumber= registrationNumber;
        this.firstName=firstName;
        this.lastName=lastName;
        this.userName=userName;
        this.role=role;
        this.createdAt=createdAt;
        this.enabled=enabled;
        this.password=password;
    }
    // getters and setters
    public void setId(Long id){this.id=id;}
    public Long getId() {return id;}
    public void setUserName(String userName){this.userName=userName;}
    public String getUserName(){return userName;};
    public void setPassword(String password){this.password=password;}
    public String getPassword(){return password;}
    public String getRegistrationNumber(){ return registrationNumber;}
    public void setRegistrationNumber(String registrationNumber){this.registrationNumber=registrationNumber;}
    public void setFirstName(String firstName){this.firstName=firstName;}
    public String getFirstName(){return firstName;}
    public void setLastName(String lastName){this.lastName=lastName;}
    public String getLastName(){return lastName;}
    public Role getRole(){return role;}
    public void setRole(Role role){this.role=role;}
    public LocalDate getCreatedAt(){return createdAt;}
    public void setCreatedAt(LocalDate createdAt){this.createdAt=createdAt;}
    public void setEnabled( boolean enabled){this.enabled=enabled;}
    public boolean isEnabled(){return enabled;}
    }
