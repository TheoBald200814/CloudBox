package cloudbox.file.Controller;


import cloudbox.file.Bean.File;
import cloudbox.file.Service.FileManagement;
import cloudbox.file.ServiceImpl.FileRedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.sql.Timestamp;

@RestController
public class test {

    @Resource(name = "TokenRedisTemplate")
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private FileManagement fileManagement;



    @PostMapping(value = "test")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    Object test(@RequestParam MultipartFile file){

        if(file != null){

            System.out.println("dsssdsds");
        }else{
            System.out.println("qqqqqqqq");
        }



        fileManagement.deleteFCB("test_update_url","controller_4@test.com");
        return "23333";
    }
}
