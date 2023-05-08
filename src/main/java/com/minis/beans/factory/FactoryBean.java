package com.minis.beans.factory;

public interface FactoryBean<T> {
    T getObject();
    Class<?> getObjectType();
    default boolean isSingleton() {
        return true;
    }
}
