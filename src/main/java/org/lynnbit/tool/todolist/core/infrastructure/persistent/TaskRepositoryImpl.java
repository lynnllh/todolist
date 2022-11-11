package org.lynnbit.tool.todolist.core.infrastructure.persistent;

import java.util.ArrayList;
import java.util.List;

import org.lynnbit.tool.todolist.core.domain.model.Task;
import org.lynnbit.tool.todolist.core.domain.model.TaskRepository;

public class TaskRepositoryImpl implements TaskRepository {
    private List<Task> finishedTask = new ArrayList<Task>();

    private List<Task> doingTask = new ArrayList<Task>();

    public void addFinishedTask(Task task) {
        finishedTask.add(task);
    }

    public void addDoingTask(Task task) {
        doingTask.add(task);
    }

    public List<Task> getTask(long fromTime, long toTime) {
        return null;
    }
}
