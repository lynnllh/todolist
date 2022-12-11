package org.lynnbit.tool.todolist.ui.trayIcon;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.lynnbit.tool.todolist.core.domain.model.Label;
import org.lynnbit.tool.todolist.core.domain.model.Task;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ApplicationTrayIcon extends TrayIcon {
    private Stage stage = new Stage();
    private StackPane pane = new StackPane();
    private boolean isShowed;

    public ApplicationTrayIcon(Image image, String tooltip, Stage mainStage) {
        super(image, tooltip);
        // 设置系统托盘图标为自适应
        this.setImageAutoSize(true);
        // 添加组件到面板中
        initStage();

        initLayout(mainStage);

        initAction();
    }

    public void disPlayMessageInFirstTime() {
        if (isShowed) {
            return;
        }
        displayMessage("通知", "to do list已隐藏于系统托盘", TrayIcon.MessageType.INFO);
        isShowed = true;
    }

    private void initAction() {
        // 添加鼠标事件
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // getButton() 1左键 2中键 3右键
                if (e.getButton() == 3) {
                    // 在JavaFx的主线程中调用javaFx组件的方法
                    Platform.runLater(() -> {
                        // 设置dialog的显示位置
                        stage.setX(e.getX() - (stage.getWidth() / 2));
                        stage.setY(e.getY() - stage.getHeight());
                        // 设置为顶层，否则在windows系统中会被底部任务栏遮挡
                        stage.setAlwaysOnTop(true);
                        // 展示/隐藏
                        if (!stage.isShowing()) {
                            stage.show();
                        } else {
                            stage.hide();
                        }
                    });
                }
            }
        });
    }

    private void initStage() {
        stage.setScene(new Scene(pane));
        // 去掉面板的标题栏
        stage.initStyle(StageStyle.TRANSPARENT);
        // 监听面板焦点
        stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                // 如果失去焦点就隐藏面板
                stage.hide();
            }
        });
    }

    private void initLayout(final Stage mainStage) {
        VBox vBox = new VBox();
        javafx.scene.control.Button showButton = new javafx.scene.control.Button("显示");
        showButton.setOnMouseClicked(e -> {
            Platform.runLater(() -> mainStage.show());
        });
        showButton.setPrefWidth(100);
        showButton.setPrefHeight(20);

        javafx.scene.control.Button exitButton = new javafx.scene.control.Button("退出");
        exitButton.setOnMouseClicked(e -> {
            Task.saveTask();
            Label.saveLabel();
            Platform.exit();
            System.exit(0);
        });
        exitButton.setPrefWidth(100);
        exitButton.setPrefHeight(20);

        vBox.getChildren().addAll(showButton, exitButton);
        vBox.setPrefWidth(100);
        vBox.setPrefHeight(40);

        pane.getChildren().add(vBox);
        // 设置面板的宽高
        stage.setWidth(vBox.getPrefWidth());
        stage.setHeight(vBox.getPrefHeight());
    }
}
