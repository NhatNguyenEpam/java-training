package com.epam.training.day02;

import java.util.Objects;

public class TaskException extends RuntimeException {
    public enum Reason {
        INVALID_INPUT, DUPLICATE_TASK, TASK_NOT_FOUND, INVALID_TRANSITION
    }

    private final Reason reason;

    public Reason reason() {
        return reason;
    }

    public TaskException(Reason reason, String message) {
        super(message);
        this.reason = Objects.requireNonNull(reason, "reason");
    }

    static void require(boolean condition, String message) {
        if (!condition) {
            throw new TaskException(Reason.INVALID_INPUT, message);
        }
    }
}
