package com.example.taskmanager.user;

import java.time.LocalDate;

public class UserDTO {

    private Long id;

    private String registrationNumber;

    private String firstName;

    private String lastName;
    private String userName;

    private Role role;

    private LocalDate createdAt;

    private boolean enabled;

    // Default constructor
    public UserDTO() {
    }

    // Constructor
    public UserDTO(String registrationNumber,
                   String firstName,
                   String lastName,
                   String userName,
                   Role role,
                   LocalDate createdAt,
                   boolean enabled) {

        this.registrationNumber = registrationNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName=userName;
        this.role = role;
        this.createdAt = createdAt;
        this.enabled = enabled;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
    public void setUserName(String userName){this.userName=userName;}
    public String getUserName(){return userName;};

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}