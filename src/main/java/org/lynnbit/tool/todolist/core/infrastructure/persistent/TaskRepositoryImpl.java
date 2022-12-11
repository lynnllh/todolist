package org.lynnbit.tool.todolist.core.infrastructure.persistent;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.lynnbit.tool.todolist.core.domain.model.Task;
import org.lynnbit.tool.todolist.core.domain.model.TaskRepository;

import com.alibaba.fastjson.JSON;

public class TaskRepositoryImpl implements TaskRepository {
    private static final String UNFINISHED_TASK_FILE_NAME = "unfinishedTask.json";
    private static final String FINISHED_TASK_FILE_NAME = "finishedTask.json";
    private static final String PROJECT_DIR = "\\todolist\\";
    private static String USER_HOME = System.getProperty("user.home");

    private List<Task> finishedTask = new ArrayList<>();

    private List<Task> unfinishedTask = new ArrayList<>();

    @Override
    public void finishTask(Task task) {
        if (!unfinishedTask.contains(task)) {
            throw new IllegalStateException("the task is not created");
        }
        unfinishedTask.remove(task);
        finishedTask.add(task);
    }

    @Override
    public void addNewTask(Task task) {
        unfinishedTask.add(task);
    }

    @Override
    public List<Task> getUnfinishedTask() {
        List<Task> ret = new ArrayList<>();
        ret.addAll(unfinishedTask);
        return ret;
    }

    @Override
    public void changeUnfinishedTaskOrder(Task task, Integer order) {
        if (!unfinishedTask.contains(task)) {
            throw new IllegalStateException("the task is not created");
        }

        if (order >= unfinishedTask.size()) {
            throw new IllegalStateException("the order is exceed the size of unfinished task list");
        }
        unfinishedTask.remove(task);
        unfinishedTask.add(order, task);
    }

    @Override
    public List<Task> getTask(long fromTime, long toTime) {
        return null;
    }

    @Override
    public void saveTask() throws IOException {
        FileUtils.write(new File(USER_HOME + PROJECT_DIR + UNFINISHED_TASK_FILE_NAME),
            JSON.toJSONString(unfinishedTask), Charset.forName("UTF-8"));
        FileUtils.write(new File(USER_HOME + PROJECT_DIR + FINISHED_TASK_FILE_NAME), JSON.toJSONString(finishedTask),
            Charset.forName("UTF-8"));
    }

    @Override
    public void loadTask() throws IOException {
        String unfinishedTaskStr = FileUtils
            .readFileToString(new File(USER_HOME + PROJECT_DIR + UNFINISHED_TASK_FILE_NAME), Charset.forName("UTF-8"));

        String finishedTaskStr = FileUtils.readFileToString(new File(USER_HOME + PROJECT_DIR + FINISHED_TASK_FILE_NAME),
            Charset.forName("UTF-8"));

        unfinishedTask = JSON.parseArray(unfinishedTaskStr, Task.class);
        finishedTask = JSON.parseArray(finishedTaskStr, Task.class);
    }
}
