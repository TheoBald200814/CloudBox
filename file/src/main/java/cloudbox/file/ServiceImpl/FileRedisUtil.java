package cloudbox.file.ServiceImpl;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

@Validated
@Component
public class FileRedisUtil {

    @Resource(name = "FileRedisTemplate")
    RedisTemplate<String, Object> redisTemplate;
}
