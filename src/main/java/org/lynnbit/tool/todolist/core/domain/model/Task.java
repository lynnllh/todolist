package org.lynnbit.tool.todolist.core.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.lynnbit.tool.todolist.core.infrastructure.persistent.TaskRepositoryImpl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private static TaskRepository taskRepository = new TaskRepositoryImpl();

    public Task(String content) {
        id = UUID.randomUUID().getMostSignificantBits();
        this.content = content;
        isFinish = false;
        taskRepository.addNewTask(this);
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

    public static Task createTask(String content, String... labels) {
        Task task = createTask(content);
        for (String label : labels) {
            task.addLabel(Label.create(label));
        }
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

    public String getContent() {
        return content;
    }

    public String[] getLabelNames() {
        return labels.stream().map(Label::getName).collect(Collectors.toList()).toArray(new String[0]);
    }
}
