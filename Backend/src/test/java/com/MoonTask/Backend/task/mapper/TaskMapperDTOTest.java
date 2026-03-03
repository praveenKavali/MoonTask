package com.MoonTask.Backend.task.mapper;

import com.MoonTask.Backend.task.dto.CreateTask;
import com.MoonTask.Backend.task.entity.Priority;
import com.MoonTask.Backend.task.entity.Status;
import com.MoonTask.Backend.task.entity.TaskInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperDTOTest {

    TaskMapperDTO dto = new TaskMapperDTO();

    @Test
    void mappedToTask() {
        CreateTask create = CreateTask.builder().name("Pay the electricity bill")
                .priority(Priority.HIGH)
                .status(Status.DO).build();
        TaskInfo task = dto.mappedToTask(create);
        assertNotNull(task);
        assertEquals("Pay the electricity bill", task.getName());
    }
}