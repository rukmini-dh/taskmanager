package com.example.taskmanager;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface  ConcernRepository extends  JpaRepository <Concern,Integer>{
    Optional<Concern> findByName(String name);
}
