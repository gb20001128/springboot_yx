package com.springboot_yx;

import com.springboot_yx.bean.MicroVideo;
import com.springboot_yx.mapper.MicroVideoMapper;
import com.springboot_yx.service.MicroVideoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
class SpringbootYxApplicationTests {

    @Autowired
    MicroVideoMapper mapper;

    @Autowired
    MicroVideoService service;
    

    @Test
    void contextLoads() {

        boolean save = service.save(new MicroVideo(null, "英语", null,"2.mp4", 2));
        System.out.println(save);


        String ui = ui();
        System.out.println(ui);
        System.out.println("=============================");
        String u = ui();
        System.out.println(u);

    }





    @Cacheable("ca")
    public String ui(){
        System.out.println(1);
        return "121";
    }



}
