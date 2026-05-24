package com.example.taskmanager.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(
            "CURRENT USER = " +
            auth.getName()
        );
        if (auth == null || !auth.isAuthenticated()) {
                  return null;
        }
    
        return auth.getName();
    }
}