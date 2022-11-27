package com.springboot_yx.controller;

import com.springboot_yx.bean.DataJson;
import com.springboot_yx.bean.Love;
import com.springboot_yx.bean.ShowLove;
import com.springboot_yx.service.LoveService;
import com.springboot_yx.service.ShowLoveService;
import com.springboot_yx.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;

@Controller
public class LoveController {

    @Autowired
    StringRedisTemplate redisTemplate;


    @Autowired
    LoveService loveService;

    @Autowired
    ShowLoveService showLoveService;

    @Value("${file.pathLove}")
    String Base_Path;

    //进入脱单页
    @RequestMapping("/love")
    public String love(HttpSession session, Model model){

        //在redis中获取属于当前用户的标志是否完善资料的键
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String ifUserName = operations.get(session.getAttribute("userName") + "data");
        //如果键为0或null,说明未完善资料
        if ("0".equals(ifUserName)||ifUserName==null)
            //去完善资料
            return "single/tomakedata";
        else{

            List<ShowLove> showLoves = showLoveService.selectLoveALL();

            model.addAttribute("gbs",showLoves);
            return "single/love";
        }

    }

    //去完善资料的页面
    @RequestMapping("/makedata")
    public String makedata(){
        return "single/makedata";
    }

    //接收完善资料的数据
    @RequestMapping("/postdata")
    public String postdata( Love love, HttpSession session){
        //补充信息
       love.setUserName( session.getAttribute("userName")+"");
        String headImage = session.getAttribute("My_headImage") + "";
        love.setHeadImage(headImage);

        int i = loveService.addLove(love);

        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        //设置当前用户的redis键状态为已完善资料
        operations.set(session.getAttribute("userName") + "data","1");
        return "redirect:/love";
    }

    //上传自己的恋爱展示
    @RequestMapping("/addLoves")
    public String addLove(){
        
        return "single/addLoves";
    }


    //把上传的求偶资料 保存到本机的Tomcat上
    @RequestMapping("/uploadLove")
    @ResponseBody
    public DataJson uploadLove(MultipartFile file, HttpSession session){

        //参加Json格式的对象
        DataJson dataJson = new DataJson();

        //图片大于20MB提示不可
        if (file.getSize()>20000000){
            dataJson.setCode(-2);
            return dataJson;
        }



        //调用工具类完成文件上传,获取文件在服务器中的位置
        String imagePath = UploadUtils.upload2(file);

        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        //通过图片访问路径截取到图片名
        String imageName=imagePath.substring(imagePath.length()-9, imagePath.length());

        //设置键,如果提交成功就删除此键
        operations.set("delete"+imageName,"");

        //打印文件在服务器中的名字
        System.out.println("图片地址: "+imagePath);


        if (imagePath != null){
            //创建一个HashMap用来存放图片路径
            HashMap hashMap = new HashMap();
            hashMap.put("src",imagePath);
            //Json设置属性值
            dataJson.setCode(1);
            dataJson.setMsg("上传成功");
            dataJson.setData(hashMap);
        }else{
            dataJson.setCode(0);
            dataJson.setMsg("上传失败");
        }
        //返回Json
        return dataJson;

    }

    //添加到脱单展示
    @RequestMapping("/addLove")
    @ResponseBody  //注解作用:表单里的域值都能在参数列表中获取,只不过参数名要与域名一致
    public String addLove(String imageDescribe,String imagePath,HttpSession session){

        //图片描述太多就返回
        if (imageDescribe.length()>20){
            return "0";
        }
        //描述为空也返回
        if (imageDescribe.length()==0){
            return "-1";
        }


        ValueOperations<String, String> operation = redisTemplate.opsForValue();



        //获取当前用户,与字符串imageLove结合,成为redis的键(目的是控制用户每天提交图片次数)
        String userNameImage="imageLove"+session.getAttribute("userName");
        String userName = operation.get(userNameImage);

        //不存在就设置提交次数为1
        if (userName==null){
            operation.set(userNameImage,"1");
        }
        //>=3就说明提交太多,不能继续提交了
        else if (Integer.parseInt(userName)>=3){

            return "-2";
        }
        //其他情况下,就设置提交次数+1
        else {
            operation.increment(userNameImage);
        }


        //通过图片访问路径截取到图片名
        String imageName=imagePath.substring(imagePath.length()-9, imagePath.length());

        //设置键,如果提交成功就删除此键
        redisTemplate.delete("delete"+imageName);


        System.out.println("图片描述:"+imageDescribe);
        System.out.println("图片名字:"+imageName);

        ShowLove showLove = new ShowLove();
        String uName = session.getAttribute("userName") + "";
        showLove.setUserName(uName);

        //修改头像
        String headImage = operation.get(session.getAttribute("userName") + "_headImage");
        showLove.setHeadImage(headImage);

        showLove.setDeclaration(imageDescribe);
        showLove.setLoveImage(imageName);
        showLoveService.addLove(showLove);

        return "1";
    }



    //转筛选页
    @RequestMapping("/selectLove")
    public String selectLove( ){

        return "single/selectLove";
    }

    //筛选自己的Love
    @RequestMapping("/selectMyLove")
    public String selectMyLove( Love love, Model model){

        List<Love> loves = loveService.selectLove(love);

        if (loves.size()==0)
           model.addAttribute("result",0);
       else {
            model.addAttribute("result",1);
            model.addAttribute("gbs",loves);
        }


        return "single/myloves";
    }



    //查看自己的脱单历史
    @RequestMapping("/myhistory")
    public String myHistory(HttpSession session, Model model){
        String userName = session.getAttribute("userName") + "";

        List<ShowLove> showLoves = showLoveService.myHistory(userName);

        model.addAttribute("gbs",showLoves);

        return "single/myhistory";
    }

    //删除自己的历史
    @RequestMapping("/deletehistory/{imageName}")
    public String deleteHistory(@PathVariable("imageName")  String imageName,Model model){

        //删除图片在本机中的位置
        //File file = new File("F:\\apache-tomcat-8.0.50\\webapps\\love\\"+imageName);
        //File file = new File("/www/server/tomcat/webapps/love/"+imageName);
        File file = new File(Base_Path+imageName);
        file.delete();

        //删除数据表对应的字段
         showLoveService.delete(imageName);

        return "redirect:/myhistory";
    }






    //看别人的的历史
    @RequestMapping("/ta_history/{userName}")
    public String taHistory(@PathVariable("userName")  String userName,Model model){

        List<ShowLove> showLoves = showLoveService.myHistory(userName);

        if (showLoves.size()==0)
            return "single/sorryhistory";

        model.addAttribute("gbs",showLoves);

        return "single/love";
    }


    //点击头像出现资料卡
    @ResponseBody
    @RequestMapping("/test0/{id}")
    public Love test9(@PathVariable("id") Integer id){

        String userName = showLoveService.selectNameById(id);

        System.out.println(userName);
        return loveService.selectLoveByName(userName);
    }


    
}
