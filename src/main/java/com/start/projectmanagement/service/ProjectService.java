package com.start.projectmanagement.service;

import com.start.projectmanagement.model.Project;
import com.start.projectmanagement.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectService {

    @Autowired ProjectRepository repo;

    public Project create(Project p) { return repo.save(p); }

    public List<Project> getAll() { return repo.findAll(); }

    public Project getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public Project update(Long id, Project updated) {
        Project p = getById(id);
        p.setName(updated.getName());
        p.setDescription(updated.getDescription());
        return repo.save(p);
    }

    public String delete(Long id) {
        repo.deleteById(id);
        return "Project deleted";
    }
}