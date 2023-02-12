package cloudbox.account.Service.ServiceImpl;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TokenRedisUtil {

    @Resource(name = "TokenRedisTemplate")
    RedisTemplate<String, Object> redisTemplate;


}
