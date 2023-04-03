package com.minis.beans.factory.config;

import com.minis.beans.factory.ListableBeanFactory;

// 组合 ListableBeanFactory（将bean用集合管理）AutowireCapableBeanFactory（支持bean注解）和ConfigurableBeanFactory（维护bean依赖关系和支持BeanPostProcessor)
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
}
