package com.dcx.spring6.bean;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.logging.Logger;

public class TestStudent {

    //利用setter注入
    @Test
    public void testStudent(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-di.xml");
        Student student = (Student) ac.getBean(Student.class);
        System.out.println(student.toString());

    }
    //利用构造器
    @Test
    public void testStudent02(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-di.xml");
        Student student = (Student) ac.getBean(Student.class);
        System.out.println(student.toString());
    }


//    为对象类型属性赋值
    //引用外部bean为对象赋值
    @Test
    public void testStudent03(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-di.xml");
        Student student = (Student) ac.getBean("studentThree",Student.class);
        System.out.println(student.toString());
    }
    //引用内部bean
    @Test
    public void testStudent04(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-di.xml");
        Student student = (Student) ac.getBean("studentFour");
        System.out.println(student.toString());
    }
    //级联属性赋值
    @Test
    public void testStudent05(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("Spring-di.xml");
        Student student = (Student) ac.getBean("studentFive",Student.class);
        System.out.println(student.toString());
    }
    //为Map赋值
    @Test
    public void testStudent06(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("Spring-di.xml");
        Student student = (Student) ac.getBean("studentSix",Student.class);
        System.out.println(student.toString());
    }
    //对象的集合
    @Test
    public void testStudent07(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("Spring-di.xml");
        Student student = (Student) ac.getBean("studentSeven",Student.class);
        System.out.println(student.toString());
    }
}

