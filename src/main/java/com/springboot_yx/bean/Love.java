package com.springboot_yx.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Love {

    @TableId(type = IdType.AUTO)
    Integer id;

    String userName;

    String headImage;

    String sex;

    String age;

    //理性与感性
    String lw;

    String characters;

    String talent;

    //联系方式
    String mode;

    //号码
    String number;


}
