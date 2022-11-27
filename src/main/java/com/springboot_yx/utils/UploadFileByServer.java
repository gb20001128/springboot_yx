package com.springboot_yx.utils;
//上传文件到远程服务器
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description
 * @Author gb
 * @Data 2022/10/18 12:09
 */
public class UploadFileByServer {





        private static ChannelSftp sftp = null;

        /**
         * Description: 向FTP服务器上传文件
         * @param filename 上传到FTP服务器上的文件名
         * @param input    输入流
         * @return 成功返回true，否则返回false
         */
        public static boolean uploadFile(String filename, InputStream input1,String doorImage,InputStream input2) {
            boolean result = false;
            FTPClient ftp = new FTPClient();
            File file = null;
            try {
                JSch jsch = new JSch();
                //获取sshSession  账号-ip-端口
                Session sshSession = jsch.getSession("root", "1.15.73.202");
                //添加密码
                sshSession.setPassword("@gb20001128");
                Properties sshConfig = new Properties();
                //严格主机密钥检查
                sshConfig.put("StrictHostKeyChecking", "no");
                sshSession.setConfig(sshConfig);
                //开启sshSession链接
                sshSession.connect();
                //获取sftp通道
                Channel channel = sshSession.openChannel("sftp");
                //开启
                channel.connect();
                sftp = (ChannelSftp) channel;
                //服务器路径
                file = new File("/www/server/tomcat/webapps/mv/");
                //设置为被动模式
                ftp.enterLocalPassiveMode();
                //设置上传文件的类型为二进制类型
                //进入到要上传的目录  然后上传文件
                sftp.cd("/www/server/tomcat/webapps/mv/");
                sftp.put(input1, filename);
                sftp.cd("/www/server/tomcat/webapps/doorImage/");
                sftp.put(input2, doorImage);
                input1.close();
                input2.close();
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (ftp.isConnected()) {
                    try {
                        ftp.disconnect();
                    } catch (IOException ioe) {
                    }
                }
            }
            return result;
        }


    public static boolean uploadDoorImage(String filename, InputStream input) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        File file = null;
        try {
            JSch jsch = new JSch();
            //获取sshSession  账号-ip-端口
            Session sshSession = jsch.getSession("root", "1.15.73.202");
            //添加密码
            sshSession.setPassword("@gb20001128");
            Properties sshConfig = new Properties();
            //严格主机密钥检查
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            //开启sshSession链接
            sshSession.connect();
            //获取sftp通道
            Channel channel = sshSession.openChannel("sftp");
            //开启
            channel.connect();
            sftp = (ChannelSftp) channel;
            //服务器路径
            file = new File("/www/server/tomcat/webapps/doorImage/");
            //设置为被动模式
            ftp.enterLocalPassiveMode();
            //设置上传文件的类型为二进制类型
            //进入到要上传的目录  然后上传文件
            sftp.cd("/www/server/tomcat/webapps/doorImage/");
            sftp.put(input, filename);
            input.close();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }


    public static boolean deleteFile(String filename,String doorImage) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        File file = null;
        try {
            JSch jsch = new JSch();
            //获取sshSession  账号-ip-端口
            Session sshSession = jsch.getSession("root", "1.15.73.202");
            //添加密码
            sshSession.setPassword("@gb20001128");
            Properties sshConfig = new Properties();
            //严格主机密钥检查
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            //开启sshSession链接
            sshSession.connect();
            //获取sftp通道
            Channel channel = sshSession.openChannel("sftp");
            //开启
            channel.connect();
            sftp = (ChannelSftp) channel;
            //服务器路径
            file = new File("/www/server/tomcat/webapps/mv/");
            //设置为被动模式
            ftp.enterLocalPassiveMode();
            //设置上传文件的类型为二进制类型
            //进入到要上传的目录  然后上传文件
            sftp.cd("/www/server/tomcat/webapps/mv/");
            sftp.rm(filename);
            sftp.cd("/www/server/tomcat/webapps/doorImage/");
            sftp.rm(doorImage);

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }




}
