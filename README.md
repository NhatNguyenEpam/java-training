# EPAM Java Training Course: Smart Task Manager

This course revisits Java and the Spring framework through a Smart Task Manager project. The repository will also implement a proof of concept of a replicated state machine using [Aeron](https://github.com/aeron-io) to explore Raft consensus, leader election, and deterministic replay.

## Project Overview

The planned API will let users:

- Create a task through `POST /tasks`.
- Update task status through `PATCH /tasks/{id}/status`.
- Retrieve a task through `GET /tasks/{id}`.
- Retrieve paginated tasks through `GET /tasks`.

The application goals are to:

- Replicate task state across three Aeron Cluster nodes.
- Elect a new leader through Raft consensus when the current leader fails and a quorum remains available.
- Recover task state from snapshots and the replicated log after a failure.

## Architecture Overview

![img.png](img/architecture_overview.png)

Aeron Cluster will own authoritative task state and process ordered mutation commands.<br>
PostgreSQL and Spring Data JPA will provide an external read model for task queries and history.<br>

State changes are deterministic:<br>
- Task IDs and time through controlled command inputs or cluster-provided timestamps
- Preserve ordering where it affects results, and reproduce state and outputs from the same initial state and ordered inputs.
- Keep external I/O, password checks, asynchronous work, and cache access outside replicated task processing.<br>
⇒ Follow [Aeron's clustered-service tutorial](https://github.com/aeron-io/aeron/wiki/Cluster-Tutorial).

## Task State Machine Diagram

![img.png](img/task_state_machine.png)