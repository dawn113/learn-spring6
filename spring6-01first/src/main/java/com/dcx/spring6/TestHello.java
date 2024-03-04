package com.dcx.spring6;
/**
 * @author dawn
 * @ @date 2024/2/29 22:46
 */
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestHello {
    //创建logger对象
    private Logger logger = LoggerFactory.getLogger(TestHello.class);
    @Test
    public void test(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        Bean bean = (Bean) ac.getBean("hello");
        bean.HelloWorld();
        logger.info("hi");
    }
}
