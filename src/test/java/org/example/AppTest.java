package org.example;

import static org.junit.Assert.assertTrue;

import com.minis.beans.BeansException;
import com.minis.context.ClassPathXmlApplicationContext;
import com.minis.web.DispatcherServlet;
import org.example.service.AService;
import org.example.service.BaseService;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void test1() {
        new DispatcherServlet();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        try {
            AService aService = (AService)context.getBean("aservice");
            aService.sayHello();
            BaseService baseService = (BaseService) context.getBean("baseService");
            baseService.go();
        } catch (BeansException e) {
            throw new RuntimeException(e);
        }
    }
}
