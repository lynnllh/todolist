package org.lynnbit.tool.todolist.ui;

import static org.lynnbit.tool.todolist.ui.Constant.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class TaskPane {
    public static Pane create(String content, List<org.lynnbit.tool.todolist.core.domain.model.Label> labels) {

        FlowPane taskPane = new FlowPane();

        taskPane.getChildren().add(createTaskLabel(content));
        taskPane.getChildren().add(createLabelPane(labels));
        taskPane.setPickOnBounds(true);

        taskPane.setPrefHeight(getContentHeight(content, TASK_FONT_SIZE, MAX_TASK_LABEL_WIDTH));
        return taskPane;
    }

    private static Label createTaskLabel(String content) {
        content = addIntent(content);
        Label task = new Label(content);
        task.setFont(new Font("微软雅黑", TASK_FONT_SIZE));
        task.setMaxWidth(MAX_TASK_LABEL_WIDTH);
        task.setPrefWidth(MAX_TASK_LABEL_WIDTH);
        task.setWrapText(true);
        return task;
    }

    private static FlowPane createLabelPane(List<org.lynnbit.tool.todolist.core.domain.model.Label> labels) {
        FlowPane labelPane = new FlowPane();

        for (org.lynnbit.tool.todolist.core.domain.model.Label labelModel : labels) {
            final Label label = createLabel(labelModel);

            label.setOnMouseEntered((e) -> {
                label.setScaleX(1.2);
                label.setScaleY(1.2);
            });

            label.setOnMouseExited(e -> {
                label.setScaleX(1);
                label.setScaleY(1);
            });
            labelPane.getChildren().add(label);
        }

        labelPane.setHgap(4);
        labelPane.setVgap(3);
        labelPane.setMaxWidth(MAX_LABEL_LABEL_WIDTH);
        return labelPane;
    }

    private static String addIntent(String content) {
        return "  " + content;
    }

    private static double getContentHeight(String content, int taskFontSize, int maxTaskLabelWidth) {
        content = addIntent(content);
        int rows = (content.length() * taskFontSize / maxTaskLabelWidth) + 1;
        return rows * taskFontSize + (rows - 1) * 7 + 10;
    }

    private static Label createLabel(org.lynnbit.tool.todolist.core.domain.model.Label labelModel) {
        Label label = new Label(getSplitedLineContent(labelModel.getName(), LABEL_FONT_SIZE, MAX_LABEL_LABEL_WIDTH));
        label.setFont(new Font("微软雅黑", LABEL_FONT_SIZE));
        label.setBackground(new Background(new BackgroundFill(labelModel.getColor(), new CornerRadii(3), null)));
        return label;
    }

    private static String getSplitedLineContent(String content, int fontSize, int maxWidth) {
        if (content.length() * fontSize < maxWidth - 10) {
            return content;
        }

        List<String> list = Arrays.asList(content.split(""));
        List<String> ret = new ArrayList<>();

        int i = 0;
        for (String str : list) {
            if ((i + 1) * fontSize >= maxWidth - 10) {
                ret.add(System.lineSeparator());
                i = 0;
            }
            ret.add(str);
            i++;
        }
        return StringUtils.join(ret, "");
    }
}
