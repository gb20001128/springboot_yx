package com.springboot_yx.controller;

import com.springboot_yx.bean.ShowLove;
import com.springboot_yx.service.ShowLoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@Controller
public class MangerController {



    @Autowired
    ShowLoveService showLoveService;

    @Value("${file.pathLove}")
    String Base_Path;

    @RequestMapping("/manger/root")
    public String manger(HttpSession session, Model model){


            List<ShowLove> showLoves = showLoveService.selectLoveALL();

            model.addAttribute("gbs",showLoves);
            return "manger";

    }

    //管理员删除
    @RequestMapping("/deleteManger/{imageName}")
    public String deleteHistory(@PathVariable("imageName")  String imageName, Model model){

        //删除图片在本机中的位置
        //File file = new File("F:\\apache-tomcat-8.0.50\\webapps\\love\\"+imageName);
        //File file = new File("/www/server/tomcat/webapps/love/"+imageName);
        File file = new File(Base_Path+imageName);
        file.delete();

        //删除数据表对应的字段
        showLoveService.delete(imageName);

        return "redirect:/manger/root";
    }


}
