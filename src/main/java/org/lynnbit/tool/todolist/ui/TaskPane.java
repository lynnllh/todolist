package org.lynnbit.tool.todolist.ui;

import static org.lynnbit.tool.todolist.ui.Constant.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

public class TaskPane {

    private static final List<Color> COLORS = Lists.newArrayList(Color.web("#f16d7a"), Color.web("#e27386"),
        Color.web("#f55066"), Color.web("#ef5464"), Color.web("#ae716e"), Color.web("#cb8e85"), Color.web("#cf8878"),
        Color.web("#c86f67"), Color.web("#f1ccb8"), Color.web("#f2debd"), Color.web("#b7d28d"), Color.web("#dcff93"),
        Color.web("#ff9b6a"), Color.web("#f1b8e4"), Color.web("#d9b8f1"), Color.web("#f1ccb8"), Color.web("#f1f1b8"),
        Color.web("#b8f1ed"), Color.web("#b8f1cc"), Color.web("#e7dac9"), Color.web("#e1622f"), Color.web("#f3d64e"),
        Color.web("#fd7d36"), Color.web("#fe9778"), Color.web("#c38e9e"), Color.web("#f28860"), Color.web("#de772c"),
        Color.web("#e96a25"), Color.web("#ca7497"), Color.web("#e29e4b"), Color.web("#edbf2b"), Color.web("#fecf45"),
        Color.web("#f9b747"), Color.web("#c17e61"), Color.web("#ed9678"), Color.web("#ffe543"), Color.web("#e37c5b"),
        Color.web("#ff8240"), Color.web("#aa5b71"), Color.web("#f0b631"), Color.web("#cf8888"));

    public static Pane create(String content, String... labels) {
        content = addIntent(content);
        FlowPane taskPane = new FlowPane();
        Label task = new Label(content);
        task.setFont(new Font("微软雅黑", TASK_FONT_SIZE));
        task.setMaxWidth(MAX_TASK_LABEL_WIDTH);
        task.setPrefWidth(MAX_TASK_LABEL_WIDTH);
        task.setWrapText(true);

        taskPane.getChildren().add(task);
        taskPane.setPickOnBounds(true);

        FlowPane labelPane = new FlowPane();

        for (String labelName : labels) {
            final Label label = createLabel(labelName);

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
        taskPane.getChildren().add(labelPane);

        taskPane.setPrefHeight(transferHeight(content, TASK_FONT_SIZE, MAX_TASK_LABEL_WIDTH));
        return taskPane;
    }

    private static String addIntent(String content) {
        return "  " + content;
    }

    private static Node createLine() {
        Line line = new Line(0, 0, MAX_TASK_LABEL_WIDTH + MAX_LABEL_LABEL_WIDTH, 0);
        line.setStroke(Color.GREY);

        return line;
    }

    private static double transferHeight(String content, int taskFontSize, int maxTaskLabelWidth) {
        int rows = (content.length() * taskFontSize / maxTaskLabelWidth) + 1;
        return rows * taskFontSize + (rows - 1) * 7 + 10;
    }

    private static Label createLabel(String labelName) {
        Label label = new Label(transferContent(labelName, LABEL_FONT_SIZE, MAX_LABEL_LABEL_WIDTH));
        label.setFont(new Font("微软雅黑", LABEL_FONT_SIZE));
        label.setBackground(new Background(
            new BackgroundFill(COLORS.get(RandomUtils.nextInt() % COLORS.size()), new CornerRadii(3), null)));

        return label;
    }

    private static String transferContent(String content, int fontSize, int maxWidth) {
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
