package com.example.taskmanager.user;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);
    
    Optional<User> findByRegistrationNumber(String registrationNumber);
    Optional<User> findById(Long id);

    void deleteByRegistrationNumber(String regno);

    List<User> findByEnabled(boolean enabled);
    
    List<User> findByCreatedAt(LocalDate createdAt);

    List<User> findByRole(Role role);
    
}