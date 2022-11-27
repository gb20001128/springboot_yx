package com.springboot_yx.controller;

import com.springboot_yx.bean.Love;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Controller
public class MusicController {

    @RequestMapping("/music")
    public String music(){
        return "simple/music";
    }



    @RequestMapping("/test")
    public String test(){
        return "show/test";
    }


    @RequestMapping("/test2")
    public String test2(){

        return "show/test2";
    }

}
