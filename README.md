# EPAM Java Training Course: Smart Task Manager

The Java Course aims to revise Java and Spring framework knowledge. Besides, this repo will implement a simple poc of Replicated State Machine that uses [Aeron](https://github.com/aeron-io) for learning distributed system concepts like: Raft consensus, Leader election, Determinism replay, etc.

## Project Overview

User can:
- Create a task through `POST /tasks`.
- Update task status through `PATCH /tasks/{id}/status`.
- Retrieve a task through `GET /tasks/{id}`.
- Retrieve paginated tasks through `GET /tasks`.

The application can:
- Replicate its state across three  Aeron Cluster nodes.
- Elect new leader when current leader crashes by Raft consensus.
- Replay the data when any failure happens.

## Architecture Overview
![img.png](img/architecture_overview.png)

## Task State Machine Diagram
![img.png](img/task_state_machine.png)
