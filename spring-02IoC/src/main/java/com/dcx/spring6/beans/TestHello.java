package com.dcx.spring6.beans;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author dawn
 * @ @date 2024/2/29 22:56
 */
public class TestHello {


        private Logger logger = LoggerFactory.getLogger(TestHello.class);


        @Test
        public void testHelloWorld01(){
            //##### 根据id获取
            //
            //由于 id 属性指定了 bean 的唯一标识，所以根据 bean 标签的 id 属性可以精确获取到一个组件对象。上个实验中我们使用的就是这种方式。
            ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
            Hello bean = (Hello) ac.getBean("hello");
            bean.sayHello();
            logger.info("hi");
        }
        @Test
        public void testHelloWorld02(){
            //根据类型获取
            ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
            Hello bean = ac.getBean(Hello.class);
            bean.sayHello();
        }
        @Test
        public void testHelloWorld03(){
            //根据类型和id
            ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
            Hello hello = (Hello) ac.getBean("hello",Hello.class);
            hello.sayHello();
            logger.info("执行成功");
        }


}
