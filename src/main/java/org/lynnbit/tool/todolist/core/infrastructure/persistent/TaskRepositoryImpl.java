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
    private static final String NEW_TASK_FILE_NAME = "newTask.json";
    private static final String DOING_TASK_FILE_NAME = "doingTask.json";
    private static final String FINISHED_TASK_FILE_NAME = "finishedTask.json";
    private static final String PROJECT_DIR = "\\todolist\\";
    private static String USER_HOME = System.getProperty("user.home");

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

    @Override
    public void saveTask() {
        try {
            FileUtils.write(new File(USER_HOME + PROJECT_DIR + NEW_TASK_FILE_NAME), JSON.toJSONString(newTask),
                Charset.forName("UTF-8"));
            FileUtils.write(new File(USER_HOME + PROJECT_DIR + DOING_TASK_FILE_NAME), JSON.toJSONString(doingTask),
                Charset.forName("UTF-8"));
            FileUtils.write(new File(USER_HOME + PROJECT_DIR + FINISHED_TASK_FILE_NAME),
                JSON.toJSONString(finishedTask), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadTask() {
        // FileUtils.readFileToString()
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.home"));
    }
}
