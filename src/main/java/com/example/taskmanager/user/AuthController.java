package com.example.taskmanager.user;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import com.example.taskmanager.security.SecurityUtil;
import com.example.taskmanager.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;

    public AuthController(UserService userService,UserRepository userRepository){
        this.userService = userService;
        this.userRepository=userRepository;
    }
    
    @GetMapping("/{regNo}")
    public UserDTO getUserByRegistrationNumber(@PathVariable String regNo) {
        return userService.getUserByRegistrationNumber(regNo);
    }
@GetMapping("/me")

public AuthResponseDTO getCurrentUser() 
     {
        
           return userService.getCurrentUser();

      }
   
    // Get all users
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    // Get user by userName
    @GetMapping("/{userName}")
    public UserDTO getUserByUserName(@PathVariable String userName) {
        return userService.getUserByUserName(userName);
    }

   /*  // Create a new user
    @PostMapping
    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
        return userServiceImpl.createUser(userDTO);
    } */
    
     @PostMapping("/signin")
     public AuthResponseDTO login(@RequestBody LoginDTO dto,HttpServletRequest request) 
     {
        
           return userService.login(dto, request);

      }
      @GetMapping("/forgotPassword/{userName}")
      public ChangePasswordDTO forgotPassword(@PathVariable String userName){
        return userService.forgotPassword(userName);
      }

      @PutMapping("/changePassword/{userName}")
      public ChangePasswordDTO changePassword(
              @RequestBody ChangePasswordDTO dto,
              @PathVariable String userName) {
      
          return userService.changePassword(
              dto,
              userName
          );
      }
    // Create Register new user
    @PostMapping("/register")
    public UserDTO registerUser( @RequestBody RegistrationDTO registrationDTO) {
        return userService.registerUser(registrationDTO);
    }
    // Update an existing task
    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @Valid @RequestBody  UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }
    // Delete a task
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    // Get users by enabled status
    @GetMapping("/status/{enabled}")
    public List<UserDTO> getUsersByStatus(@PathVariable boolean enabled) {
        return userService.getByEnabled(enabled);
    }
    @PostMapping("/logout")
public ResponseEntity<String> logout(
        HttpServletRequest request,
        HttpServletResponse response) {

    Authentication auth =
            SecurityContextHolder
                    .getContext()
                    .getAuthentication();

    if (auth != null) {

        new SecurityContextLogoutHandler()
                .logout(request, response, auth);
    }

    return ResponseEntity.ok("Logged out successfully");
}
    
}