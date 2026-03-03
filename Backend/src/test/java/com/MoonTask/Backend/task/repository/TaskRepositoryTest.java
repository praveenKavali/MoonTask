package com.MoonTask.Backend.task.repository;

import com.MoonTask.Backend.task.entity.Priority;
import com.MoonTask.Backend.task.entity.Status;
import com.MoonTask.Backend.task.entity.TaskInfo;
import com.MoonTask.Backend.user.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
       /* UserInfo user = UserInfo.builder()
                .name("testuser")
                .email("test@example.com")
                .password("00000000")
                .build();
        userRepo.save(user);*/

        task1 = TaskInfo.builder().name("Pay the electricity bill")
                .priority(Priority.HIGH)
                .status(Status.DO)
                .description("hh")
                .createdTime(LocalTime.now()).createdDate(LocalDate.now())
                .completedTime(LocalTime.now())
                .completedDate(LocalDate.now()).build();
        task2 = TaskInfo.builder().name("study").priority(Priority.MEDIUM).status(Status.DO)
                .description("hh")
                .createdTime(LocalTime.now()).createdDate(LocalDate.now())
                .completedTime(LocalTime.now())
                .completedDate(LocalDate.now()).build();
    }

    @Test
    @DisplayName("Test for getAllTask method")
    void getAllTask() {
        repo.save(task1);
        repo.save(task2);
        List<TaskInfo> tasks = repo.getAllTask();
        assertEquals(6, tasks.size());
        assertThat(tasks.get(4).getId()).isGreaterThan(tasks.get(5).getId());
    }

    @Test
    @DisplayName("Test for findByPriority method")
    void findByPriority() {
        repo.save(task1);
        repo.save(task2);
        List<TaskInfo> tasks = repo.findByPriority(Priority.MEDIUM);
        assertEquals(3, tasks.size());
        assertEquals("study", tasks.get(2).getName());
    }

    @Test
    @DisplayName("Test for findByStatus method")
    void findByStatus() {
        repo.save(task1);
        repo.save(task2);
        List<TaskInfo> tasks = repo.findByStatus(Status.DO);
        assertEquals(11, tasks.size());
        assertEquals(Status.DO, tasks.get(10).getStatus());
    }

    @Test
    @DisplayName("Test for searchForTask method")
    void searchForTask() {
        repo.save(task1);
        repo.save(task2);
        List<TaskInfo> tasks = repo.searchForTask("bill");
        assertEquals(5, tasks.size());
        assertEquals("Pay the electricity bill", tasks.get(4).getName());
    }

    @Test
    @DisplayName("Test for updatePriority method")
    void updatePriority() {
        repo.save(task1);
        repo.save(task2);
        repo.updatePriority(Priority.HIGH, 2);
        List<TaskInfo> tasks = repo.getAllTask();
        assertEquals(4, tasks.size());
        assertEquals(Priority.HIGH, tasks.get(3).getPriority());
    }

    @Test
    @DisplayName("Test for updateStatus method")
    void updateStatus() {
        repo.save(task1);
        repo.save(task2);
        repo.updateStatus(Status.InPROGRESS, 2);
        List<TaskInfo> tasks = repo.getAllTask();
        assertEquals(2, tasks.size());
        assertEquals(Status.InPROGRESS, tasks.get(0).getStatus());
    }
}