package com.MoonTask.Backend.task.service;

import com.MoonTask.Backend.task.dto.CreateTask;
import com.MoonTask.Backend.task.entity.Priority;
import com.MoonTask.Backend.task.entity.Status;
import com.MoonTask.Backend.task.entity.TaskInfo;
import com.MoonTask.Backend.task.mapper.TaskMapperDTO;
import com.MoonTask.Backend.task.repository.TaskRepository;
import com.MoonTask.Backend.user.entity.UserInfo;
import com.MoonTask.Backend.user.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    TaskRepository repo;
    @Mock
    UserRepo userRepo;
    @Mock
    TaskMapperDTO dto;
    @InjectMocks
    TaskService service;
    TaskInfo task;

    @BeforeEach
    void setUp() {
        task = TaskInfo.builder()
                .name("have to play games at evening.")
                .priority(Priority.LOW)
                .status(Status.DO)
                .description("To day is a holiday so evening I has to play games.").build();
    }

    @Test
    @DisplayName("Test for allTask method")
    void allTask() {
        when(repo.getAllTask()).thenReturn(List.of(task));

        List<TaskInfo> tasks = service.allTasks();
        assertNotNull(tasks);
        assertEquals(1, tasks.size());

        verify(repo).getAllTask();
    }

    @Test
    @DisplayName("Test for getTasksByPriority method")
    void getTasksByPriority() {
        when(repo.findByPriority(any(Priority.class))).thenReturn(List.of(task));

        List<TaskInfo> tasks = service.getTasksByPriority(Priority.LOW);
        assertNotNull(tasks);
        assertEquals(task.getPriority(), tasks.get(0).getPriority());
        verify(repo).findByPriority(any(Priority.class));
    }

    @Test
    @DisplayName("Test for getTasksByStatus method")
    void getTasksByStatus() {
        when(repo.findByStatus(any(Status.class))).thenReturn(List.of(task));

        List<TaskInfo> tasks = service.getTasksByStatus(Status.DO);
        assertNotNull(tasks);
        assertEquals(task.getStatus(), tasks.get(0).getStatus());
        verify(repo).findByStatus(any(Status.class));
    }

    @Test
    @DisplayName("Test for searchTask method")
    void searchTask() {
        when(repo.searchForTask(anyString())).thenReturn(List.of(task));

        List<TaskInfo> tasks = service.searchTask("play");
        assertNotNull(tasks);
        assertEquals(task.getName(), tasks.get(0).getName());
        verify(repo).searchForTask(anyString());
    }

    @Nested
    @DisplayName("Test for addTask method")
    class addTask {
        UserDetails user = UserInfo.builder().email("p@gmail.com").password("00000000").build();
        @Test
        @DisplayName("Success")
        void success() {
            UserInfo user1 = UserInfo.builder().email("p@gmail.com").build();
            TaskInfo taskInfo = TaskInfo.builder().name("study in morning").build();
            CreateTask create = CreateTask.builder().name("study in morning").build();
            when(userRepo.findByEmail(anyString())).thenReturn(Optional.of(user1));
            when(dto.mappedToTask(create)).thenReturn(taskInfo);
            String result = service.addTask(user, create);
            assertEquals("Task has been added.", result);
            verify(dto).mappedToTask(create);
            verify(userRepo).save(any());
        }

        @Test
        @DisplayName("Failure")
        void failure() {
            when(userRepo.findByEmail(anyString())).thenReturn(Optional.empty());
            UsernameNotFoundException ex = assertThrows(UsernameNotFoundException.class,
                    () -> service.addTask(user, CreateTask.builder().build()));
            assertEquals("Please login to save a task.", ex.getMessage());
            verify(dto, times(0)).mappedToTask(any());
            verify(userRepo, times(0)).save(any());
        }
    }

    @DisplayName("Test for updatePriority method")
    @Nested
    class updatePriority {
        @DisplayName("Success")
        @Test
        void success() {
            when(repo.findById(anyInt())).thenReturn(Optional.of(task));
            String result = service.updatePriority(Priority.MEDIUM, 1);
            assertEquals("Priority has been updated from " + task.getPriority() + " to MEDIUM.", result);
            verify(repo).updatePriority(eq(Priority.MEDIUM), anyInt());
        }

        @DisplayName("Failure")
        @Test
        void failure() {
            when(repo.findById(anyInt())).thenReturn(Optional.empty());
            UsernameNotFoundException ex = assertThrows(UsernameNotFoundException.class,
                    () -> service.updatePriority(Priority.MEDIUM, 1));
            assertEquals("no task is present", ex.getMessage());
            verify(repo, times(0)).updatePriority(any(), any());
        }
    }

    @DisplayName("Test for updateStatus method")
    @Nested
    class updateStatus {
        @DisplayName("Success")
        @Test
        void success() {
            when(repo.findById(anyInt())).thenReturn(Optional.of(task));
            String result = service.updateStatus(Status.COMPLETED, 1);
            assertEquals("Status has been updated from " + task.getStatus() + " to COMPLETED.", result);
            verify(repo).updateStatus(eq(Status.COMPLETED), anyInt());
        }

        @DisplayName("Failure")
        @Test
        void failure() {
            when(repo.findById(anyInt())).thenReturn(Optional.empty());
            UsernameNotFoundException ex = assertThrows(UsernameNotFoundException.class,
                    () -> service.updateStatus(Status.COMPLETED, 1));
            assertEquals("no task is present", ex.getMessage());
            verify(repo, times(0)).updateStatus(any(), any());
        }
    }

    @DisplayName("Test for markAsComplete method")
    @Nested
    class markAsComplete {
        @DisplayName("Success")
        @Test
        void success() {
            when(repo.findById(anyInt())).thenReturn(Optional.of(task));
            String result = service.markAsComplete(1);
            assertEquals("Congratulations! on completing task.", result);
            verify(repo).save(any(TaskInfo.class));
        }

        @DisplayName("Failure")
        @Test
        void failure() {
            when(repo.findById(anyInt())).thenReturn(Optional.empty());
            UsernameNotFoundException ex = assertThrows(UsernameNotFoundException.class,
                    () -> service.markAsComplete(1));
            assertEquals("No Task present", ex.getMessage());
        }
    }
}