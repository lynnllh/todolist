package org.lynnbit.tool.todolist.core.application;

import java.util.Objects;

import org.lynnbit.tool.todolist.core.domain.model.Label;

public class LabelService {
    private LabelService() {}

    private static LabelService labelService;

    public static LabelService getInstance() {
        if (Objects.isNull(labelService)) {
            labelService = new LabelService();
        }

        return labelService;
    }

    public Label createLabel(String name) {
        return new Label(name);
    }
}
