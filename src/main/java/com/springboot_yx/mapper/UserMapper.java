package com.springboot_yx.mapper;

import com.springboot_yx.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper //标注这是Mapper接口
public interface UserMapper {

    //添加用户
    int addUser(User user);

    //判断是否存在用户
    User ifExist(String userName);


    int deleteUser(String userName);

    //修改用户昵称
    int updateName(String oldName,String newName);

    //修改密码
    int  updatePassWord(String userName,String passWord);

}
