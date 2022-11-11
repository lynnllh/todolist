package org.lynnbit.tool.todolist.core.domain.model;

import java.util.List;

public interface LabelRepository {
    void addLabel(Label label);

    List<Label> searchLabels();
}
