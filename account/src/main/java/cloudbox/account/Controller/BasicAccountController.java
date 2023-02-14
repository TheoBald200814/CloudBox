package cloudbox.account.Controller;

import cloudbox.account.Service.AccountManagement;
import cloudbox.account.Service.ServiceImpl.TokenRedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.sql.rowset.serial.SerialBlob;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础账户控制类
 * @author TheoBald
 * @version 0.0.1
 */
@RestController
public class BasicAccountController {

    @Autowired
    private AccountManagement accountManagement;

    @Autowired
    private TokenRedisUtil tokenRedisUtil;

    @ExceptionHandler(ConstraintViolationException.class)
    public void handleValidationExceptions(ConstraintViolationException ex) {
        System.out.println("Service层数据格式校验未通过");
    }

    /**
     * 账户创建控制器
     * @param id 账户id
     * @param password 账户密码
     * @param nickname 账户昵称
     * @param photo 账户头像
     * @return 成功返回sucess；失败返回failure
     */
    @PostMapping(value = "createAccount")
    @ResponseBody
    Object createAccount(@RequestParam String id, @RequestParam String password, String nickname, MultipartFile photo) throws IOException, SQLException {

        Map<String,String> result = new HashMap<>();

        Blob imageBlob = null;

        if(photo != null){

            byte [] image = photo.getBytes();

            imageBlob = new SerialBlob(image);

        }
        boolean judge = accountManagement.createAccount(id,password,nickname,imageBlob);

        if(judge){

            result.put("res","success");
            return result;
        }
        result.put("res","failure");
        return result;
    }


    /**
     * 账户登出控制器
     * @param token 账户当前token
     * @return 默认成功sucess
     */
    @PostMapping(value = "logoutAccount")
    @ResponseBody
    Object logoutAccount(@RequestParam String token){

        Map<String,String> result = new HashMap<>();

        tokenRedisUtil.tokenDelete(token);

        result.put("res","success");

        return result;
    }


    /**
     * 账户注销控制器
     * @param token 账户当前token
     * @return 注销成功返回sucess；失败返回failure
     */
    @PostMapping(value = "deleteAccount")
    @ResponseBody
    Object deleteAccount(@RequestParam String token){

        Map<String,String> result = new HashMap<>();

        Map<String,String> account = tokenRedisUtil.tokenCheck(token);

        if(account != null){

            boolean judge = accountManagement.updateAccountDeleted(account.get("account_id"),(byte) 0);

            if(judge){
                result.put("res","success");
                return result;
            }
        }
        result.put("res","failure");
        return result;
    }


    /**
     * 账户修改密码控制器
     * @param token 账户当前token
     * @param password 账户新密码
     * @return 修改成功返回sucess；失败返回failure
     */
    @PostMapping(value = "updatePassword")
    @ResponseBody
    Object updatePassword(@RequestParam String token, @RequestParam String password){

        Map<String,String> result = new HashMap<>();

        Map<String,String> account = tokenRedisUtil.tokenCheck(token);

        if(account != null){

            boolean judge = accountManagement.updateAccountPassword(account.get("account_id"),password);

            if(judge){
                result.put("res","success");
                return result;
            }
        }
        result.put("res","failure");
        return result;
    }


    /**
     * 账户修改昵称控制器
     * @param token 账户当前token
     * @param nickname 账户新昵称
     * @return 修改成功返回sucess；失败返回failure
     */
    @PostMapping(value = "updateNickname")
    @ResponseBody
    Object updateNickname(@RequestParam String token, @RequestParam String nickname){

        Map<String,String> result = new HashMap<>();

        Map<String,String> account = tokenRedisUtil.tokenCheck(token);

        if(account != null){

            boolean judge = accountManagement.updateAccountNickname(account.get("account_id"),nickname);

            if(judge){
                result.put("res","success");
                return result;
            }
        }
        result.put("res","failure");
        return result;
    }


    /**
     * 账户修改头像控制器
     * @param token 账户当前token
     * @param photo 账户新头像
     * @return 修改成功返回sucess；失败返回failure
     * @throws IOException
     * @throws SQLException
     */
    @PostMapping(value = "updatePhoto")
    @ResponseBody
    Object updatePhoto(@RequestParam String token, @RequestParam MultipartFile photo) throws IOException, SQLException {

        Map<String,String> result = new HashMap<>();

        Map<String,String> account = tokenRedisUtil.tokenCheck(token);

        if(account != null){

            byte [] image = photo.getBytes();

            Blob imagBlob = new SerialBlob(image);

            boolean judge = accountManagement.updateAccountPhoto(account.get("account_id"),imagBlob);

            if(judge){
                result.put("res","success");
                return result;
            }
        }
        result.put("res","failure");
        return result;
    }

}
