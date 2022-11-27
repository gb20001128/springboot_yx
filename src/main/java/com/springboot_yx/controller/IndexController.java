package com.springboot_yx.controller;


import com.springboot_yx.bean.User;
import com.springboot_yx.service.UserService;
import com.springboot_yx.utils.HttpClient;
import com.springboot_yx.utils.QueryCityByIp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class IndexController {

    @Autowired
    UserService userService;

    @Autowired
    StringRedisTemplate redisTemplate;


/*
    //登录页、默认页
    @RequestMapping(value = {"/index","/"})
    public String index(){
        return "init/index";
    }*/

    @RequestMapping(value = {"/index","/"})
    public String index(HttpSession session, HttpServletRequest request){

        //打访问日志
        String remoteAddr = request.getRemoteAddr();

        String addr = QueryCityByIp.queryAddrByIp(remoteAddr);


        FileOutputStream file= null;
        try {
            file = new FileOutputStream("/Access_Log.txt",true);

            Date date=new Date();
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss ");
            String formatDate = formatter.format(date);

            byte[] bytes = (formatDate +"\t"+remoteAddr+"\t"+addr+"\n").getBytes();
            file.write(bytes);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.increment("countAccess");


        Object userName = session.getAttribute("userName");

        if (userName==null)
            session.setAttribute("My_headImage","logo.png");
        return "init/main";
    }


    @RequestMapping("/denglu")
    public String denglu(){
        return "init/index";
    }







    //注册
    @RequestMapping("/regist")
    public String regist(){
        return "init/regist";
    }


    //利用ajax判断注册页输入的用户名是否存在(保证用户名不重)
    @GetMapping("/testaj/{userName}")
    @ResponseBody
    public boolean doTest(@PathVariable("userName") String userName) {
        System.out.println(userName);

        User user = userService.ifExist(userName);
        System.out.println(user);
        if (user==null) {
            return false;
        }
        return true;

    }


    //注册成功,将输入的用户信息封装进入数据库
    @RequestMapping("/re_success")
    public String re_success(User user,Model model){

        int i = userService.addUser(user);
        if (i!=1){
            model.addAttribute("msg","注册异常 ！");
            return "redirect:/denglu";
        }

        //每一个用户都有redis的键:用户名+data 标志着用户是否已完善资料,0代表没有，1代表有
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(user.getUserName()+"data","0");

        return "init/regist_success";
    }


    //登录
    @RequestMapping("/login")
    public String login(User user, HttpSession session, Model model){

        //判断User内容是否正确
        User user1 = userService.ifExist(user.getUserName());

        if(user1==null||!(user1.equals(user))){

            //设置登录失败的信息
            model.addAttribute("msg","账号密码错误!");
            //回到登录页面
            return "init/index";

        }else {

            //把登陆成功的用户名保存起来
            session.setAttribute("userName",user.getUserName());

            //默认用户的头像是logo.png
            //设置头像不仅要在redis中设置,还有在session中同步当前用户的头像
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            if (operations.get(user.getUserName()+"_headImage")==null){
                operations.set(user.getUserName()+"_headImage","logo.png");
                session.setAttribute("My_headImage","logo.png");
            }

            String headImage = operations.get(user.getUserName() + "_headImage");
            session.setAttribute("My_headImage",headImage);

            //登录成功重定向到main.html;  重定向防止表单重复提交
            return "redirect:/main.html";
        }
    }


    //为防止表单重复提交,而采用的真正的主页
    @GetMapping("/main.html")
    public String mainPage(HttpSession session, Model model){
        return "init/main";
    }



    //修改信息
    @GetMapping("/updateI")
    public String updateI(){
        return "updateI";
    }


}
