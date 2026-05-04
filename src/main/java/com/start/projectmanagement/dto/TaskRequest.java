package com.start.projectmanagement.dto;

import lombok.Data;
import java.util.Date;

@Data
public class TaskRequest {
    private String title;
    private String status;
    private Long projectId;
    private Long assignedToId;
    private Date dueDate;
}