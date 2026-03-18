package com.MoonTask.Backend.task.controller;

import com.MoonTask.Backend.security.jwt.JwtFilter;
import com.MoonTask.Backend.security.service.CustomUserDetailsService;
import com.MoonTask.Backend.security.service.JwtService;
import com.MoonTask.Backend.task.dto.CreateTask;
import com.MoonTask.Backend.task.entity.Priority;
import com.MoonTask.Backend.task.entity.Status;
import com.MoonTask.Backend.task.entity.TaskInfo;
import com.MoonTask.Backend.task.service.TaskService;
import com.MoonTask.Backend.user.entity.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    TaskService service;
    @MockitoBean
    JwtService jwtService;
    @MockitoBean
    CustomUserDetailsService userDetailsService;
    @MockitoBean
    JwtFilter filter;

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
    @DisplayName("Test for getAllTask method")
    @WithMockUser(username = "test@gmail.com")
    void getAllTask() throws Exception {
        when(service.allTasks("test@gmail.com")).thenReturn(List.of(task));
        mockMvc.perform(get("/task/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("have to play games at evening."));
        verify(service).allTasks("test@gmail.com");
    }

    @Test
    @DisplayName("Test for filterByPriority method")
    @WithMockUser(username = "test@gmail.com")
    void filterByPriority() throws Exception {
        when(service.getTasksByPriority(eq("test@gmail.com"), eq(Priority.LOW))).thenReturn(List.of(task));
        mockMvc.perform(get("/task/priority")
                        .param("priority", "LOW"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].priority").value("LOW"));
        verify(service).getTasksByPriority(eq("test@gmail.com"),eq(Priority.LOW));
    }

    @Test
    @DisplayName("Test for filterByStatus method")
    @WithMockUser(username = "test@gmail.com")
    void filterByStatus() throws Exception {
        when(service.getTasksByStatus(eq("test@gmail.com"), eq(Status.DO))).thenReturn(List.of(task));
        mockMvc.perform(get("/task/status")
                        .param("status", "DO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].status").value("DO"));
        verify(service).getTasksByStatus(eq("test@gmail.com"), eq(Status.DO));
    }

    @Test
    @DisplayName("Test for searchForTask method")
    @WithMockUser(username = "test@gmail.com", value = "test@gmail.com")
    void searchForTask() throws Exception {
        when(service.searchTask(eq("test@gmail.com"), anyString())).thenReturn(List.of(task));
        mockMvc.perform(get("/task/search")
                        .param("word", "play"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("have to play games at evening."));
        verify(service).searchTask(eq("test@gmail.com"), anyString());
    }

    @Nested
    @DisplayName("Test for createTask method")
    class createTask {

        UserDetails user = UserInfo.builder().email("p@gmail.com").build();
        @Test
        @DisplayName("Success")
        @WithMockUser(username = "testUser", roles = {"USER"})
        void success() throws Exception {
            CreateTask create = CreateTask.builder().name("Watch the Attack On Titan anime in evening.")
                    .priority(Priority.HIGH)
                    .status(Status.DO).build();
            when(service.addTask(any(UserDetails.class), any(CreateTask.class))).thenReturn("task is added");
            mockMvc.perform(post("/task/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(create)))
                    .andExpect(status().isCreated())
                    .andExpect(content().string("task is added"));
            verify(service).addTask(any(UserDetails.class), any(CreateTask.class));
        }

        @Test
        @DisplayName("Failure")
        void failure() throws Exception {
            CreateTask create = CreateTask.builder().name("Watch the Attack On Titan anime in evening.").build();
            mockMvc.perform(post("/task/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(create)))
                    .andExpect(status().isBadRequest());
            verify(service, times(0)).addTask(eq(user), eq(create));
        }
    }

    @Test
    @DisplayName("Task for updatePriority method")
    void updatePriority() throws Exception{
        when(service.updatePriority(eq(Priority.MEDIUM), anyInt())).thenReturn("priority updated.");
        mockMvc.perform(patch("/task/1/priority")
                        .param("priority", "MEDIUM"))
                .andExpect(status().isOk())
                .andExpect(content().string("priority updated."));
        verify(service).updatePriority(eq(Priority.MEDIUM), anyInt());
    }

    @Test
    @DisplayName("Test for updateStatus method")
    void updateStatus() throws Exception {
        when(service.updateStatus(eq(Status.COMPLETED), anyInt())).thenReturn("status updated.");
        mockMvc.perform(patch("/task/1/status")
                        .param("status", "COMPLETED"))
                .andExpect(status().isOk())
                .andExpect(content().string("status updated."));
        verify(service).updateStatus(eq(Status.COMPLETED), anyInt());
    }

    @Test
    @DisplayName("Test for taskCompleted method")
    void taskCompleted() throws Exception {
        when(service.markAsComplete(anyInt())).thenReturn("Congratulation on completed task.");
        mockMvc.perform(patch("/task/complete/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Congratulation on completed task."));
        verify(service).markAsComplete(anyInt());
    }
}