package cloudbox.account.Controller;


import cloudbox.account.Bean.Account;
import cloudbox.account.Service.AccountManagement;
import cloudbox.account.Service.ServiceImpl.TokenRedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员账户控制类
 * @author TheoBald
 * @version 0.0.1
 */
@RestController
@CrossOrigin(value = "http://localhost:3000")
public class ManagmentAccountController {

    @Autowired
    private AccountManagement accountManagement;

    @Autowired
    private TokenRedisUtil tokenRedisUtil;

    @ExceptionHandler(ConstraintViolationException.class)
    public void handleValidationExceptions(ConstraintViolationException ex) {
        System.out.println("Service层数据格式校验未通过");
    }


    /**
     * 查看基础账户（列表）
     * @param token 管理员账户当前token
     * @return 成功，返回普通用户账户列表；失败，返回failure
     */
    @PostMapping(value = "selectBasicList")
    @ResponseBody
    Object selectBasicList(@RequestParam String token){

        Map<String,String> result = new HashMap<>();

        Map<String,String> account = tokenRedisUtil.tokenCheck(token);

        if(account !=null && account.get("authority") == "0"){

            System.out.println("权限泄漏");

            result.put("res","failure");

            return result;
        }

        return accountManagement.readAccountList((byte) 0);
    }


    /**
     * 重置基础账户密码（特定）
     * @param token 管理员账户当前token
     * @param id 待重置密码的基础账户id
     * @return 修改成功，返回success；修改失败/违规操作，返回failure
     * @throws IOException
     * @throws SQLException
     */
    @PostMapping(value = "updateBasicPassword")
    @ResponseBody
    Object updateBasicPassword(@RequestParam String token, @RequestParam String id) throws IOException, SQLException {

        Map<String,String> result = new HashMap<>();

        Map<String,String> account = tokenRedisUtil.tokenCheck(token);

        if(account !=null && account.get("authority") == "0"){

            System.out.println("拦截：权限泄漏");

            result.put("res","failure");
            return result;
        }
        if(accountManagement.readAccountAuthority(id) != (byte) 0){

            System.out.println("拦截：管理员越权操作");

            result.put("res","failure");
            return result;
        }
        boolean judge = accountManagement.updateAccountPassword(id,"0000000000");

        if(judge){

            result.put("res","success");
            return result;
        }
        result.put("res","failure");
        return result;
    }


    /**
     * 重置基础账户容量（特定）
     * @param token 管理员账户当前token
     * @param id 待重置容量的基础账户id
     * @param empty 重置容量空间
     * @return 修改成功，返回success；修改失败/违规操作，返回failure
     * @throws IOException
     * @throws SQLException
     */
    @PostMapping(value = "updateBasicEmpty")
    @ResponseBody
    Object updateBasicEmpty(@RequestParam String token, @RequestParam String id, @RequestParam int empty) throws IOException, SQLException {

        Map<String,String> result = new HashMap<>();

        Map<String,String> account = tokenRedisUtil.tokenCheck(token);

        if(account !=null && account.get("authority") == "0"){

            System.out.println("拦截：权限泄漏");

            result.put("res","failure");
            return result;
        }
        if(accountManagement.readAccountAuthority(id) != (byte) 0){

            System.out.println("拦截：管理员越权操作");

            result.put("res","failure");
            return result;
        }
        boolean judge = accountManagement.updateAccountEmpty(id,empty);

        if(judge){

            result.put("res","success");
            return result;
        }
        result.put("res","failure");
        return result;
    }


    /**
     * 注销基础账户（特定）
     * @param token 管理员账户当前token
     * @param id 待注销的基础账户id
     * @return 注销成功，返回success；注销失败/违规操作，返回failure
     * @throws IOException
     * @throws SQLException
     */
    @PostMapping(value = "deleteBasicAccount")
    @ResponseBody
    Object deleteBasicAccount(@RequestParam String token, @RequestParam String id) throws IOException, SQLException {

        Map<String,String> result = new HashMap<>();

        Map<String,String> account = tokenRedisUtil.tokenCheck(token);

        if(account !=null && account.get("authority") == "0"){

            System.out.println("拦截：权限泄漏");

            result.put("res","failure");
            return result;
        }
        if(accountManagement.readAccountAuthority(id) != (byte) 0){

            System.out.println("拦截：管理员越权操作");

            result.put("res","failure");
            return result;
        }
        boolean judge = accountManagement.updateAccountDeleted(id,(byte) 0);

        if(judge){

            result.put("res","success");
            return result;
        }
        result.put("res","failure");
        return result;
    }

}
