package com.dcx.spring6.autowire;

import com.dcx.spring6.autowire.service.UserService;

public class UserController {
    private UserService userService;
    public void setUserService(UserService userService){
        this.userService = userService;
    }
    public void saveUser(){
        userService.saveUser();
    }
}
