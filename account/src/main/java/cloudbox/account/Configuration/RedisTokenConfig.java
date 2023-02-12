package cloudbox.account.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * Redis账户库配置
 * @author TheoBald
 * @version 0.0.1
 */
@Configuration
public class RedisTokenConfig extends RedisConfig {

    @Value("${spring.redis.database_token}")
    private int studentDatabase;

    @Bean(name = "TokenRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(){

        return getRedisTemplate(studentDatabase);
    }

}
