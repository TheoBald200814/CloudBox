package cloudbox.notice.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Validated
public class TokenRedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 前端请求身份检查（token校验）
     * @param token 用户令牌
     * @return 若校验成功，返回用户Id；若校验失败，返回null；
     */
    public Map<String,String> tokenCheck(@Size(max = 32,min = 32) String token) {
        Object temp = redisTemplate.opsForHash().entries(token);
        //查找token，获得账户Id和账户权限
        Map<String,String> result = (HashMap<String,String>) temp;
        //类型转换
        if(!result.isEmpty()){
            System.out.println("token认证成功，允许服务");
            //若查找成功
            redisTemplate.opsForHash().getOperations().expire(token,600, TimeUnit.SECONDS);
            //刷新token有效期
            return result;
        }
        System.out.println("身份认证失败，拒绝服务");
        return null;
    }

}
