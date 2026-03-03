package com.MoonTask.Backend.task.controller;

import com.MoonTask.Backend.task.dto.CreateTask;
import com.MoonTask.Backend.task.entity.Priority;
import com.MoonTask.Backend.task.entity.Status;
import com.MoonTask.Backend.task.entity.TaskInfo;
import com.MoonTask.Backend.task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Controller class for managing tasks
 * provide points like all, priority, status, search, create, completed etc.*/
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService service;

    /**
     * Retries all the tasks from dataset
     * @return ResponseEntity contains the list of task.*/
    @GetMapping("/all")
    public ResponseEntity<List<TaskInfo>> getAllTask(){
        return ResponseEntity.ok(service.allTasks());
    }

    /**
     * Get the list of account filtered by priority.
     * Uses Get mapping
     * @param priority used for filtering data.
     * @return a list of filtered task.*/
    @GetMapping("/priority")
    public ResponseEntity<List<TaskInfo>> filterByPriority(@RequestParam("priority") Priority priority){
        return ResponseEntity.ok(service.getTasksByPriority(priority));
    }

    /**
     * Get the list of account filtered by status.
     * Uses Get mapping
     * @param status used for filtering data.
     * @return a list of filtered task.*/
    @GetMapping("/status")
    public ResponseEntity<List<TaskInfo>> filterByStatus(@RequestParam("status") Status status){
        return ResponseEntity.ok(service.getTasksByStatus(status));
    }

    /**
     * Get the list of account based on the search words.
     * Uses Get mapping
     * @param word used for filtering data.
     * @return a list of filtered task.*/
    @GetMapping("/search")
    public ResponseEntity<List<TaskInfo>> searchForTask(@RequestParam("word") String word){
        return ResponseEntity.ok(service.searchTask(word));
    }

    /**
     * used for creating the task and assign to the user.
     * Uses POST mapping
     * @param userDetails spring security provides the user details of the currently logged in user through {@link AuthenticationPrincipal}.
     * @param task contains the information about the task from the frontend.
     * @return a message if task is successfully created.*/
    @PostMapping("/create")
    public ResponseEntity<String> createTask(@AuthenticationPrincipal UserDetails userDetails,
                                                     @Valid @RequestBody CreateTask task){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addTask(userDetails, task));
    }

    /**
     * used for changing the priority of the task.
     * Uses Patch mapping
     * @param id used for identifying the task which we want to update.
     * @param priority used for changing priority.
     * @return a message if priority changed successfully.*/
    @PatchMapping("/{id}/priority")
    public ResponseEntity<String> updatePriority(@PathVariable("id") Integer id,
                                                 @RequestParam("priority") Priority priority){
        return ResponseEntity.ok(service.updatePriority(priority, id));
    }

    /**
     * used for changing the priority of the task.
     * Uses Patch mapping
     * @param id used for identifying the task which we want to update.
     * @param status used for changing status.
     * @return a message if status changed successfully.*/
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable("id") Integer id,
                                               @RequestParam("status") Status status){
        return ResponseEntity.ok(service.updateStatus(status, id));
    }

    /**
     * used for mark task as completed one
     * @param id for identifying the task.
     * @return a message if it is completed successfully.*/
    @PatchMapping("/complete/{id}")
    public ResponseEntity<String> taskCompleted(@PathVariable Integer id){
        return ResponseEntity.ok(service.markAsComplete(id));
    }
}

