package com.epam.training.day02;

import java.time.Instant;

public record AssignTask(long taskId, String assignee, Instant occurredAt) implements TaskCommand {
}
