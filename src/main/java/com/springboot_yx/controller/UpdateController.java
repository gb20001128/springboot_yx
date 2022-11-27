package com.springboot_yx.controller;

import com.springboot_yx.bean.User;
import com.springboot_yx.service.LoveService;
import com.springboot_yx.service.ShowLoveService;
import com.springboot_yx.service.UserService;
import com.springboot_yx.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Controller
public class UpdateController {

    @Autowired
    UserService userService;

    @Autowired
    LoveService loveService;

    @Autowired
    ShowLoveService showLoveService;

    @Autowired
    StringRedisTemplate redisTemplate;


    @Value("${file.pathHead}")
    String BASE_PATH;

    @RequestMapping("/updateI")
    public String updateI(){
        return "updateI";
    }


    @RequestMapping("/updateMyPassWord")
    public String updateMyPassWord(String pwd, HttpSession session){

        String userName = session.getAttribute("userName") + "";
        userService.updatePassWord(userName,pwd);

        return "redirect:/";
    }

    @RequestMapping("/updateMyPhotoName")
    public String updateMyPhotoName(MultipartFile photo,String newName,HttpSession session) throws Exception{

        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        //可以修改用户名
        if (!newName.equals("")){
            //改session
            String oldName = session.getAttribute("userName") + "";
            session.setAttribute("userName",newName);


            //修改用户名
            userService.updateName(oldName,newName);

            //更新数据库另外两个表的name
            loveService.updateLove(oldName,newName);
            showLoveService.updateShowLove(oldName,newName);


            //修改redis中的键

            String s = operations.get(oldName + "data");
            if (s!=null)
                operations.set(newName+"data",s);
            redisTemplate.delete(oldName + "data");

            String s1 = operations.get("image1"+oldName  );
            if (s1!=null)
                operations.set("image1"+newName,s1);
            redisTemplate.delete("image1"+oldName  );

            String s2= operations.get("image2"+oldName  );
            if (s2!=null)
                operations.set("image2"+newName,s2);
            redisTemplate.delete("image2"+oldName  );


            String sl1 = operations.get("imageLove1"+oldName  );
            if (sl1!=null)
                operations.set("imageLove1"+newName,sl1);
            redisTemplate.delete("imageLove1"+oldName  );


            String sl2 = operations.get("imageLove2"+oldName  );
            if (sl2!=null)
                operations.set("imageLove2"+newName,sl2);
            redisTemplate.delete("imageLove2"+oldName  );



            //把头像移到新用户名上
            String headImage = operations.get(oldName + "_headImage");
            operations.set(newName+"_headImage",headImage);
            redisTemplate.delete(oldName + "_headImage");
            session.setAttribute("My_headImage",headImage);


        }


             if (photo.getOriginalFilename().equals("")){
                 return "redirect:/updateI";
         }

        //可以修改头像
        //String BASE_PATH="F:\\apache-tomcat-8.0.50\\webapps\\headImage\\";
        //String BASE_PATH="/www/server/tomcat/webapps/headImage";

        //获得上传文件的名称
        String filename = photo.getOriginalFilename();
        System.out.println("图片的原始名字: " + filename);
        //创建UUID,用来保持文件名字的唯一性
        String uuid = UUID.randomUUID().toString().replace("-","");
        //uuid太长,截一部分
        uuid=uuid.substring(27);
        //进行文件名称的拼接
        String newFileName = uuid+".png";
        //创建文件实例对象
        File uploadFile = new File(BASE_PATH,newFileName);
        //判断当前文件是否存在
        if (!uploadFile.exists()){
            //如果不存在就创建一个文件夹
            uploadFile.mkdirs();
        }
        //执行文件上传的命令
        try {
            photo.transferTo(uploadFile);
        } catch (IOException e) {
            return null;
        }


        //压缩图片
        UploadUtils.generateScale(BASE_PATH+"/"+newFileName);


        //修改头像
        String userName = session.getAttribute("userName") + "";

        //修改在恋爱个人资料表love的头像
        loveService.updateImage(userName,newFileName);

        operations.set(session.getAttribute("userName")+"_headImage",newFileName);
        //获取原来的头像名
        String headImage = session.getAttribute("My_headImage") + "";
        //通过会话设置新头像
        session.setAttribute("My_headImage",operations.get(session.getAttribute("userName")+"_headImage"));


        showLoveService.updateHeadImage(userName,newFileName);

        //删除图片在本机中的位置
        if (!headImage.equals("logo.png")) {
            //File file = new File("F:\\apache-tomcat-8.0.50\\webapps\\headImage\\" + headImage);
            //File file = new File("/www/server/tomcat/webapps/headImage/"+headImage);
            File file = new File(BASE_PATH+headImage);
            file.delete();
        }

        return "redirect:/updateI";
    }







}
