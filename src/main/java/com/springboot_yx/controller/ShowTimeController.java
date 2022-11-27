package com.springboot_yx.controller;

import com.springboot_yx.bean.DataJson;
import com.springboot_yx.utils.QueryCityByIp;
import com.springboot_yx.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;

@Controller
public class ShowTimeController {


    @Autowired
    StringRedisTemplate redisTemplate;

    @Value("${file.pathShow}")
    String Base_Path;


    @RequestMapping("/showTime")
    public String showTime(Model model, HttpSession session, HttpServletRequest request){

        ZSetOperations<String, String> operations = redisTemplate.opsForZSet();

        //风采展示所存储的信息全部由zet类型的键 StyleShow 存储(以Set集合方式获取所有键中的值,即图片与描述)
        Set<ZSetOperations.TypedTuple<String>> styleShows = operations.reverseRangeByScoreWithScores("StyleShow", 0, 9999999 * 999999);

        //设置参数
        model.addAttribute("gbs",styleShows);

        String uName = session.getAttribute("userName") + "";
        if ("admin".equals(uName)||"king".equals(uName))
            model.addAttribute("result",1);
        else
            model.addAttribute("result",0);

        String ip = request.getRemoteAddr();
        String city = QueryCityByIp.queryAddrByIp(ip);


        model.addAttribute("city",city);


        return "show/showTime";
    }

    @RequestMapping("/addImages")
    public String addImage(){
        return "show/addImages";
    }

    //添加到我的图片库
    @RequestMapping("/addImage")
    @ResponseBody  //注解作用:表单里的域值都能在参数列表中获取,只不过参数名要与域名一致
    public String addImages(String imageDescribe,String imagePath,HttpSession session){

        //图片描述太多就返回
        if (imageDescribe.length()>20){
            return "0";
        }
        //描述为空也返回
        if (imageDescribe.length()==0){
            return "-1";
        }


        ZSetOperations<String, String> operations = redisTemplate.opsForZSet();
        ValueOperations<String, String> operation = redisTemplate.opsForValue();




        //获取当前用户,与字符串image结合,成为redis的键(目的是控制用户每天提交图片次数)
        String userNameImage="image"+session.getAttribute("userName");
        String userName = operation.get(userNameImage);

        String uName = session.getAttribute("userName") + "";

        //不存在就设置提交次数为1
        if (userName==null){
            operation.set(userNameImage,"1");
        }
        //>=3就说明提交太多,不能继续提交了
        else if (Integer.parseInt(userName)>=3 && !"king".equals(uName)){

            return "-2";
        }
        //其他情况下,就设置提交次数+1
        else {
            operation.increment(userNameImage);
        }

        //通过图片访问路径截取到图片名
        String imageName=imagePath.substring(imagePath.length()-9, imagePath.length());

        System.out.println(imageName);

        //说明合法提交,可以删除键了
        redisTemplate.delete("delete"+imageName);

        //设置提交的图片名与当前用户相关联,用于设置以后的删除权限
        //userName =session.getAttribute("userName")+"";
        //operation.set(imageName,userName);

        System.out.println("图片描述:"+imageDescribe);
        System.out.println("图片名字:"+imageName);


        //redis:通过指定的key放入传来的 图片名称与图片描述,初始评分是0
        operations.add("StyleShow",imageName+"\" "+imageDescribe+" \"",0);

        return "1";
    }

    //把上传的图片保存到本机的Tomcat上
    @RequestMapping("/uploadImage")
    @ResponseBody
    public DataJson uploadImage(MultipartFile file,HttpSession session){

        //参加Json格式的对象
        DataJson dataJson = new DataJson();

        //图片大于20MB提示不可
        if (file.getSize()>20000000){
            dataJson.setCode(-2);
            return dataJson;
        }




        //调用工具类完成文件上传,获取文件在服务器中的位置
        String imagePath = UploadUtils.upload1(file);

        String imageName=imagePath.substring(imagePath.length()-9, imagePath.length());

        System.out.println(imageName);

        ValueOperations<String, String> operation = redisTemplate.opsForValue();


        operation.set("delete"+imageName,"");
        System.out.println("经过了设置删除键");

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



    //下载指定图片
    @RequestMapping("/downImage/{imageName}")
    public ResponseEntity<byte[]> testResponseEntity(@PathVariable("imageName") String imageName) throws IOException {

        //String realPath="F:\\apache-tomcat-8.0.50\\webapps\\upload\\"+imageName;
        //String realPath="/www/server/tomcat/webapps/upload/"+imageName;
        String realPath=Base_Path+imageName;

        System.out.println(realPath);
        //创建输入流,导向该文件
        InputStream is = new FileInputStream(realPath);
        //创建与文件一样大小的字节数组
        byte[] bytes = new byte[is.available()];
        //将流读到字节数组中
        is.read(bytes);
        //创建HttpHeaders对象设置响应头信息
        MultiValueMap<String, String> headers = new HttpHeaders();
        //设置要下载方式以及下载文件的名字(固定的)
        headers.add("Content-Disposition", "attachment;filename="+imageName);
        //设置响应状态码:成功响应
        HttpStatus statusCode = HttpStatus.OK;
        //创建ResponseEntity对象
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);
        //关闭输入流
        is.close();
        //将带有文件下载的响应实体响应给浏览器
        return responseEntity;
    }

    //通过key给图片点赞+1
    /*@RequestMapping("/call/{photo}")
    public String  call(@PathVariable("photo")String photo, Model model){
        System.out.println(photo);
        ZSetOperations<String, String> operations = redisTemplate.opsForZSet();
        operations.incrementScore("StyleShow",photo,1);
        return "redirect:/showTime";
    }*/


    //通过key给图片点赞+1
    @RequestMapping("/call/{photo}")
    @ResponseBody
    public int  calls(@PathVariable("photo")String photo, Model model){
        System.out.println(photo);
        ZSetOperations<String, String> operations = redisTemplate.opsForZSet();
        Double styleShow = operations.incrementScore("StyleShow", photo, 1);
        double style=styleShow;
        int score = (int) style;
        return score;
    }

    //通过图片名字描述删除指定图片
    @GetMapping("/delete/{photo}")
    public String delete(@PathVariable("photo")String photo, Model model, HttpSession session){

        //截取出图片名
        String imageName = photo.substring(0, 9);

        ValueOperations<String, String> operation = redisTemplate.opsForValue();

        //获取图片的上传者
        String s = operation.get(imageName);

        //获取当前用户
        String userName = session.getAttribute("userName")+"";

        //只有当前用户是此图片的上传者或管理员才能进行删除
        if(userName.equals("admin")||userName.equals("king")||userName.equals(s)){

            ZSetOperations<String, String> operations = redisTemplate.opsForZSet();
            operations.remove("StyleShow",photo);
            operation.getAndDelete(imageName);

            //删除图片在本机中的位置
            //File file = new File("F:\\apache-tomcat-8.0.50\\webapps\\upload\\"+imageName);
            //File file = new File("/www/server/tomcat/webapps/upload/"+imageName);
            File file = new File(Base_Path+imageName);
            file.delete();
            return "redirect:/showTime";
        //否则不能删除,还要提示用户权限不够
        }else {
            return "redirect:/showTime";
        }


    }

}
