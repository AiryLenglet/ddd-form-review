package ch.lenglet.repository;

import ch.lenglet.workflow.Task;

public interface TaskRepository {

    Task findLatestByTaskId(String taskId);
    void save(Task task);
}
