package org.example;

import static org.junit.Assert.assertTrue;

import com.minis.beans.BeansException;
import com.minis.context.ClassPathXmlApplicationContext;
import com.minis.web.servlet.DispatcherServlet;
import org.example.domain.User;
import org.example.domain.UserClass;
import org.example.service.*;
import org.junit.Test;

import java.util.List;

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
            User user2 = userService.getUserInfo2(1);
            System.out.println(user2);
            List<User> users = userService.getUserInfos(1);
            System.out.println(users);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAop3() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        try {
            AopTestService aopTestService = (AopTestService) context.getBean("realService");
            aopTestService.doAction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAop4() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        try {
            AopTestService2 aopTestService = (AopTestService2) context.getBean("realService2");
            aopTestService.doAction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJdbcUpdate() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        try {
            UserService userService = (UserService) context.getBean("userService");
            userService.UpdateUserInfo(new User(1, "newName0503"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testJdbcUpdate2() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        try {
            UserService userService = (UserService) context.getBean("userService");
            int line = userService.UpdateUserInfo2(new User(1, "newName050511"));
            System.out.println(line);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdate3() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        try {
            UserClassService userClassService = (UserClassService) context.getBean("realUserClassService");
            int line = userClassService.doUpdateClass(new UserClass(1, "className0508"));
            System.out.println(line);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testTransaction() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        try {
            UserClassService userClassService = (UserClassService) context.getBean("realUserClassService");
            userClassService.doUpdateWithTransaction(new User(1, "userName0508-trans2"), new UserClass(1, "className0508-trans2"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
