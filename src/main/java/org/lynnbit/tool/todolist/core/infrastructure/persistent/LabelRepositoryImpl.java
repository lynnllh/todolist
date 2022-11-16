package org.lynnbit.tool.todolist.core.infrastructure.persistent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;

import com.google.common.collect.Lists;

public class LabelRepositoryImpl {
    private static final List<String> COLORS = Lists.newArrayList("#f16d7a", "#e27386", "#f55066", "#ef5464", "#ae716e",
        "#cb8e85", "#cf8878", "#c86f67", "#f1ccb8", "#f2debd", "#b7d28d", "#dcff93", "#ff9b6a", "#f1b8e4", "#d9b8f1",
        "#f1ccb8", "#f1f1b8", "#b8f1ed", "#b8f1cc", "#e7dac9", "#e1622f", "#f3d64e", "#fd7d36", "#fe9778", "#c38e9e",
        "#f28860", "#de772c", "#e96a25", "#ca7497", "#e29e4b", "#edbf2b", "#fecf45", "#f9b747", "#c17e61", "#ed9678",
        "#ffe543", "#e37c5b", "#ff8240", "#aa5b71", "#f0b631", "#cf8888");

    private static Map<String, String> label2ColorMap = new HashMap<>();

    public String getColor(String name) {
        if (label2ColorMap.containsKey(name)) {
            return label2ColorMap.get(name);
        }
        String color = COLORS.get(RandomUtils.nextInt() % COLORS.size());
        label2ColorMap.put(name, color);
        return color;
    }
}
