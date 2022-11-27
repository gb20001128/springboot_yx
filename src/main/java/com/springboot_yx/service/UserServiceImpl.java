package com.springboot_yx.service;

import com.springboot_yx.bean.User;
import com.springboot_yx.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{


    @Autowired
    UserMapper userMapper;

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public User ifExist(String userName) {
        User user = userMapper.ifExist(userName);
        return user;

    }

    @Override
    public int deleteUser(String userName){
        return userMapper.deleteUser(userName);
    }

    @Override
    public int updateName(String oldName, String newName) {
        return userMapper.updateName(oldName,newName);
    }

    @Override
    public int updatePassWord(String userName, String passWord) {
        return userMapper.updatePassWord(userName,passWord);
    }
}
