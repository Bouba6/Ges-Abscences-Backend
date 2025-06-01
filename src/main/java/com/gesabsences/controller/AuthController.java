package com.gesabsences.controller;

import com.gesabsences.model.User;
import com.gesabsences.repository.UserRepository;
import com.gesabsences.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    // Endpoint to create a super admin (for initial setup)
    @PostMapping("/create-superadmin")
    public String createSuperAdmin(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "Super admin with this username already exists.";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("SUPER_ADMIN");
        userRepository.save(user);
        return "Super admin created successfully.";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User requestUser) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestUser.getUsername(),
                        requestUser.getPassword()
                )
        );
        UserDetails userDetails = userRepository.findByUsername(requestUser.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found")); // Should not happen after authentication

        String jwtToken = jwtService.generateToken(userDetails);
        Map<String, String> response = new HashMap<>();
        response.put("token", jwtToken);
        return response;
    }
} 