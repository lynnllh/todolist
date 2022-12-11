package org.lynnbit.tool.todolist.core.domain.model;

import java.io.IOException;
import java.util.List;

public interface LabelRepository {

    void addNewLabel(Label label);

    boolean exist(String name);

    Label getLabel(String name);

    List<String> searchLabelName(String key);

    void saveLabel() throws IOException;

    void loadLabel() throws IOException;
}
