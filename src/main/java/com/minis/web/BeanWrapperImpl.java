package com.minis.web;

import com.minis.beans.PropertyEditor;
import com.minis.beans.PropertyEditorRegistrySupport;
import com.minis.beans.PropertyValue;
import com.minis.beans.PropertyValues;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BeanWrapperImpl extends PropertyEditorRegistrySupport {
    Object wrappedObject; //目标对象
    Class<?> clz;
    PropertyValues pvs; //参数值

    public BeanWrapperImpl(Object object) {
        registerDefaultEditors();
        this.wrappedObject = object;
        this.clz = object.getClass();
    }

    public void setBeanInstance(Object object) {
        this.wrappedObject = object;
    }

    public Object getBeanInstance() {
        return this.wrappedObject;
    }

    // 绑定参数值
    public void setPropertyValues(PropertyValues pvs) {
        this.pvs = pvs;
        for (PropertyValue pv : this.pvs.getPropertyValues()) {
            setPropertyValue(pv);
        }
    }

    // 绑定某个具体参数
    private void setPropertyValue(PropertyValue pv) {
        // 获取参数处理器
        BeanPropertyHandler propertyHandler = new BeanPropertyHandler(pv.getName());
        // 找到对该参数类型的editor
        PropertyEditor pe = this.getCustomEditor(propertyHandler.getPropertyClz());
        if (pe == null) {
            pe = this.getDefaultEditor(propertyHandler.getPropertyClz());
        }
        // 设置参数值
        pe.setAsText((String) pv.getValue());
        propertyHandler.setValue(pe.getValue());
    }

    // 一个内部类，用于处理参数，通过getter()和setter()操作属性
    class BeanPropertyHandler{
        Method writeMethod = null;
        Method readMethod = null;
        Class<?> propertyClz = null;

        public Class<?> getPropertyClz() {
            return propertyClz;
        }

        public BeanPropertyHandler(String propertyName) {
            try {
                //获取参数对应的属性及类型
                Field field = clz.getDeclaredField(propertyName);
                propertyClz = field.getType();
                //获取设置属性的方法，按照约定为setXxxx（）
                this.writeMethod = clz.getDeclaredMethod("set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1), propertyClz);
                this.readMethod = clz.getDeclaredMethod("get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Object getValue() {
            Object result = null;
            readMethod.setAccessible(true);
            try {
                result = readMethod.invoke(wrappedObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        public void setValue(Object value) {
            writeMethod.setAccessible(true);
            try {
                writeMethod.invoke(wrappedObject, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
