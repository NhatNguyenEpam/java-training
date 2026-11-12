package com.epam.training.day02;


import java.util.Optional;

public interface TaskStore {
    Optional<Task> findById(long taskId);

    void insert(Task task);

    void update(Task task);
}
