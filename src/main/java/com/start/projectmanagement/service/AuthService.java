package com.start.projectmanagement.service;

import com.start.projectmanagement.dto.*;
import com.start.projectmanagement.model.User;
import com.start.projectmanagement.repository.UserRepository;
import com.start.projectmanagement.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired private UserRepository repo;
    @Autowired private PasswordEncoder encoder;
    @Autowired private JwtUtil jwt;

    public String register(RegisterRequest req) {
        if (repo.findByEmail(req.getEmail()).isPresent())
            return "User already exists";

        String role = req.getRole();
        if (role == null || role.isBlank()) {
            role = "ROLE_EMPLOYEE";
        } else {
            role = "ROLE_" + role.toUpperCase().trim(); // "ADMIN" → "ROLE_ADMIN"
        }

        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setPassword(encoder.encode(req.getPassword()));
        u.setRole(role);
        repo.save(u);
        return "Registered successfully as " + role;
    }

    public String login(LoginRequest req) {
        User user = repo.findByEmail(req.getEmail()).orElse(null);
        if (user == null) return "User not found";
        if (!encoder.matches(req.getPassword(), user.getPassword())) return "Wrong password";
        return jwt.generateToken(user.getEmail(), user.getRole());
    }
}