package com.EAD.EAD_monolithic.controller;



import com.EAD.EAD_monolithic.dto.UserResponseDTO;
import com.EAD.EAD_monolithic.dto.UserUpdateDTO;
import com.EAD.EAD_monolithic.entity.User;
import com.EAD.EAD_monolithic.service.UserCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserCrudService userService;

    @GetMapping("/getUsers")
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getUserById/{userId}")
    public UserResponseDTO getUserById(@PathVariable int userId){
        return userService.getUserById(userId);
    }

    @PutMapping("updateUser")
    public UserUpdateDTO updateUser(@RequestBody UserUpdateDTO userDTO){
        return userService.updateUser(userDTO);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable int userId){
        return userService.deleteUser(userId);
    }

    // Add other CRUD operations as needed
}
