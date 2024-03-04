package com.dcx.spring6.dao.impl;


import com.dcx.spring6.annotation.Bean;
import com.dcx.spring6.dao.UserDao;

@Bean
public class UserDaoImpl implements UserDao {
    @Override
    public void saveUser() {
        System.out.println("DAO success");
    }
}
