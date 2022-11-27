package com.springboot_yx.controller;


import com.springboot_yx.mapper.ShowLoveMapper;
import com.springboot_yx.service.LoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class Test1 {

    @Autowired
    LoveService loveService;

    @Autowired
    ShowLoveMapper showLoveMapper;

    @Autowired
    StringRedisTemplate redisTemplate;



    @PostMapping("/test12")
    public Integer test(@RequestBody MultipartFile photo,String imageDescribe,String ui){

        System.out.println(ui);
        System.out.println(imageDescribe);
        System.out.println(photo);
        //System.out.println(photo.getOriginalFilename());
        return 1;
    }

}