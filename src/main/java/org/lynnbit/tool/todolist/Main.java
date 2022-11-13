package org.lynnbit.tool.todolist;

import org.lynnbit.tool.todolist.ui.TaskPane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Pane root = FXMLLoader.load(getClass().getClassLoader().getResource("todolist.fxml"));
        // root.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        // Scene scene = new Scene(root);
        // stage.setScene(scene);
        // stage.show();

        VBox vBox = new VBox();
        vBox.getChildren().add(TaskPane.create("今天完成变更算法JFK发放啊离开房间啊发阿斯弗", "维优", "开发交流空间", "维优"));
        vBox.getChildren().add(TaskPane.create("今天完成开发", "开发kaifa kf", "维优"));
        vBox.getChildren().add(TaskPane.create("发生看哈法案", "开发kaifa kf", "维优莱卡风尚", "开发"));
        Scene scene = new Scene(vBox, 500, 500);
        stage.setScene(scene);

        stage.show();
    }
}
