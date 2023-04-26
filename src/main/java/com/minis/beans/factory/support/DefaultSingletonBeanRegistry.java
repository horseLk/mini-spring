package com.minis.beans.factory.support;

import com.minis.beans.factory.config.SingletonBeanRegistry;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    // beanNames 存放所有bean名称的列表
    protected List<String> beanNames = new ArrayList<>();
    // singletons 存放所有bean实例的map
    private Map<String, Object> singletons = new ConcurrentHashMap<>(256);
    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletons) {
            this.singletons.put(beanName, singletonObject);
            this.beanNames.add(beanName);
        }
    }

    public void registerDependentBean(String beanName, String dependentBeanName) {
//        Set<String> dependentBeans = this.dependentBeanMap.get(beanName);
//        if (dependentBeans != null && dependentBeans.contains(dependentBeanName)) {
//            return;
//        }
//
//        // No entry yet -> fully synchronized manipulation of the dependentBeans Set
//        synchronized (this.dependentBeanMap) {
//            dependentBeans = this.dependentBeanMap.get(beanName);
//            if (dependentBeans == null) {
//                dependentBeans = new LinkedHashSet<String>(8);
//                this.dependentBeanMap.put(beanName, dependentBeans);
//            }
//            dependentBeans.add(dependentBeanName);
//        }
//        synchronized (this.dependenciesForBeanMap) {
//            Set<String> dependenciesForBean = this.dependenciesForBeanMap.get(dependentBeanName);
//            if (dependenciesForBean == null) {
//                dependenciesForBean = new LinkedHashSet<String>(8);
//                this.dependenciesForBeanMap.put(dependentBeanName, dependenciesForBean);
//            }
//            dependenciesForBean.add(beanName);
//        }

    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletons.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return this.singletons.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return (String[])this.beanNames.toArray();
    }

    protected void removeSingleton(String beanName) {
        synchronized (this.singletons) {
            this.beanNames.remove(beanName);
            this.singletons.remove(beanName);
        }
    }
}
