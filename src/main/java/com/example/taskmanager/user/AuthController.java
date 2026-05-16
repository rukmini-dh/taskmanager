package com.example.taskmanager.user;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/{regNo}")
    public UserDTO getUserByRegistrationNumber(@PathVariable String regNo) {
        return userService.getUserByRegistrationNumber(regNo);
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
     public AuthResponseDTO login(@RequestBody LoginDTO dto)
     {
        
            return userService.login(dto);
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
    
}