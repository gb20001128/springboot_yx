package com.springboot_yx.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author gb
 * @Data 2022/10/24 15:27
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("yx_microvideo")
public class MicroVideo {


    String id;

    String description;

    String doorImage;

    String microVideoName;

    int likeCount;
}
