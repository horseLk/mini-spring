package com.minis.beans.factory;

import com.minis.beans.BeansException;
import com.minis.beans.factory.config.BeanDefinition;

// Bean容器接口
public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;
    Boolean containsBean(String name);
    boolean isSingleton(String name);
    boolean isPrototype(String name);
    Class<?> getType(String name);
}
