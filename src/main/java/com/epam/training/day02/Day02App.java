package com.epam.training.day02;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Day02App {
    private static final Instant BASE_TIME = Instant.parse("2026-01-01T00:00:00Z");

    private Day02App() {
    }

    public static void main(String[] args) {
        TaskStore store = new InMemoryStore();
        CommandExecutor<TaskCommand> executor = new TaskCommandExecutor(store);
        List<CommandResult<Task>> originalResults = new ArrayList<>();

        for (TaskCommand command : sampleCommands()) {
            originalResults.add(executeAndPrint(executor, command));
        }
    }

    public static List<TaskCommand> sampleCommands() {
        return List.of(
                new CreateTask(101, "Finish Day 2", List.of("java", "oop"), at(0)),
                new AssignTask(101, "alice", at(1)),
                new StartTask(101, at(2)),
                new CompleteTask(101, at(3)),
                new CreateTask(102, "Cancel before assignment", List.of(), at(0)),
                new CancelTask(102, at(1)),
                new CreateTask(103, "Cancel after assignment", List.of(), at(0)),
                new AssignTask(103, "bob", at(1)),
                new CancelTask(103, at(2)),
                new CreateTask(104, "Cancel work in progress", List.of(), at(0)),
                new AssignTask(104, "carol", at(1)),
                new StartTask(104, at(2)),
                new CancelTask(104, at(3)));
    }

    public static <C extends TaskCommand> CommandResult<Task> executeAndPrint(
            CommandExecutor<? super C> executor, C command) {
        CommandResult<Task> result = executor.execute(command);
        System.out.println(result.value());
        return result;
    }

    private static Instant at(long seconds) {
        return BASE_TIME.plusSeconds(seconds);
    }
}
