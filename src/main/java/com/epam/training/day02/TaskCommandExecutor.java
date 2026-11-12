package com.epam.training.day02;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class TaskCommandExecutor implements CommandExecutor<TaskCommand> {
    private final TaskStore store;

    public TaskCommandExecutor(TaskStore store) {
        this.store = Objects.requireNonNull(store, "store");
    }

    @Override
    public CommandResult<Task> execute(TaskCommand command) {
       if (command instanceof CreateTask(
               long taskId, String title, List<String> tags, Instant occurredAt
       )) {
           Task task = Task.create(taskId, title, tags, occurredAt);
            store.insert(task);
            return new CommandResult<>(task);
       }

       Task existing = store.findById(command.taskId()).orElseThrow(() ->
               new TaskException(TaskException.Reason.TASK_NOT_FOUND,
                       "Task #" + command.taskId() + " was not found"));

       Task updated = switch (command) {
           case AssignTask assign -> existing.assign(assign.assignee(), assign.occurredAt());
           case StartTask start -> existing.start(start.occurredAt());
           case CompleteTask complete -> existing.complete(complete.occurredAt());
           case CancelTask cancel -> existing.cancel(cancel.occurredAt());
           case CreateTask ignored -> throw new IllegalStateException("Creation is handled before lookup");
       };

       store.update(updated);
       return new CommandResult<>(updated);
    }
}
