package org.lynnbit.tool.todolist.core.domain.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.lynnbit.tool.todolist.core.infrastructure.persistent.TaskRepositoryImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Task {
    private static TaskRepository taskRepository = TaskRepositoryImpl.getInstance();

    public Task(String content) {
        id = UUID.randomUUID().getMostSignificantBits();
        this.content = content;
        state = TaskState.NEW;
    }

    private long id;

    private TaskState state;

    private long startTime;

    private long finishTime;

    private String content;

    private List<Label> labels = new ArrayList<>();

    public static Task createTask(String content) {
        Task task = new Task(content);
        return task;
    }

    public static Task createTask(String content, String... labels) {
        Task task = createTask(content);
        for (String label : labels) {
            task.addLabel(Label.getOrCreateLabel(label));
        }
        taskRepository.addNewTask(task);
        return task;
    }

    public void modify(String content) {
        this.content = content;
    }

    public void cancel() {

    }

    public void addLabel(Label label) {
        labels.add(label);
    }

    public void addLabels(List<Label> list) {
        labels.addAll(list);
    }

    public void startTask() {
        if (!state.equals(TaskState.NEW)) {
            throw new IllegalStateException("the task is not just created.");
        }

        startTime = System.currentTimeMillis();
        state = TaskState.DOING;
    }

    public void finishTask() {
        if (!state.equals(TaskState.DOING)) {
            throw new IllegalStateException("the task is not started.");
        }
        finishTime = System.currentTimeMillis();
        state = TaskState.FINISHED;
        taskRepository.finishTask(this);
    }

    public void changeOrder(Integer order) {
        taskRepository.changeUnfinishedTaskOrder(this, order);
    }

    public String getContent() {
        return content;
    }

    public String[] getLabelNames() {
        return labels.stream().map(Label::getName).collect(Collectors.toList()).toArray(new String[0]);
    }

    public static List<Task> getUnfinishedTask() {
        return taskRepository.getUnfinishedTask();
    }

    public static void saveTask() {
        try {
            taskRepository.saveTask();
        } catch (IOException e) {
            log.error("save task failed.", e);
        }
    }

    public static void loadTask() {
        try {
            taskRepository.loadTask();
        } catch (IOException e) {
            log.error("load task failed.", e);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Task other = (Task)obj;
        return Objects.equals(id, other.id);
    }
}
