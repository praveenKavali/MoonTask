package com.MoonTask.Backend.task.repository;

import com.MoonTask.Backend.task.entity.Priority;
import com.MoonTask.Backend.task.entity.Status;
import com.MoonTask.Backend.task.entity.TaskInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A repository class which is communicates with database. It extends {@link JpaRepository}
 * which contains all the necessary methods for data manipulations.*/
@Repository
public interface TaskRepository extends JpaRepository<TaskInfo, Integer> {

    //This method is used to find the all task in descending order
    @Query(value = "SELECT * FROM Task_Information ORDER BY Id DESC", nativeQuery = true)
    List<TaskInfo> getAllTask();
    
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

    /**
     * This method is useful for searching the task based on the words
     * @param  word a string letter or word useful for searching through database.
     * @return a list of tasks that are start with words.*/
    @Query(value = "SELECT * FROM Task_Information WHERE name LIKE CONCAT('%', :word, '%')", nativeQuery = true)
    List<TaskInfo> searchForTask(@Param("word") String word);

    /**
     * This method is useful for changing priority of the task.
     * @param priority {@link Priority} a new priority to update the older one.
     * @param id for updating specific task. if we don't provide id we will update all task which is not a good option.*/
    @Modifying
    @Transactional
    @Query("UPDATE TaskInfo t SET t.priority = :priority where t.id = :id")
    void updatePriority(@Param("priority") Priority priority, @Param("id") Integer id);

    /**
     * This method is useful for changing status of the task.
     *
     * @param status {@link Status} a new status to update the older one.
     * @param id     for updating specific task. if we don't provide id we will update all task which is not a good option.
     */
    @Modifying
    @Transactional
    @Query("UPDATE TaskInfo t SET t.status = :status where t.id = :id")
    void updateStatus(@Param("status") Status status, @Param("id") Integer id);
}
