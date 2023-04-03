package com.minis.beans.factory.config;

import java.util.*;

public class ConstructorArgumentValues {
    private final Map<Integer, ConstructorArgumentValue> indexedArgumentValues = new HashMap<>(0);
    // 通用的构造方法参数列表
    private final List<ConstructorArgumentValue> constructorArgumentValueList = new LinkedList<>();

    public ConstructorArgumentValues() {
    }

    public void addArgumentValue(ConstructorArgumentValue constructorArgumentValue) {
        this.constructorArgumentValueList.add(constructorArgumentValue);
    }

    public ConstructorArgumentValue getIndexedArgumentValue(int index) {
        ConstructorArgumentValue constructorArgumentValue = this.constructorArgumentValueList.get(index);
        return constructorArgumentValue;
    }

    public int getArgumentCount() {
        return (this.constructorArgumentValueList.size());
    }

    public boolean isEmpty() {
        return (this.constructorArgumentValueList.isEmpty());
    }
}
