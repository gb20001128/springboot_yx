package com.springboot_yx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springboot_yx.bean.MicroVideo;
import com.springboot_yx.service.MicroVideoService;
import com.springboot_yx.utils.UploadFileByServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @Description
 * @Author gb
 * @Data 2022/10/24 15:09
 */

@Controller
public class MicroVideoController {


    @Autowired
    MicroVideoService service;

    @RequestMapping("/microVideo")
    public String microVideo(Model model, HttpSession session){

        QueryWrapper<MicroVideo> queryWrapper=new QueryWrapper();
        queryWrapper.orderByDesc("id");

        List<MicroVideo> list = service.list(queryWrapper);

        model.addAttribute("list",list);

        String uName = session.getAttribute("userName") + "";
        if ("admin".equals(uName)||"king".equals(uName))
            model.addAttribute("result",1);
        else
            model.addAttribute("result",0);


        return "video/microVideo";
    }

    @RequestMapping("/addMicroVideo")
    public String addMicroVideo(){


        return "video/addVideo";
    }


    @RequestMapping("/uploadMicroVideo")
    public String uploadMicroVideo( MultipartFile video,String description,MultipartFile doorImage) throws Exception{


        InputStream inputStream = video.getInputStream();

        String uuid = UUID.randomUUID().toString().replace("-","");
        //uuid太长,截一部分
        uuid=uuid.substring(27);
        //进行文件名称的拼接
        String newFileName = uuid+".mp4";



        String uuid1 = UUID.randomUUID().toString().replace("-","");
        //uuid太长,截一部分
        uuid1=uuid1.substring(27);
        //进行文件名称的拼接
        String doorImageName = uuid+".png";

        boolean b = UploadFileByServer.uploadFile(newFileName,inputStream,doorImageName, doorImage.getInputStream());

        if (b){
            service.save(new MicroVideo(null,description,doorImageName,newFileName,0));
        }


        return "redirect:/microVideo";
    }


    @RequestMapping("/deleteVideo/{id}")
    public String deleteVideo(@PathVariable("id") String id){

        MicroVideo video = service.getById(id);
        String microVideoName = video.getMicroVideoName();
        String doorImage = video.getDoorImage();
        service.removeById(id);
        UploadFileByServer.deleteFile(microVideoName,doorImage);



        return "redirect:/microVideo";
    }

    @RequestMapping("/callVideo/{id}")
    @ResponseBody
    public int callVideo(@PathVariable("id") String id){

        MicroVideo video = service.getById(id);

        int likeCount=video.getLikeCount()+1;

        video.setLikeCount(likeCount);

        service.updateById(video);

        return likeCount;
    }




}
