package org.lynnbit.tool.todolist.core.domain.model;

import java.util.List;

public interface TaskRepository {
    void addFinishedTask(Task task);

    void addDoingTask(Task task);

    List<Task> getTask(long fromTime, long toTime);
}
