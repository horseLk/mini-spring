package org.example;

import static org.junit.Assert.assertTrue;

import com.minis.beans.BeansException;
import com.minis.context.ClassPathXmlApplicationContext;
import com.minis.web.servlet.DispatcherServlet;
import org.example.domain.User;
import org.example.service.AService;
import org.example.service.BaseService;
import org.example.service.UserService;
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

    @Test
    public void testJdbc() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        try {
            UserService userService = (UserService) context.getBean("userService");
            User user = userService.getUserInfo(1);
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
