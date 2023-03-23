package cloudbox.identity.service.serverImpl;

import cloudbox.identity.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

/**
 * @author theobald
 */
@Service
@Validated
public class IdentityServiceImpl implements IdentityService {

    @Autowired
    private RestTemplate restTemplate;
    //远程过程调用
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    //Redis数据库

    /**
     * 用户身份认证
     *
     * @param accountId 账户ID
     * @param password  账户密码
     * @return 账户令牌
     */
    @Override
    public String userLogin(@NotBlank @Email @Size(min = 10, max = 30) String accountId,
                            @NotBlank @Size(min = 10, max = 30) String password) {
        Map<String,String> result = restTemplate.postForObject("http://cloud-box-account-management/readAccountPassword",accountId,Map.class);
        //远程调取账户密码
        String encryptPassword = encryptPasswordByDM5(password);
        String resultPassword = result.get("passwordByMD5");
        String resultAuthority = result.get("authority");
        //数据拆箱
        if (resultPassword != null && resultPassword.equals(encryptPassword)) {
            //密码校验成功
            Map<String,String> tokenResult = new HashMap<>();
            tokenResult.put("accountId", accountId);
            tokenResult.put("authority", resultAuthority);
            //token映射数据装箱
            String token = generateToken(password);
            //生成token
            redisTemplate.opsForHash().putAll(token,tokenResult);
            //token写入Redis相应数据库
            return token;
        } else {
            //密码校验失败
            return null;
        }

    }


    /**
     * 接口预留
     * 服务模块登陆
     */
    @Override
    public void serverLogin() {

    }

    /**
     * 原始密码加密（MD5算法）
     * @param password 原始密码
     * @return 加密后的32位字符串
     */
    private String encryptPasswordByDM5(String password) {
        String data = password + "aa7aa8a8fa604c60866413f52563b70c";
        return DigestUtils.md5DigestAsHex(data.getBytes());
    }

    /**
     * 临时Token生成器
     * @param info 生成信息
     * @return token
     */
    private String generateToken(String info) {
        String data = info + "aa7aa8a8fa604c60866413f52563b70c";
        return DigestUtils.md5DigestAsHex(data.getBytes());
    }
}
