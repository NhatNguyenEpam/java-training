package com.epam.training.day02;

public interface CommandExecutor<C extends TaskCommand> {
    CommandResult<Task> execute(C command);
}
