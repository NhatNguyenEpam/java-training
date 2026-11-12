package com.epam.training.day02;

import java.time.Instant;
import java.util.List;

/**
 * immutable state by using Record and defensive copy
 */
public record Task(
        Long id,
        String title,
        TaskStatus status,
        String assignee,
        List<String> tags,
        Instant createdAt, Instant updatedAt
) {
    public Task {
        TaskException.require(id > 0, "Task id must be positive");
        TaskException.require(title != null && !title.isBlank(), "Task title must not be blank");
        TaskException.require(status != null, "Task status is required");
        TaskException.require(tags != null, "Task tags are required");
        for (String tag : tags) {
            TaskException.require(tag != null, "Task tags must not contain null");
        }
        // defensive copy -> return an unmodifiable List
        tags = List.copyOf(tags);
        TaskException.require(createdAt != null && updatedAt != null, "Task timestamps are required");
        TaskException.require(!updatedAt.isBefore(createdAt), "Updated time must not precede creation");
        if (status == TaskStatus.CREATED) {
            TaskException.require(assignee == null, "A created task must be unassigned");
            TaskException.require(createdAt.equals(updatedAt), "A created task has no updates yet");
        } else if (status != TaskStatus.CANCELED || assignee != null) {
            TaskException.require(assignee != null && !assignee.isBlank(), "Assignee must not be blank");
        }
    }

    public static Task create(long id, String title, List<String> tags, Instant occurredAt) {
        return new Task(id, title, TaskStatus.CREATED, null, tags, occurredAt, occurredAt);
    }

    public Task assign(String assignee, Instant occurredAt) {
        requireStatus("assign", TaskStatus.CREATED);
        TaskException.require(assignee != null && !assignee.isBlank(), "Assignee must not be blank");
        return transition(TaskStatus.ASSIGNED, assignee, occurredAt);
    }

    public Task start(Instant occurredAt) {
        requireStatus("start", TaskStatus.ASSIGNED);
        return transition(TaskStatus.IN_PROGRESS, assignee, occurredAt);
    }

    public Task complete(Instant occurredAt) {
        requireStatus("complete", TaskStatus.IN_PROGRESS);
        return transition(TaskStatus.COMPLETED, assignee, occurredAt);
    }

    public Task cancel(Instant occurredAt) {
        requireStatus("cancel", TaskStatus.CREATED, TaskStatus.ASSIGNED, TaskStatus.IN_PROGRESS);
        return transition(TaskStatus.CANCELED, assignee, occurredAt);
    }

    private Task transition(TaskStatus target, String nextAssignee, Instant occurredAt) {
        TaskException.require(occurredAt != null, "Operation timestamp is required");
        TaskException.require(!occurredAt.isBefore(updatedAt), "operation timestamp must not move backward");
        return new Task(id, title, target, nextAssignee, tags, createdAt, occurredAt);
    }

    private void requireStatus(String operation, TaskStatus... allowed) {
        for (TaskStatus source : allowed) {
            if (source == status) {
                return;
            }
        }

        throw new TaskException(TaskException.Reason.INVALID_TRANSITION,
                "Cannot " + operation + " task #" + id + " from " + status);

    }
}
