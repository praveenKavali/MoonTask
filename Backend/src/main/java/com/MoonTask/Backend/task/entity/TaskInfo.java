package com.MoonTask.Backend.task.entity;


import com.MoonTask.Backend.user.entity.UserInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This contains fields for creating task in database.*/
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Task_Information")
public class TaskInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // name should not be null because it is required to create a task. and useful for retrieving task.
    @Column(nullable = false)
    private String name;
    // priority should not be null because it is required to create a task. and useful for retrieving task based on priority.
    @Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority;
    // status should not be null because it is required to create a task. and useful for retrieving task based on status.
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalTime createdTime;
    private LocalDate createdDate;
    private String description;
    private LocalTime completedTime;
    private LocalDate completedDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = true)
    private UserInfo user;

    /**
     * This method is automatically assign time and date for task. It will automatically run before the record
     * is inserted into database.*/
    @PrePersist
    public void prePersist(){
        this.createdTime = LocalTime.now();
        this.createdDate = LocalDate.now();
    }
}
