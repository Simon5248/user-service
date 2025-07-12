package com.example.userapp.controller;


// ===================================================================================
// FILE: src/main/java/com/example/taskapp/controller/UserController.java
// ===================================================================================

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.userapp.dto.UserDetailsDTO;
import com.example.userapp.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired private UserService userService;

    @GetMapping("/details/{email}")
    public UserDetailsDTO getUserDetailsByEmail(@PathVariable String email) {
        return userService.getUserDetailsByEmail(email);
    }
}