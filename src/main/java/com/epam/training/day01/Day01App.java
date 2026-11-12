package com.epam.training.day01;

public class Day01App {
    private Day01App() {}

    static void main(String[] args) {
        LearningTask task = new LearningTask(1, "Learn Java Syntax");
        System.out.println(task.summary());
    }
}
