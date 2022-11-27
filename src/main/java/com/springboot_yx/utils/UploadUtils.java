package com.springboot_yx.utils;
//上传图片用的工具类

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;
import net.coobird.thumbnailator.resizers.configurations.ScalingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
@Component
public class UploadUtils {


    static String BASE_PATH;

    static String BASE_PATH_L;

    @Value("${file.pathShow}")
     public void get1(String string){
        BASE_PATH=string;
    }


    @Value("${file.pathLove}")
    public void get2(String string){
        BASE_PATH_L=string;
    }




    public static Logger logger = LoggerFactory.getLogger(UploadUtils.class);




    /*  风采展示 */
    //定义一个目标路径,就是我们要把图片上传到的位置;
    //private  static final String BASE_PATH="F:\\apache-tomcat-8.0.50\\webapps\\upload\\";
    //private  static final String BASE_PATH="/www/server/tomcat/webapps/upload";


    //定义文件服务器的访问地址
    private static  final String SERVER_PATH="http://43.142.110.195:8080/upload/";






    /* 脱单 */
    //定义一个目标路径,就是我们要把图片上传到的位置
    //private  static final String BASE_PATH_L="F:\\apache-tomcat-8.0.50\\webapps\\love\\";
    //private  static final String BASE_PATH_L="/www/server/tomcat/webapps/love";

    //定义文件服务器的访问地址
    private static  final String SERVER_PATH_L="http://43.142.110.195:8080/love/";




    public static String upload1(MultipartFile file){
        //获得上传文件的名称
        String filename = file.getOriginalFilename();
        System.out.println("图片的原始名字: " + filename);
        //创建UUID,用来保持文件名字的唯一性
        String uuid = UUID.randomUUID().toString().replace("-","");
        //uuid太长,截一部分
        uuid=uuid.substring(27);
        //进行文件名称的拼接
        String newFileName = uuid+".png";
        //创建文件实例对象
        System.out.println("aaaaaa"+BASE_PATH);
        File uploadFile = new File(BASE_PATH,newFileName);
        //判断当前文件是否存在
        if (!uploadFile.exists()){
            //如果不存在就创建一个文件夹
            uploadFile.mkdirs();
        }
        //执行文件上传的命令
        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
            return null;
        }

        //压缩图片
        //generateScale(BASE_PATH+"/"+newFileName);

        //将上传的文件名称返回,注意: 这里我们返回一个 服务器地址
        return SERVER_PATH+newFileName;
    }




    public static String upload2(MultipartFile file){
        //获得上传文件的名称
        String filename = file.getOriginalFilename();
        System.out.println("图片的原始名字: " + filename);
        //创建UUID,用来保持文件名字的唯一性
        String uuid = UUID.randomUUID().toString().replace("-","");
        //uuid太长,截一部分
        uuid=uuid.substring(27);
        //进行文件名称的拼接
        String newFileName = uuid+".png";
        //创建文件实例对象
        File uploadFile = new File(BASE_PATH_L,newFileName);
        //判断当前文件是否存在
        if (!uploadFile.exists()){
            //如果不存在就创建一个文件夹
            uploadFile.mkdirs();
        }
        //执行文件上传的命令
        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
            return null;
        }

        //generateScale(BASE_PATH_L+"/"+newFileName);
        //将上传的文件名称返回,注意: 这里我们返回一个 服务器地址
        return SERVER_PATH_L+newFileName;
    }


    public static void generateScale(String fileName){
        try {
            Thumbnails.of(fileName).
                    scalingMode(
                            ScalingMode.BICUBIC).
                    // 图片缩放10%, 不能和size()一起使用
                            scale(0.15).
                    // 图片质量压缩10%
                            outputQuality(0.15).
                    toFile(fileName);
        } catch (IOException e) {
            System.out.println("压缩异常");
            logger.error(e.getMessage());
        }
    }




    public static String upload3(MultipartFile file){
        //获得上传文件的名称
        String filename = file.getOriginalFilename();
        System.out.println("图片的原始名字: " + filename);
        //创建UUID,用来保持文件名字的唯一性
        String uuid = UUID.randomUUID().toString().replace("-","");
        //uuid太长,截一部分
        uuid=uuid.substring(27);
        //进行文件名称的拼接
        String newFileName = uuid+".png";
        //创建文件实例对象
        System.out.println("aaaaaa"+BASE_PATH);
        File uploadFile = new File(BASE_PATH,newFileName);
        //判断当前文件是否存在
        if (!uploadFile.exists()){
            //如果不存在就创建一个文件夹
            uploadFile.mkdirs();
        }
        //执行文件上传的命令
        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
            return null;
        }

        //压缩图片
        //generateScale(BASE_PATH+"/"+newFileName);

        //将上传的文件名称返回,注意: 这里我们返回一个 服务器地址
        return SERVER_PATH+newFileName;
    }



}
