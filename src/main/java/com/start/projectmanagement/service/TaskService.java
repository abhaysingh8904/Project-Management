package com.start.projectmanagement.service;

import com.start.projectmanagement.model.Task;
import com.start.projectmanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    @Autowired TaskRepository repo;

    public Task create(Task t) { return repo.save(t); }

    public List<Task> getAll() { return repo.findAll(); }

    public Task getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    // Employee can update their task status
    public Task updateStatus(Long id, String status) {
        Task t = getById(id);
        t.setStatus(status);
        return repo.save(t);
    }

    // Admin can fully update a task
    public Task update(Long id, Task updated) {
        Task t = getById(id);
        t.setTitle(updated.getTitle());
        t.setDescription(updated.getDescription());
        t.setStatus(updated.getStatus());
        t.setAssignedTo(updated.getAssignedTo());
        t.setProject(updated.getProject());
        t.setDueDate(updated.getDueDate());
        return repo.save(t);
    }

    public String delete(Long id) {
        repo.deleteById(id);
        return "Task deleted";
    }
}