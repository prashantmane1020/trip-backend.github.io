package com.aitrip.backend.controller;

import com.aitrip.backend.model.User;
import com.aitrip.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173") // Allow frontend access
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // 🟢 LOGIN ENDPOINT
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginDetails) {
        Optional<User> user = userRepository.findByEmail(loginDetails.getEmail());

        if (user.isPresent()) {
            if (user.get().getPassword().equals(loginDetails.getPassword())) {
                return ResponseEntity.ok(user.get()); // Login Success: Return User Data
            } else {
                return ResponseEntity.status(401).body("Invalid Password");
            }
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    // 🟢 SIGNUP ENDPOINT (For the "Sign Up" button)
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User newUser) {
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        userRepository.save(newUser);
        return ResponseEntity.ok("User registered successfully");
    }
}