package org.lynnbit.tool.todolist.core.application;

import java.util.List;
import java.util.Objects;

import org.lynnbit.tool.todolist.core.domain.model.Task;
import org.lynnbit.tool.todolist.core.domain.model.TaskRepository;
import org.lynnbit.tool.todolist.core.infrastructure.persistent.TaskRepositoryImpl;

/**
 * 对UI层的统一接口
 */
public class TaskService {
    private static TaskRepository taskRepository = new TaskRepositoryImpl();

    private TaskService() {}

    private static TaskService taskService;

    public static TaskService getInstance() {
        if (Objects.isNull(taskService)) {
            taskService = new TaskService();
        }

        return taskService;
    }

    public void saveTask() {
        taskRepository.saveTask();
    }

    public void loadTask() {
        taskRepository.loadTask();
    }

    public List<Task> getUnfinishedTask() {
        return taskRepository.getUnfinishedTask();
    }

    public Task createTask(String content, String... labels) {
        return Task.createTask(content, labels);
    }
}
