package com.MoonTask.Backend.task.entity;

/**
 * Tasks are divided into 3 types.
 * HIGH: A task that takes maximum priority(or a task that needed to be completed very soon).
 * MEDIUM: A task that has less priority compared to HIGH.
 * LOW: A task that has low priority.*/
public enum Priority {
    HIGH,
    MEDIUM,
    LOW;
}
