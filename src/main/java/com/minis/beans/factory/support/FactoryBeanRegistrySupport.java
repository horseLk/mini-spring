package com.minis.beans.factory.support;

import com.minis.beans.BeansException;
import com.minis.beans.factory.FactoryBean;

public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {
    protected Class<?> getTypeForFactoryBean(final FactoryBean<?> factoryBean) {
        return factoryBean.getObjectType();
    }

    protected Object getObjectFromFactoryBean(FactoryBean<?> factoryBean, String beanName) {
        Object object = doGetObjectFromFactoryBean(factoryBean, beanName);
        try {
            object = postProcessObjectFromFactoryBean(object, beanName);
        } catch (BeansException e) {
            e.printStackTrace();
        }
        return object;
    }

    private Object doGetObjectFromFactoryBean(final FactoryBean<?> factoryBean, String beanName) {
        Object obj = null;
        try {
            obj = factoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    protected Object postProcessObjectFromFactoryBean(Object object, String beanName) throws BeansException {
        return object;
    }
}
