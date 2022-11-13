package org.lynnbit.tool.todolist.core.domain.model;

import javafx.scene.paint.Color;

public class Label {
    String name;

    Color color;

    private Label(String name) {
        this.name = name;
    }

    public static Label create(String name) {
        Label label = new Label(name);
        return label;
    }

    public String getName() {
        return name;
    }
}
