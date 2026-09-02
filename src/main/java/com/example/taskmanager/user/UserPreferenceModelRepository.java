package com.example.taskmanager.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserPreferenceModelRepository extends JpaRepository<UserPreferenceModel,Long> {
   Optional <UserPreferenceModel> findByUser(User user);
    
}
