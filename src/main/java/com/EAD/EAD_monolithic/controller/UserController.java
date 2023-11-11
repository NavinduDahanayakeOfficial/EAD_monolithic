package com.EAD.EAD_monolithic.controller;

import com.EAD.EAD_monolithic.dto.ProductDTO;
import com.EAD.EAD_monolithic.dto.UserDTO;
import com.EAD.EAD_monolithic.dto.UserUpdateDTO;
import com.EAD.EAD_monolithic.entity.Delivery;
import com.EAD.EAD_monolithic.entity.User;
import com.EAD.EAD_monolithic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/getUserById/{userId}")
    public User getUserById(@PathVariable int userId){
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
