package com.springboot_yx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GenerationController {

    @RequestMapping("/generation")
    public String generation(){
        return "simple/generation";
    }
}
