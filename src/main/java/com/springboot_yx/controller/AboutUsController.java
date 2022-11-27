package com.springboot_yx.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AboutUsController {

    @RequestMapping("/aboutus")
    public String aboutus(){
        return "simple/aboutus";
    }
}
