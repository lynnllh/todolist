package org.lynnbit.tool.todolist.core.infrastructure.persistent;

import java.util.ArrayList;
import java.util.List;

import org.lynnbit.tool.todolist.core.domain.model.Task;
import org.lynnbit.tool.todolist.core.domain.model.TaskRepository;

public class TaskRepositoryImpl implements TaskRepository {
    private List<Task> finishedTask = new ArrayList<>();

    private List<Task> doingTask = new ArrayList<>();

    private List<Task> newTask = new ArrayList<>();

    @Override
    public void addFinishedTask(Task task) {
        if (!doingTask.contains(task)) {
            throw new IllegalStateException("the task is not started");
        }
        doingTask.remove(task);
        finishedTask.add(task);
    }

    @Override
    public void addDoingTask(Task task) {
        if (!newTask.contains(task)) {
            throw new IllegalStateException("the task is not created");
        }
        newTask.remove(task);
        doingTask.add(task);
    }

    @Override
    public void addNewTask(Task task) {
        newTask.add(task);
    }

    @Override
    public List<Task> getUnfinishedTask() {
        List<Task> ret = new ArrayList<>();
        ret.addAll(doingTask);
        ret.addAll(newTask);
        return ret;
    }

    @Override
    public List<Task> getTask(long fromTime, long toTime) {
        return null;
    }
}
