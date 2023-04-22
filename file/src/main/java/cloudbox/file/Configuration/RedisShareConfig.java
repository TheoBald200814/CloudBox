package cloudbox.file.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisShareConfig extends RedisConfig{

    @Value("${spring.redis.database_share}")
    private int ShareDatabase;

    @Bean(name = "ShareRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(){

        return getRedisTemplate(ShareDatabase);
    }
}
