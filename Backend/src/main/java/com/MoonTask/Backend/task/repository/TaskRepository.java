package com.MoonTask.Backend.task.repo;

import com.MoonTask.Backend.task.entity.Priority;
import com.MoonTask.Backend.task.entity.Status;
import com.MoonTask.Backend.task.entity.TaskInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * A repository class which is communicates with database. It extends {@link JpaRepository} which contains all the necessary methods for data manipulations.*/
public interface TaskRepository extends JpaRepository<TaskInfo, Integer> {
    /**
     * This method is useful for finding the list of tasks based on priority.
     * @param priority {@link Priority} used to filter the task.
     * @return list of tasks after being filtered.*/
    List<TaskInfo> findByPriority(Priority priority);

    /**
     * This method is useful for finding the list of tasks based on status.
     * @param status {@link Status} used to filter the task.
     * @return list of tasks after being filtered.*/
    List<TaskInfo> findByStatus(Status status);
}
