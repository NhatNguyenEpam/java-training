package com.epam.training.day02;

import java.util.Objects;

public record CommandResult<T>(T value) {
    public CommandResult {
        Objects.requireNonNull(value, "command result");
    }
}
