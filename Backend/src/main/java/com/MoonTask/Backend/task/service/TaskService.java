package com.MoonTask.Backend.task.service;

import com.MoonTask.Backend.task.dto.CreateTask;
import com.MoonTask.Backend.task.entity.Priority;
import com.MoonTask.Backend.task.entity.Status;
import com.MoonTask.Backend.task.entity.TaskInfo;
import com.MoonTask.Backend.task.mapper.TaskMapperDTO;
import com.MoonTask.Backend.task.repository.TaskRepository;
import com.MoonTask.Backend.user.entity.UserInfo;
import com.MoonTask.Backend.user.repository.UserRepo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Service class contains the business logic related to {@link TaskInfo}*/
@Service
@CacheConfig(cacheNames = "tasks")
public class TaskService {

    private final TaskRepository repository;
    private final TaskMapperDTO mapper;
    private final UserRepo repo;

    public TaskService(TaskRepository repository,
                       TaskMapperDTO mapper,
                       UserRepo repo){
        this.repository = repository;
        this.mapper = mapper;
        this.repo = repo;
    }

    /**
     * This method is useful to get all the task from database.
     * @return a list of {@link TaskInfo} task.*/
    public List<TaskInfo> allTasks(String email){
        return repository.getAllTask(email);
    }

    /**
     * This method is useful to get all tasks based on {@link Priority}
     * @param priority useful for filtering data
     * @param email for finding particular user
     * @return a list of {@link TaskInfo} task.*/
    @Cacheable(key = "#email + '_' + #priority")
    public List<TaskInfo> getTasksByPriority(String email, Priority priority){
        return repository.findByUserEmailAndPriority(email, priority);
    }

    /**
     * This method is useful to get all tasks based on {@link Status}
     * @param email used to find user details
     * @param status useful for filtering data
     * @return a list of {@link TaskInfo} task.*/
    @Cacheable(key = "#email + '_' + #status")
    public List<TaskInfo> getTasksByStatus(String email, Status status){
        return repository.findByUserEmailAndStatus(email, status);
    }

    /**
     * This method is useful for searching for specific task with few letters(you don't need to remember the entire task name)
     * @param word contains the letters fot searching through the database.
     * @return a list of all task that matches the entered word.*/
    @Cacheable(key = "#email + '_' + #word")
    public List<TaskInfo> searchTask(String email, String word){
        return repository.searchForTask(email, word);
    }

    /**
     * This method is used to store a task to the database.
     * @param userDetails {@link UserDetails} when login security save the details. which will be helpful for mapping this task to specific user.
     * @param task container the user enter details about task.
     * @return a string message if task has been added successfully.
     * @throws UsernameNotFoundException if the user details are not present in database.*/
    @CacheEvict(value = "tasks", allEntries = true)
    public String addTask(UserDetails userDetails, CreateTask task){
        UserInfo user = repo.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("Please login to save a task."));
        TaskInfo taskInfo = mapper.mappedToTask(task);
        user.addTask(taskInfo);
        repo.save(user);
        return "Task has been added.";
    }

    /**
     * This method used to update the priority of the task
     * @param priority new priority
     * @param id which task need to be updated determined by id
     * @return a message if task is updated successfully
     * @throws UsernameNotFoundException if no task is presented with id.*/
    @CachePut(key = "#id")
    public String updatePriority(Priority priority, Integer id){
        TaskInfo task = repository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("no task is present")
        );
        repository.updatePriority(priority, id);
        return "Priority has been updated from " + task.getPriority() + " to " + priority + ".";
    }

    /**
     * This method is used for updating the status of the task.
     * @param status new status
     * @param id which task need to be updated determined by id
     * @return a message if task is successfully updated.*/
    @CachePut(key = "#id")
    public String updateStatus(Status status, Integer id){
        TaskInfo task = repository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("no task is present")
        );
        repository.updateStatus(status, id);
        return "Status has been updated from " + task.getStatus() + " to " + status + ".";
    }

    /**
     * This method is useful for updating the completed time and date.
     * @param id for finding task to update time and task
     * @return {@link TaskInfo} object
     * @throws UsernameNotFoundException if no task present with given id.*/
    @CachePut(key = "#id")
    public TaskInfo markAsComplete(Integer id){
        TaskInfo task = repository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("No Task present"));
        task.setCompletedTime(LocalTime.now());
        task.setCompletedDate(LocalDate.now());
        task.setStatus(Status.COMPLETED);
        return repository.save(task);
    }

}
