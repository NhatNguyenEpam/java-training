package com.epam.training.day02;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory storage, currently use LinkedHashMap, using another Collection in Day03
 */
public class InMemoryStore implements TaskStore {
    private final Map<Long, Task> tasks = new LinkedHashMap<>();

    @Override
    public Optional<Task> findById(long taskId) {
        return Optional.ofNullable(tasks.get(taskId));
    }

    @Override
    public void insert(Task task) {
        if (tasks.putIfAbsent(task.id(), task) != null) {
            throw new TaskException(TaskException.Reason.DUPLICATE_TASK, "Task #" + task.id() + " already exists");
        }
    }

    @Override
    public void update(Task task) {
        if (tasks.replace(task.id(), task) == null) {
            throw new TaskException(TaskException.Reason.TASK_NOT_FOUND, "Task #" + task.id() + " was not found");
        }
    }
}
