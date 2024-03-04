package com.dcx.spring6.factory;

import com.dcx.spring6.bean.User;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestFactory {
    @Test
    public void test01(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("factory.xml");
        User user = (User) ac.getBean("user");
        System.out.println(user);
    }
}
