package com.dcx.spring6;

import com.dcx.spring6.bean.ApplicationContext;
import com.dcx.spring6.bean.impl.ApplicationContextImpl;
import com.dcx.spring6.service.UserService;

/**
 * @author dawn
 * @ @date 2024/3/4 22:29
 */
public class TestUser {
    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContextImpl("com.dcx");
        UserService userService = (UserService) context.getBean(UserService.class);
        System.out.println(userService);
        userService.saveUser();

    }
}
