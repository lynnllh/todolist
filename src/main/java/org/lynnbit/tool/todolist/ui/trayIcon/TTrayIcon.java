package org.lynnbit.tool.todolist.ui.trayIcon;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TTrayIcon extends TrayIcon {
    private Stage stage = new Stage();
    private StackPane pane = new StackPane();

    public TTrayIcon(Image image, String tooltip, Region menu) {
        super(image, tooltip);
        // 设置系统托盘图标为自适应
        this.setImageAutoSize(true);
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
        // 添加组件到面板中
        pane.getChildren().add(menu);
        // 设置面板的宽高
        stage.setWidth(menu.getPrefWidth());
        stage.setHeight(menu.getPrefHeight());
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
}
