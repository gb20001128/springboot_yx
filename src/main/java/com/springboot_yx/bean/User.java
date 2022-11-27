package com.springboot_yx.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @TableId(type = IdType.AUTO)
    private  Integer id;

    private String  userName;

    private String passWord;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userName.equals(user.userName) && passWord.equals(user.passWord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, passWord);
    }
}
