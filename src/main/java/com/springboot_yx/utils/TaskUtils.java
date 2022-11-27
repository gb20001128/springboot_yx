package com.springboot_yx.utils;
//定时任务类,每24点清空redis的1号数据库的数据
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Set;

@Component
@Slf4j
public class TaskUtils {

    @Autowired
    StringRedisTemplate redisTemplate;


    @Value("${file.pathShow}")
    String Base_Path1;

    @Value("${file.pathLove}")
    String Base_Path2;

    // 添加定时任务
    @Scheduled(cron = "0 0 0 * * ?") // cron表达式：每天24点执行
    public void doTask(){

        //获取所有redis的键
        Set<String> keys = redisTemplate.keys("*");

        //删除只存活于一天,用于搜索配偶的键、上传图片的键、提交图片的键
        for (String key : keys) {
            boolean matches = key.matches("(image.*)|(imageLove.*)");
            if (matches)
                redisTemplate.delete(key);
        }

        log.info("定时任务已完成~");

    }

    // 添加定时任务
    // @Scheduled(fixedRate = 3000)
    // cron表达式：每250秒执行
    @Scheduled(cron = "0 0 0 * * ?") // cron表达式：每天24点执行
    public void doTask2(){

        //获取所有redis的键
        Set<String> keys = redisTemplate.keys("*");

        //删除只存活于一天,用于搜索配偶的键、上传图片的键、提交图片的键
        for (String key : keys) {
            boolean matches = key.matches("(^delete.*)");
            if (matches) {
                //刪除健
                redisTemplate.delete(key);

                String imageName = key.substring(6, key.length());

                //删除图片在本机中的位置
                //File file1 = new File("F:\\apache-tomcat-8.0.50\\webapps\\upload\\"+imageName);
                //File file1 = new File("/www/server/tomcat/webapps/upload/"+imageName);
                File file1 = new File(Base_Path1+imageName);
                file1.delete();

                //删除图片在本机中的位置
                //File file2 = new File("F:\\apache-tomcat-8.0.50\\webapps\\love\\"+imageName);
                //File file2 = new File("/www/server/tomcat/webapps/love/"+imageName);
                File file2 = new File(Base_Path2+imageName);
                System.out.println(file2);
                file2.delete();

            }

        }

        log.info("定时任务已完成~");

    }

}
