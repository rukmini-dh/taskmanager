package com.example.taskmanager.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.taskmanager.user.User;
import com.example.taskmanager.user.UserRepository;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(
            UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(
            String username)
            throws UsernameNotFoundException {

        User user = userRepository
                .findByUserName(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );

        return new CustomUserDetails(user);
    }
}