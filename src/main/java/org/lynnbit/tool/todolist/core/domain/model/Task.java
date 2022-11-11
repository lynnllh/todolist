package org.lynnbit.tool.todolist.core.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Task {

    private Task(String content) {
        id = UUID.randomUUID().getMostSignificantBits();
        this.content = content;
        isFinish = false;
    }

    private long id;

    private boolean isFinish;

    private long startTime;

    private long finishTime;

    private String content;

    private List<Label> labels = new ArrayList<Label>();

    private Category category;

    public static Task createTask(String content) {
        Task task = new Task(content);
        return task;
    }

    public static Task createTask(String content, Label label) {
        Task task = createTask(content);
        task.addLabel(label);
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
        startTime = System.currentTimeMillis();
    }

    public void finishTask() {
        isFinish = true;
        finishTime = System.currentTimeMillis();
    }

    public void move2Category(Category category) {
        this.category = category;
    }
}
