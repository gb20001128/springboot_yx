package com.springboot_yx.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowLove {


    @TableId(type = IdType.AUTO)
    private Integer id;

    private String userName;

    private String headImage="12";

    private String declaration;

    private String loveImage;


}
