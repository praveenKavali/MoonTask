package com.MoonTask.Backend.task.dto;


import com.MoonTask.Backend.task.entity.Priority;
import com.MoonTask.Backend.task.entity.Status;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTask {
    @NotNull
    private String name;
    @NotNull
    private Priority priority;
    @NotNull
    private Status status;
    private String description;
}
