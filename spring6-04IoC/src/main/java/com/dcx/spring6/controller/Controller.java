package com.dcx.spring6.controller;

import com.dcx.spring6.annotation.Bean;
import com.dcx.spring6.annotation.DI;
import com.dcx.spring6.service.UserService;

/**
 * @author dawn
 * @ @date 2024/3/4 18:33
 */
@Bean
public class Controller {
    @DI
    private UserService userService;
    public void saveUser(){
        userService.saveUser();
    }
}
