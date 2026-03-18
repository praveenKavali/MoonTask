package com.MoonTask.Backend.task.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.MoonTask.Backend.user.entity.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.MoonTask.Backend.task.entity.Priority;
import com.MoonTask.Backend.task.entity.Status;
import com.MoonTask.Backend.task.entity.TaskInfo;
import com.MoonTask.Backend.user.repository.UserRepo;

@Testcontainers
@SpringBootTest
class TaskRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    TaskRepository repo;
    @Autowired
    UserRepo userRepo;
    TaskInfo task1;
    TaskInfo task2;

    @BeforeEach
    void setUp() {
        userRepo.deleteAll();
        repo.deleteAll();
        saveUser();

        UserInfo user = userRepo.findByEmail("test@example.com").orElseThrow();

        task1 = TaskInfo.builder()
                .name("Pay the electricity bill")
                .priority(Priority.HIGH)
                .status(Status.DO)
                .description("hh")
                .createdTime(LocalTime.now())
                .createdDate(LocalDate.now())
                .completedTime(LocalTime.now())
                .completedDate(LocalDate.now())
                .user(user)
                .build();

        task2 = TaskInfo.builder()
                .name("study")
                .priority(Priority.MEDIUM)
                .status(Status.DO)
                .description("hh")
                .createdTime(LocalTime.now())
                .createdDate(LocalDate.now())
                .completedTime(LocalTime.now())
                .completedDate(LocalDate.now())
                .user(user)
                .build();
    }

    void saveUser() {
        UserInfo user = UserInfo.builder()
                .name("testuser")
                .email("test@example.com")
                .password("00000000")
                .build();
        userRepo.save(user);
    }

    @Test
    @DisplayName("Test for getAllTask method")
    void getAllTask() {
        repo.save(task1);
        repo.save(task2);
        List<TaskInfo> tasks = repo.getAllTask("test@example.com");
        assertNotEquals(0, tasks.size());
        assertNotNull(tasks);
    }

    @Test
    @DisplayName("Test for findByPriority method")
    void findByPriority() {
        repo.save(task1);
        repo.save(task2);
        List<TaskInfo> tasks = repo.findByUserEmailAndPriority("test@example.com", Priority.MEDIUM);
        assertNotEquals(0, tasks.size());
        assertNotNull(tasks);
    }

    @Test
    @DisplayName("Test for findByStatus method")
    void findByStatus() {
        repo.save(task1);
        repo.save(task2);
        List<TaskInfo> tasks = repo.findByUserEmailAndStatus("test@example.com", Status.DO);
        assertNotEquals(0, tasks.size());
        assertNotNull(tasks);
    }

    @Test
    @DisplayName("Test for searchForTask method")
    void searchForTask() {
        repo.save(task1);
        repo.save(task2);
        List<TaskInfo> tasks = repo.searchForTask("test@example.com", "bill");
        assertNotEquals(0, tasks.size());
        assertNotNull(tasks);
    }

    @Test
    @DisplayName("Test for updatePriority method")
    void updatePriority() {
        repo.save(task1);
        repo.save(task2);
        repo.updatePriority(Priority.HIGH, 2);
        List<TaskInfo> tasks = repo.getAllTask("test@example.com");
        assertNotEquals(0, tasks.size());
        assertNotNull(tasks);
    }

    @Test
    @DisplayName("Test for updateStatus method")
    void updateStatus() {
        repo.save(task1);
        repo.save(task2);
        repo.updateStatus(Status.InPROGRESS, 2);
        List<TaskInfo> tasks = repo.getAllTask("test@example.com");
        assertNotEquals(0, tasks.size());
        assertNotNull(tasks);
    }
}