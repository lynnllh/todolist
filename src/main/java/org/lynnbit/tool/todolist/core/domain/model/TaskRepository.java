package org.lynnbit.tool.todolist.core.domain.model;

import java.util.List;

public interface TaskRepository {
    void addFinishedTask(Task task);

    void addDoingTask(Task task);

    void addNewTask(Task task);

    List<Task> getTask(long fromTime, long toTime);

    List<Task> getUnfinishedTask();

    void saveTask();

    void loadTask();
}
