package com.epam.training.day01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class LearningTaskTest {
    @Test
    void constructionAndPreserveIdentityAndShareReferences(){
        LearningTask task = new LearningTask(1, "Original");
        task.rename("Renamed");
        assertAll(
                () -> assertEquals(1, task.getId()),
                () -> assertEquals("Renamed", task.getTitle()),
                () -> assertEquals("Task #1: Renamed", task.summary())
        );
    }

    @ParameterizedTest
    @ValueSource(longs = {0, -1, Long.MIN_VALUE})
    void rejectsInvalidIds(long id) {
        assertThrows(IllegalArgumentException.class, () -> new LearningTask(id, "Title"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t\n"})
    void rejectInvalidTitle(String title) {
        assertThrows(IllegalArgumentException.class, () -> new LearningTask(1, title));
        assertThrows(IllegalArgumentException.class, () ->
                new LearningTask(1, "original").rename(title));
    }
}
