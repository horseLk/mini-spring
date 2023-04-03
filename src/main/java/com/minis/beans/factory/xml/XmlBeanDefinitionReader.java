package com.minis.beans.factory.xml;

import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.config.AutowireCapableBeanFactory;
import com.minis.beans.factory.config.ConstructorArgumentValue;
import com.minis.beans.factory.config.ConstructorArgumentValues;
import com.minis.beans.PropertyValues;
import com.minis.beans.factory.config.BeanDefinition;
import com.minis.beans.factory.support.AbstractBeanFactory;
import com.minis.core.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

// XmlBeanDefinitionReader 负责将内存中的xml数据解析为BeanDefinition并注册到 BeanFactory
public class XmlBeanDefinitionReader {
    AbstractBeanFactory beanFactory;

    public XmlBeanDefinitionReader(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);

            // 处理属性
            List<Element> propertyElement = element.elements("property");
            PropertyValues pvs = new PropertyValues();
            List<String> refs = new ArrayList<>();
            for (Element e : propertyElement) {
                String type = e.attributeValue("type");
                String value = e.attributeValue("value");
                String name = e.attributeValue("name");
                String ref = e.attributeValue("ref");
                String pV = "";
                boolean isRef = false;
                if (value != null && !value.equals("")) {
                    isRef = false;
                    pV = value;
                } else if (ref != null && !ref.equals("")) {
                    isRef = true;
                    pV = ref;
                    refs.add(ref);
                }
                pvs.addPropertyValue(type, name, pV, isRef);
            }
            beanDefinition.setPropertyValues(pvs);

            String[] refArray = refs.toArray(new String[0]);
            beanDefinition.setDependsOn(refArray);

            List<Element> constructorElements = element.elements("constructor-arg");
            ConstructorArgumentValues avs = new ConstructorArgumentValues();
            for (Element e : constructorElements) {
                String type = e.attributeValue("type");
                String value = e.attributeValue("value");
                String name = e.attributeValue("name");

                avs.addArgumentValue(new ConstructorArgumentValue(type, name, value));
            }
            beanDefinition.setConstructorArgumentValues(avs);

            this.beanFactory.registerBeanDefinition(beanID, beanDefinition);
        }
    }
}
