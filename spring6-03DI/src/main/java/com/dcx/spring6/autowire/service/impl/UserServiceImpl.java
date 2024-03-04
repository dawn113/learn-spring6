package com.dcx.spring6.autowire.service.impl;

import com.dcx.spring6.autowire.dao.UserDao;
import com.dcx.spring6.autowire.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao;
    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }
    @Override
    public void saveUser() {
     userDao.saveUser();
    }
}
