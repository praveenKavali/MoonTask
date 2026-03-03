package com.MoonTask.Backend.task.mapper;

import com.MoonTask.Backend.task.dto.CreateTask;
import com.MoonTask.Backend.task.entity.TaskInfo;
import org.springframework.stereotype.Component;

/**
 * A mapper class convert {@link CreateTask} to {@link TaskInfo}*/
@Component
public class TaskMapperDTO {

    /**
     * This method transform user entered details into to {@link TaskInfo}
     * @param task container the user entered details.
     * @return {@link TaskInfo} for storing database.*/
    public TaskInfo mappedToTask(CreateTask task){
        return TaskInfo.builder()
                .name(task.getName())
                .priority(task.getPriority())
                .status(task.getStatus())
                .description(task.getDescription())
                .build();
    }
}
