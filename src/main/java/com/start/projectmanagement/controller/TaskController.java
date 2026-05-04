package com.start.projectmanagement.controller;

import com.start.projectmanagement.model.Task;
import com.start.projectmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired TaskService service;

    @PostMapping
    public Task create(@RequestBody Task t) { return service.create(t); }

    @GetMapping
    public List<Task> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public Task getById(@PathVariable Long id) { return service.getById(id); }

    // Employee updates their own task status
    @PatchMapping("/{id}/status")
    public Task updateStatus(@PathVariable Long id, @RequestParam String status) {
        return service.updateStatus(id, status);
    }

    // Admin full update
    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task t) {
        return service.update(id, t);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) { return service.delete(id); }
}