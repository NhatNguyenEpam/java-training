package com.epam.training.day01;

public class LearningTask {
    private final long id;
    private String title;

    public LearningTask(long id, String title) {
        if (id <= 0) {
            throw new IllegalArgumentException("Task ID must be positive");
        }
        this.id = id;
        this.title = validateTitle(title);
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void rename(String title) {
        this.title = validateTitle(title);
    }

    // package-level access modifier
    String summary() {
        return "Task #" + id + ": " + title;
    }

    public static String validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("");
        }

        return title;
    }
}
