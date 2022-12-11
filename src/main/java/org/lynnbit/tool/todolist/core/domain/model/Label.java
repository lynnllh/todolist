package org.lynnbit.tool.todolist.core.domain.model;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.lynnbit.tool.todolist.core.infrastructure.persistent.LabelRepositoryImpl;

import com.google.common.collect.Lists;

import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Label {
    private static final List<String> COLORS = Lists.newArrayList("#f16d7a", "#e27386", "#f55066", "#ef5464", "#ae716e",
        "#cb8e85", "#cf8878", "#c86f67", "#f1ccb8", "#f2debd", "#b7d28d", "#dcff93", "#ff9b6a", "#f1b8e4", "#d9b8f1",
        "#f1ccb8", "#f1f1b8", "#b8f1ed", "#b8f1cc", "#e7dac9", "#e1622f", "#f3d64e", "#fd7d36", "#fe9778", "#c38e9e",
        "#f28860", "#de772c", "#e96a25", "#ca7497", "#e29e4b", "#edbf2b", "#fecf45", "#f9b747", "#c17e61", "#ed9678",
        "#ffe543", "#e37c5b", "#ff8240", "#aa5b71", "#f0b631", "#cf8888");

    private static LabelRepository labelRepository = LabelRepositoryImpl.getInstance();

    String name;

    Color color;

    public Label(String name) {
        this.name = name;
        color = Color.web(COLORS.get(RandomUtils.nextInt() % COLORS.size()));
    }

    public static Label getOrCreateLabel(String name) {
        if (labelRepository.exist(name)) {
            return labelRepository.getLabel(name);
        }
        Label label = new Label(name);
        labelRepository.addNewLabel(label);
        return label;
    }

    public static List<String> searchLabels(String str) {
        return labelRepository.searchLabelName(str);
    }

    public static void loadLabel() {
        try {
            labelRepository.loadLabel();
        } catch (IOException e) {
            log.error("load label failed.", e);
        }
    }

    public static void saveLabel() {
        try {
            labelRepository.saveLabel();
        } catch (IOException e) {
            log.error("save label failed.", e);
        }
    }

    public String getName() {
        return name;
    }
}
