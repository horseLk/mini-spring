package com.minis.aop;

import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.BeanFactoryAware;
import com.minis.beans.factory.FactoryBean;
import com.minis.util.ClassUtils;

public class ProxyFactoryBean implements FactoryBean<Object>, BeanFactoryAware {
    private AopProxyFactory aopProxyFactory;

    private String[] interceptorNames;

    private String targetName;

    private Object target;

    private ClassLoader proxyClassLoader = ClassUtils.getDefaultClassLoader();

    private Object singletonInstance;

    private BeanFactory beanFactory;

    private String interceptorName;

    private PointcutAdvisor advisor;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public String getInterceptorName() {
        return interceptorName;
    }

    public void setInterceptorName(String interceptorName) {
        this.interceptorName = interceptorName;
    }

    public PointcutAdvisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(PointcutAdvisor advisor) {
        this.advisor = advisor;
    }

    public synchronized void initializeAdvisor() {
        Object advice = null;
        MethodInterceptor interceptor = null;
        try {
            advice =  this.beanFactory.getBean(this.interceptorName);
        } catch (BeansException e) {
            e.printStackTrace();
        }
        this.advisor = (PointcutAdvisor) advice;
    }

    public ProxyFactoryBean() {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

    public void setAopProxyFactory(AopProxyFactory aopProxyFactory) {
        this.aopProxyFactory = aopProxyFactory;
    }

    public AopProxyFactory getAopProxyFactory() {
        return this.aopProxyFactory;
    }

    protected AopProxy createAopProxy() {
        return getAopProxyFactory().createAopProxy(target, this.advisor);
    }

    public void setInterceptorNames(String... interceptorNames) {
        this.interceptorNames = interceptorNames;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public Object getObject() throws Exception {
        return getSingletonInstance();
    }

    private synchronized Object getSingletonInstance() {
        if (this.singletonInstance == null) {
            this.singletonInstance = getProxy(createAopProxy());
        }

        return this.singletonInstance;
    }

    protected Object getProxy(AopProxy aopProxy) {//生成代理对象
        return aopProxy.getProxy();
    }

    @Override
    public Class<?> getObjectType() {
        return this.target.getClass();
    }
}
