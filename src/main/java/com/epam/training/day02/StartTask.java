package com.epam.training.day02;

import java.time.Instant;

public record StartTask(long taskId, Instant occurredAt) implements TaskCommand {
}
