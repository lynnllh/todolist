package org.lynnbit.tool.todolist.core.domain.model;

import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Label {
    String name;

    Color color;

    public Label(String name) {
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
