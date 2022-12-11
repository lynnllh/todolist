package org.lynnbit.tool.todolist.core.infrastructure.persistent;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.lynnbit.tool.todolist.core.domain.model.Label;
import org.lynnbit.tool.todolist.core.domain.model.LabelRepository;

import com.alibaba.fastjson.JSON;

public class LabelRepositoryImpl implements LabelRepository {
    private static final String LABEL_FILE_NAME = "label.json";
    private static final String PROJECT_DIR = "\\todolist\\";
    private static String USER_HOME = System.getProperty("user.home");

    private static Map<String, Label> labelMap = new LinkedHashMap<>(16, 0.75f, true);

    private static LabelRepositoryImpl instance = new LabelRepositoryImpl();

    private LabelRepositoryImpl() {}

    public static LabelRepository getInstance() {
        return instance;
    }

    @Override
    public boolean exist(String name) {
        return labelMap.containsKey(name);
    }

    @Override
    public Label getLabel(String name) {
        return labelMap.get(name);
    }

    @Override
    public void addNewLabel(Label label) {
        labelMap.put(label.getName(), label);
    }

    /**
     * 搜索关键词 并按照访问顺序进行排序，最近访问的排在前面
     * 
     * @param key
     * @return
     */
    @Override
    public List<String> searchLabelName(String key) {
        List<String> labelList =
            labelMap.keySet().stream().filter(str -> str.contains(key)).collect(Collectors.toList());
        Collections.reverse(labelList);
        return labelList;
    }

    @Override
    public void saveLabel() throws IOException {
        FileUtils.write(new File(USER_HOME + PROJECT_DIR + LABEL_FILE_NAME), JSON.toJSONString(labelMap.values()),
            Charset.forName("UTF-8"));
    }

    @Override
    public void loadLabel() throws IOException {
        File labelFile = new File(USER_HOME + PROJECT_DIR + LABEL_FILE_NAME);
        if (labelFile.exists()) {
            String labelStr = FileUtils.readFileToString(labelFile, Charset.forName("UTF-8"));
            List<Label> labels = JSON.parseArray(labelStr, Label.class);
            labelMap =
                new LinkedHashMap<>(labels.stream().collect(Collectors.toMap(Label::getName, Function.identity())));
        }
    }

}
