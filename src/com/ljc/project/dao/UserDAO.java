package com.ljc.project.dao;

import com.ljc.project.bean.User;

import java.util.List;

public interface UserDAO {

    /**
     * @Description 根据用户名登录
     * @return 用户存在返回这个user对象，不存在返回Null
     */
    User login(String userName,String userPass);

    /**
     * @Description 根据userId查询用户
     */
    User findUserById(int userId);

    /**
     * @Description 注册用户
     * @param userName
     * @param userPass
     * @return
     */
    int addUser(String userName,String userPass);

}
