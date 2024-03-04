package com.dcx.spring6.autowire.dao.impl;

import com.dcx.spring6.autowire.dao.UserDao;

public class UserDaoImpl implements UserDao {
    @Override
    public void saveUser() {
        System.out.println("save success");
    }
}
