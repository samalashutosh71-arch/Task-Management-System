package com.ashu.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashu.DTO.AuthRequest;
import com.ashu.entity.User;
import com.ashu.security.JwtUtil;
import com.ashu.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000") 
public class AuthController {

   @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AuthRequest request) 
    {

        String email = request.getUsername();
        String password = request.getPassword();

        //validate user from db using mail from request
        User user = userService.getUserByEmail(email);

        // check pass
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid Credentials");
        }

        // get role from 
        String role = user.getRole();

        // generate jwt token
        String token = jwtUtil.generateToken(String.valueOf(user.getId()), role); //gen token using getid so auth.getName() return user.getId();
        
        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return response;
    }
}
