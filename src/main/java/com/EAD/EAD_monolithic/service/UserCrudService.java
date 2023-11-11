package com.EAD.EAD_monolithic.service;

import com.EAD.EAD_monolithic.Exception.UserNotFoundException;
import com.EAD.EAD_monolithic.dto.UserResponseDTO;
import com.EAD.EAD_monolithic.dto.UserUpdateDTO;
import com.EAD.EAD_monolithic.entity.User;
import com.EAD.EAD_monolithic.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserCrudService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;


    public List<UserResponseDTO> getAllUsers() {
        List<User> userList = userRepo.findAll();
        return modelMapper.map(userList, new TypeToken<List<UserResponseDTO>>(){}.getType());
    }


    public UserResponseDTO getUserById(int userId) {
        User user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found with id " + userId);
        }
        return modelMapper.map(user, UserResponseDTO.class);
    }

    public User getUserByIdAllDetail(int userId) {
        User user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found with id " + userId);
        }
        return user;
    }

//    public UserUpdateDTO updateUser(UserUpdateDTO userDTO){
//        userRepo.save(modelMapper.map(userDTO, User.class));
//        return userDTO;
//    }

    public UserUpdateDTO updateUser(UserUpdateDTO userDTO){
        // Retrieve the existing user entity from the database
        User existingUser = userRepo.findById(userDTO.getUserId()).orElse(null);

        // Check if the user exists
        if (existingUser == null) {
            throw new UserNotFoundException("User not found with id " + userDTO.getUserId());
        }

        // Update only the 'role' field
        existingUser.setRole(userDTO.getRole());

        // Save the updated user entity
        userRepo.save(existingUser);

        // Return the original userDTO
        return userDTO;
    }

    public String deleteUser(int userId){
        User user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found with id " + userId);
        }
        userRepo.deleteById(userId);
        return "User deleted with id " + userId;
    }

    // Add other CRUD operations as needed
}
