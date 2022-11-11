package org.lynnbit.tool.todolist.core.domain.model;

public class Category {
    private String name;

    private Category(String name) {
        this.name = name;
    }

    public static Category createCategory(String name) {
        return new Category(name);
    }
}
