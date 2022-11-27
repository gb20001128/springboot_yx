package com.springboot_yx.service;

import com.springboot_yx.bean.User;

public interface UserService {


    int addUser(User user);

    //判断是否存在用户
    User ifExist(String userName);

    //用不着了
    int deleteUser(String userName);

    //修改用户昵称
    int updateName(String oldName,String newName);

    //修改密码
    int  updatePassWord(String userName,String passWord);

}
