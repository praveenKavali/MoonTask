package com.MoonTask.Backend.task.mapper;

import com.MoonTask.Backend.task.dto.CreateTask;
import com.MoonTask.Backend.task.entity.TaskInfo;
import org.springframework.stereotype.Component;

@Component
public class MapperDTO {

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
