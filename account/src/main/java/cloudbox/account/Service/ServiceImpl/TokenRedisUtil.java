package cloudbox.account.Service.ServiceImpl;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.util.concurrent.TimeUnit;

/**
 * RedisToken库操作
 * @author TheoBald
 * @version 0.0.1
 */
@Validated
@Component
public class TokenRedisUtil {

    @Resource(name = "TokenRedisTemplate")
    RedisTemplate<String, Object> redisTemplate;


    /**
     * 前端请求身份检查（token校验）
     * @param token 用户令牌
     * @return 若校验成功，返回用户Id；若校验失败，返回null；
     */
    public String tokenCheck(@Size(max = 32,min = 32) String token){

        Object temp = redisTemplate.opsForValue().get(token);
        //查找token，获得账户Id
        String result=(String) temp;
        //类型转换
        if(result.length()!=0){
            //若查找成功
            redisTemplate.opsForHash().getOperations().expire(token,600, TimeUnit.SECONDS);
            //刷新token有效期
            return result;
        }

        System.out.println("身份认证失败，拒绝服务");

        return null;
    }

}
