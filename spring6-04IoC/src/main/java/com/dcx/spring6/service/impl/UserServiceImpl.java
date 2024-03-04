package com.dcx.spring6.service.impl;


import com.dcx.spring6.annotation.Bean;
import com.dcx.spring6.annotation.DI;
import com.dcx.spring6.dao.UserDao;
import com.dcx.spring6.service.UserService;
@Bean
public class UserServiceImpl implements UserService {
    @DI
    private UserDao userDao;

    @Override
    public void saveUser() {

        System.out.println("SERVICE");
       // userDao.saveUser();
    }
}
