package com.epam.training.day02;

import java.time.Instant;
import java.util.List;

public record CreateTask(
        long taskId,
        String title,
        List<String> tags,
        Instant occurredAt
) implements TaskCommand {
    public CreateTask {
        TaskException.require(tags != null, "Task tags are required");
        for (String tag : tags) {
            TaskException.require(tag != null, "Task tags must not contain null");
        }
        tags = List.copyOf(tags);
    }
}
