package cloudbox.account.Service.ServiceImpl;

import cloudbox.account.Bean.Account;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Redis账户库操作
 * @author TheoBald
 * @version 0.0.1
 */
@Component
public class AccountRedisUtil {

    @Resource(name = "AccountRedisTemplate")
    RedisTemplate<String, Object> redisTemplate;


    /**
     * 账户缓存插入
     * @param account 账户对象
     * @return 若更新成功，返回true；若更新失败，返回false
     */
    boolean set(Account account) throws SQLException, IOException {

        Map<String,String> redisAccount = new HashMap<>();
        //初始化HashMap
        redisAccount.put("passwordByMD5",account.getPassword());
        //集合插入密码
        redisAccount.put("nickname",account.getNickname());
        //集合插入昵称

        if(account.getPhoto() != null){

            redisAccount.put("photo",new String(account.getPhoto().getBinaryStream().readAllBytes()));
            //当头像非空时，将头像的二进制文件转化为字符串，插入集合
        }else{

            redisAccount.put("photo",null);
        }
        redisAccount.put("authority",String.valueOf(account.getAuthority()));
        //集合插入权限位
        redisAccount.put("deleted",String.valueOf(account.getDeleted()));
        //集合插入有效位
        redisAccount.put("account_empty",String.valueOf(account.getAccountEmpty()));
        //集合插入账户云盘容量
        redisTemplate.opsForHash().putAll(account.getId(),redisAccount);
        redisTemplate.opsForHash().getOperations().expire(account.getId(),60, TimeUnit.SECONDS);
        //录入缓存（key：accountId，value：HashMap）

        if(redisTemplate.opsForHash().hasKey(account.getId(),"passwordByMD5")){

            return true;
        }else {

            return false;
        }
    }


    /**
     * 账户缓存查找
     * @param accountId 账户Id
     * @return 若缓存命中，则返回该账户对象；若缓存未命中，则返回null
     */
    Account get(String accountId){

        Object account= redisTemplate.opsForHash().entries(accountId);
        //缓存查找
        LinkedHashMap<String,String> temp=(LinkedHashMap<String, String>) account;
        //类型转换
        if(temp.size()==0){

            return null;
        }else{

            redisTemplate.opsForHash().getOperations().expire(accountId,60,TimeUnit.SECONDS);
            //刷新该账户在缓存中的有效期
            Account result = new Account();
            //初始化账户对象
            result.setId(accountId);

            result.setPassword(temp.get("passwordByMD5"));

            result.setDeleted(Byte.parseByte(temp.get("deleted")));

            result.setNickname(temp.get("nickname"));

            result.setAccountEmpty(Integer.parseInt(temp.get("account_empty")));

            result.setAuthority(Byte.parseByte(temp.get("authority")));

            result.setPhoto(null);

            return result;
        }
    }


    /**
     * 账户缓存更新
     * @param accountId 账户Id（Redis中作为Key）
     * @param hashKey 账户属性（Redis中作为HashKey）
     * @param hashValue 账户属性值（Redis中作为HashValue）
     */
    void update(String accountId,String hashKey,String hashValue){

        redisTemplate.opsForHash().put(accountId,hashKey,hashValue);

        redisTemplate.opsForHash().getOperations().expire(accountId,60,TimeUnit.SECONDS);
    }

}
