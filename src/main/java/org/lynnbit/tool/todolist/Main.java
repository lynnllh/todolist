package org.lynnbit.tool.todolist;

import static org.lynnbit.tool.todolist.ui.Constant.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

import org.apache.commons.lang3.StringUtils;
import org.lynnbit.tool.todolist.core.domain.model.Task;
import org.lynnbit.tool.todolist.ui.Command;
import org.lynnbit.tool.todolist.ui.TaskPane;
import org.lynnbit.tool.todolist.ui.trayIcon.TTrayIcon;

import com.tulskiy.keymaster.common.Provider;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;

/**
 * 先把后端的能力补齐 然后再实现前端的功能 然后再优化整体架构
 */
@Slf4j
public class Main extends Application {

    private boolean firstTime;
    private TrayIcon trayIcon;

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) {
        log.info("heee");
        initData();
        initHostKey(stage);
        initStage(stage);
        initAction(stage);
        initTrayIcon(stage);
        VBox vBox = initLayout();
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
    }

    private VBox initLayout() {
        VBox vBox = new VBox();
        TextField textField = new TextField();
        textField.setPrefWidth(TEXT_FIELD_WIDTH);
        textField.setPrefHeight(TEXT_FIELD_HEIGHT);
        textField.setFont(new Font("微软雅黑", TASK_FONT_SIZE));
        textField.setOnAction(event -> {
            String content = textField.getText();
            textField.clear();
            consumeContent(content, vBox);

        });
        vBox.getChildren().add(textField);
        vBox.getChildren().addAll(getUnfinishedTask());
        return vBox;
    }

    private void initAction(Stage stage) {
        // 监听面板焦点
        stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                stage.hide();
                showProgramIsMinimizedMsg();
            }
        });
    }

    private void initStage(Stage stage) {
        stage.initStyle(StageStyle.TRANSPARENT);
        Platform.setImplicitExit(false);
    }

    private void initData() {
        Task.loadTask();
        firstTime = true;
    }

    private Collection<? extends Node> getUnfinishedTask() {
        List<Pane> ret = new ArrayList<>();
        for (Task task : Task.getUnfinishedTask()) {
            ret.add(TaskPane.create(task.getContent(), task.getLabelNames()));
        }
        return ret;
    }

    private void consumeContent(String raw, Pane pane) {
        String[] list = raw.split(" ");
        if (list.length <= 1) {
            return;
        }

        String commandStr = list[0];
        Command command = Command.getCommand(commandStr);
        if (Objects.isNull(command)) {
            return;
        }

        List<String> labelList = new ArrayList<>();
        List<String> contentList = new ArrayList<>();
        for (int i = 1; i < list.length; i++) {
            if (list[i].startsWith("#")) {
                labelList.add(list[i].substring(1));
                continue;
            }
            contentList.add(list[i]);
        }

        if (Command.ADD_TASK.equals(command)) {
            Task.createTask(StringUtils.join(contentList, " "), labelList.toArray(new String[0]));
            refreshTaskList(pane);
        }

    }

    private void refreshTaskList(Pane pane) {
        pane.getChildren().removeIf(node -> {
            if (node instanceof FlowPane) {
                return true;
            }

            return false;
        });
        pane.getChildren().addAll(getUnfinishedTask());
        pane.getScene().getWindow().sizeToScene();
    }

    private void initHostKey(Stage stage) {
        Provider provider = Provider.getCurrentProvider(true);
        provider.register(KeyStroke.getKeyStroke("control X"), hotKey -> Platform.runLater(() -> stage.show()));
    }

    public void initTrayIcon(final Stage stage) {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();

            Image image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("tray.png"));

            VBox vBox = new VBox();
            javafx.scene.control.Button showButton = new javafx.scene.control.Button("显示");
            showButton.setOnMouseClicked(e -> {
                Platform.runLater(() -> stage.show());
            });
            showButton.setPrefWidth(100);
            showButton.setPrefHeight(20);

            javafx.scene.control.Button exitButton = new javafx.scene.control.Button("退出");
            exitButton.setOnMouseClicked(e -> {
                Task.saveTask();
                Platform.exit();
                System.exit(0);
            });
            exitButton.setPrefWidth(100);
            exitButton.setPrefHeight(20);

            vBox.getChildren().addAll(showButton, exitButton);
            vBox.setPrefWidth(100);
            vBox.setPrefHeight(40);

            trayIcon = new TTrayIcon(image, "to do list", vBox);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                log.error("init tray failed, the detail message: ", e);
            }
        }
    }

    public void showProgramIsMinimizedMsg() {
        if (firstTime) {
            trayIcon.displayMessage("通知", "to do list已隐藏于系统托盘", TrayIcon.MessageType.INFO);
            firstTime = false;
        }
    }
}
