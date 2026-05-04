package com.start.projectmanagement.service;

import com.start.projectmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository repo;

    public List<Map<String, Object>> getAll() {
        return repo.findAll().stream().map(u -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id",    u.getId());
            map.put("name",  u.getName());
            map.put("email", u.getEmail());
            map.put("role",  u.getRole());
            return map;
        }).collect(Collectors.toList());
    }
}