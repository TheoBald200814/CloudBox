package cloudbox.file.Controller;


import cloudbox.file.Service.FileManagement;
import cloudbox.file.ServiceImpl.FtpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;

@RestController
public class test {

    @Resource(name = "TokenRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private FileManagement fileManagement;


    @PostMapping(value = "test")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    Object test(@RequestParam MultipartFile file) throws IOException {


        Timestamp t = new Timestamp(232323);





        if(file != null){

            fileManagement.createFile("test","controller_4@test.com",255,"doc",t,0,file);





        }else{
            System.out.println("qqqqqqqq");
        }







        return "23333";
    }


    @Autowired
    private FtpClientUtil ftpClientUtil;

    @GetMapping("/download/{fileName}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void downloadFile(@PathVariable String fileName, HttpServletResponse response) throws IOException {

//        System.out.println(fileName);
//
//        ftpClientUtil.downloadFileFromFTP(fileName,"/home/data/ftptest",response);
//
//        ftpClientUtil.downloadFile("controller_4@test.comtest","controller_4@test.com/","233",response);
//
//        fileManagement.downloadFile("test","controller_4@test.com",response);

//        fileManagement.deleteFCB("test","controller_4@test.com");




//        fileManagement.downloadFile("test","controller_4@test.com",response);
    }





    /**
     * 文件拦截
     * @param multipartFile 文件数据
     * @param fileName 重定义文件名
     * @param uploadDir 文件下载位置
     * @throws IOException
     */
    public void saveFile(MultipartFile multipartFile, String fileName, String uploadDir) throws IOException {
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        byte[] bytes = multipartFile.getBytes();
        Path path = Paths.get(uploadDir + File.separator + fileName);
        Files.write(path, bytes);
    }

}
