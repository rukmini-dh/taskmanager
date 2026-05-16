package com.example.taskmanager.user;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements UserService{
     private final UserRepository userRepository;

    public  UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public List<UserDTO> getAllUsers() {
        return  
        (userRepository.findAll()).stream().map(this::convertToDTO).toList();
    }

    // Delete User
    public void deleteUser(Long id) {
              userRepository.deleteById(id);
    }
    public void deleteUser(String registraionNumber) {
              userRepository.deleteByRegistrationNumber(registraionNumber);
    }
    public List<UserDTO> getByCreatedAt(LocalDate createdAt){
        
            return (userRepository.findByCreatedAt(createdAt)).stream()
            .map(this::convertToDTO).toList();
        }

    public UserDTO getUserById(Long id) {
        User  user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        return convertToDTO(user);
    }
    public UserDTO getUserByUserName(String userName) {
        User  user = userRepository.findByUserName(userName).orElseThrow(() -> new UserNotFoundException("User not found with username: " + userName));

        return convertToDTO(user);
    }
    public UserDTO getUserByRegistrationNumber(String regno ) {
        User  user = userRepository.findByRegistrationNumber(regno).orElseThrow(() -> new UserNotFoundException("User not found with regNo. " + regno));

        return convertToDTO(user);
    }
    // Find users  by enabled/disabled
    public List<UserDTO> getByEnabled(boolean enabled) {
        return (userRepository.findByEnabled(enabled)).stream()
        .map(this::convertToDTO)
        .toList();
    }

    // get users by role
    public List<UserDTO> getByRole(Role role) {
        return userRepository.findByRole(role).stream()
        .map(this::convertToDTO)
        .toList();
    }
   
     // Update user
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        if (userDTO.getFirstName() != null) user.setFirstName(userDTO.getFirstName());
        user.setEnabled(userDTO.isEnabled());
        user.setRole(userDTO.getRole());
        user.setLastName(userDTO.getLastName());
        user.setCreatedAt(userDTO.getCreatedAt());
        user.setUserName(userDTO.getUserName());
        user.setRegistrationNumber(userDTO.getRegistrationNumber());
        return convertToDTO(userRepository.save(user));
    }
              
    // Create new user
    /* public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setEnabled(userDTO.isEnabled());
        user.setRole(userDTO.getRole());
        user.setLastName(userDTO.getLastName());
        user.setFirstName(userDTO.getFirstName());
        user.setCreatedAt(userDTO.getCreatedAt());
        user.setRegistrationNumber(userDTO.getRegistrationNumber());
        return convertToDTO(userRepository.save(user));
    }               */

   
    
    // Register new user
    public UserDTO registerUser(RegistrationDTO regDTO) {

       User user = new User();
        
    
        user.setFirstName(regDTO.getFirstName());
    
        user.setLastName(regDTO.getLastName());
        user.setUserName(regDTO.getUserName());
    
        user.setRegistrationNumber(
            "REG-" + System.currentTimeMillis()
        );
    
        user.setPassword(regDTO.getPassword());
       
        System.out.println(user.getPassword());
        user.setRole(Role.GUEST);
    
        user.setCreatedAt(LocalDate.now());
    
        user.setEnabled(true);
        
        return convertToDTO(
            userRepository.save(user)
        );
    }
   
    // 🔹 Helper method
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUserName(user.getUserName());
        dto.setEnabled(user.isEnabled());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setRegistrationNumber(user.getRegistrationNumber());
        return dto;
    }

    public AuthResponseDTO login(LoginDTO dto) {

        Optional<User> optionalUser =
    userRepository.findByUserName(dto.getUserName());

if(optionalUser.isEmpty()){

    return new AuthResponseDTO(
        false,
        "User not found"
    );
}

User user = optionalUser.get();
    
        if (!user.isEnabled()) {
    
            return new AuthResponseDTO(
                false,
                "Account disabled"
            );
        }
    
        if (!user.getPassword().equals(dto.getPassword())) {
    
            return new AuthResponseDTO(
                false,
                "Invalid credentials"
            );
        }
    
        return new AuthResponseDTO(
            true,
            "Login successful"
        );
    }
    
}

    

