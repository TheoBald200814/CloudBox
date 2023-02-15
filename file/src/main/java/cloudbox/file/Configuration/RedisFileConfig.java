package cloudbox.file.Configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisFileConfig extends RedisConfig{

    @Value("${spring.redis.database_file}")
    private int FileDatabase;

    @Bean(name = "FileRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(){

        return getRedisTemplate(FileDatabase);
    }
}
