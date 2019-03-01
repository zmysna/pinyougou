package com.pinyougou.shop.controller;

import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传的控制器
 * @Author zmysna
 * @Version 1.0
 * @Date 2019/2/28 12:52
 */

@RestController
public class UploadController {

    @Value("${fileServerUrl}")
    public String fileServerUrl;

    @PostMapping("/upload")
    public Map<String,Object> upload(@RequestParam("file") MultipartFile multipartFile) {
        Map<String, Object> data = new HashMap<>();
        data.put("status",500);
        try{
            //加载配置文件，获得文件的绝对路径
            String conf_filename = this.getClass().getResource("/fastdfs_client.conf").getPath();
            //初始化全局对象
            ClientGlobal.init(conf_filename.substring(1));
            //初始化服务器
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            //创建存储客户端
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            //获得文件名
            String filename = multipartFile.getOriginalFilename();
            //返回文件地址（组名，目录名）"group1", "M00/00/00/wKgRcFV_08OAK_KCAAAA5fm_sy874.conf"
            String[] strings = storageClient.upload_file(multipartFile.getBytes(), FilenameUtils.getExtension(filename), null);
            //拼接成完整的地址
            StringBuilder sb = new StringBuilder(fileServerUrl);
            for (String str : strings) {
                sb.append("/" + str);
            }
            //返回给浏览器
            data.put("status",200);
            data.put("url",sb.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    @GetMapping("/deleteFile")
    public void deleteFile(String url){
        try{
            //分隔url获得组名和路径名
            String[] split = url.split("group");
            if(split!=null && split.length>2) {
                String toSplit = split[split.length - 1];
                String group = "group".concat(toSplit.substring(0, toSplit.indexOf("/")));
                String remote_filename = toSplit.substring(toSplit.indexOf("/") + 1);

                //加载配置文件，获得文件的绝对路径
                String conf_filename = this.getClass().getResource("/fastdfs_client.conf").getPath();
                //初始化全局对象
                ClientGlobal.init(conf_filename.substring(1));
                //初始化服务器
                TrackerClient tracker = new TrackerClient();
                TrackerServer trackerServer = tracker.getConnection();
                StorageServer storageServer = null;
                //创建存储客户端
                StorageClient storageClient = new StorageClient(trackerServer, storageServer);
                //删除文件
                storageClient.delete_file(group, remote_filename);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
