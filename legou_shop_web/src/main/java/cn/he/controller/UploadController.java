package cn.he.controller;

import cn.he.entity.result;
import cn.he.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Value("${FILE_SERVER_URL}")
    private String serverPath;
    @RequestMapping("/upload")
    public result uploadFile(MultipartFile file){
        try {
            String filename = file.getOriginalFilename();
            String extName = filename.substring(filename.lastIndexOf(".") + 1);
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fdfs_client.conf");
            // 返回的s是文件的组的名字+文件路径+文件名称
            String s = fastDFSClient.uploadFile(file.getBytes(), extName);
            String path = serverPath+s;
            return new result(true,"成功",path);
        } catch (Exception e) {
            e.printStackTrace();
            return new result(false,"失败");
        }
    }
}
