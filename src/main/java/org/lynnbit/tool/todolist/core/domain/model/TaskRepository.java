package org.lynnbit.tool.todolist.core.domain.model;

import java.io.IOException;
import java.util.List;

public interface TaskRepository {
    void finishTask(Task task);

    void addNewTask(Task task);

    List<Task> getTask(long fromTime, long toTime);

    List<Task> getUnfinishedTask();

    void changeUnfinishedTaskOrder(Task task, Integer order);

    void saveTask() throws IOException;

    void loadTask() throws IOException;
}
