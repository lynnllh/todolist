package org.lynnbit.tool.todolist.ui;

public enum Command {
    ADD_TASK("t"), DO_TASK("d"), FINISH_TASK("f"), LIST_TASK("l");

    private String code;

    Command(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Command getCommand(String content) {
        content = content.toLowerCase().trim();
        for (Command command : values()) {
            if (command.getCode().equals(content)) {
                return command;
            }
        }
        return null;
    }
}
