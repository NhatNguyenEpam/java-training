package com.epam.training.day02;

import java.time.Instant;

public sealed interface TaskCommand permits CreateTask, AssignTask, StartTask, CompleteTask, CancelTask{
    long taskId();

    Instant occurredAt();
}
