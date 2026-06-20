package com.example.taskmanager.user;
import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
public interface UserService {
        List<UserDTO> getAllUsers();
         UserDTO getUserById(Long id);
        UserDTO getUserByRegistrationNumber(String regno);
        UserDTO getUserByUserName(String username);
        List<UserDTO> getByRole(Role role);
        List<UserDTO> getByEnabled(boolean enabled);
        List<UserDTO> getByCreatedAt(LocalDate date);
        UserDTO updateUser(Long id, UserDTO userDTO);
        UserDTO registerUser(RegistrationDTO regDTO);
        void deleteUser(Long id);
        AuthResponseDTO login(LoginDTO dto,HttpServletRequest request);
        AuthResponseDTO getCurrentUser();
        ChangePasswordDTO changePassword(ChangePasswordDTO dto,String username);
 }
 