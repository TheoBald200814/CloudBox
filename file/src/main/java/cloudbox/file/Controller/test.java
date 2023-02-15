package cloudbox.file.Controller;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class test {

    @Resource(name = "TokenRedisTemplate")
    RedisTemplate<String, Object> redisTemplate;

    @PostMapping(value = "test")
    @ResponseBody
    Object test(){


        return "23333";
    }
}
