package com.start.projectmanagement.controller;

import com.start.projectmanagement.model.Project;
import com.start.projectmanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired ProjectService service;

    @PostMapping
    public Project create(@RequestBody Project p) { return service.create(p); }

    @GetMapping
    public List<Project> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public Project getById(@PathVariable Long id) { return service.getById(id); }

    @PutMapping("/{id}")
    public Project update(@PathVariable Long id, @RequestBody Project p) {
        return service.update(id, p);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) { return service.delete(id); }
}