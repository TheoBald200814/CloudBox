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
 * @version 0.0.2
 */
@RestController
@CrossOrigin(value = "http://localhost:3000")
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
     * @param box 注册账户数据箱（包含账户id、账户密码、账户昵称（选））
     * @return 成功返回sucess；失败返回failure
     */
    @PostMapping(value = "createAccount")
    @ResponseBody
    @CrossOrigin(value = "http://localhost:3000")
    Object createAccount(@RequestBody Map<String,String> box) throws IOException, SQLException {

        Map<String,String> result = new HashMap<>();
        //反馈结果
        String id = box.get("accountId");
        //拆箱账户Id
        String password = box.get("password");
        //拆箱账户密码
        String nickname = box.get("nickname");
        //拆箱账户昵称（如果有）
        boolean judge = accountManagement.createAccount(id,password,nickname,null);
        //账户注册
        if(judge){
            //若注册成功
            result.put("res","success");
            return result;
        }
        //若注册失败
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
    @CrossOrigin(value = "http://localhost:3000")
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
    @CrossOrigin(value = "http://localhost:3000")
    Object deleteAccount(@RequestParam String token){

        Map<String,String> result = new HashMap<>();

        Map<String,String> account = tokenRedisUtil.tokenCheck(token);

        if(account != null){

            boolean judge = accountManagement.updateAccountDeleted(account.get("accountId"),(byte) 0);

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
     * @param box 数据包
     * @return 修改成功返回sucess；失败返回failure
     */
    @PostMapping(value = "updatePassword")
    @ResponseBody
    @CrossOrigin(value = "http://localhost:3000")
    Object updatePassword(@RequestBody Map<String,String> box){

        String token = box.get("token");

        String password = box.get("password");

        Map<String,String> result = new HashMap<>();

        Map<String,String> account = tokenRedisUtil.tokenCheck(token);


        if(account != null){

            boolean judge = accountManagement.updateAccountPassword(account.get("accountId"),password);

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
     * @param box 数据包
     * @return 修改成功返回sucess；失败返回failure
     */
    @PostMapping(value = "updateNickname")
    @ResponseBody
    @CrossOrigin(value = "http://localhost:3000")
    Object updateNickname(@RequestBody Map<String,String> box){

        String token = box.get("token");
        String nickname =box.get("newNickname");

        Map<String,String> result = new HashMap<>();

        Map<String,String> account = tokenRedisUtil.tokenCheck(token);

        if(account != null){

            boolean judge = accountManagement.updateAccountNickname(account.get("accountId"),nickname);

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
    @CrossOrigin(value = "http://localhost:3000")
    Object updatePhoto(@RequestParam String token, @RequestParam MultipartFile photo) throws IOException, SQLException {

        Map<String,String> result = new HashMap<>();

        Map<String,String> account = tokenRedisUtil.tokenCheck(token);

        if(account != null){

            byte [] image = photo.getBytes();

            Blob imagBlob = new SerialBlob(image);

            boolean judge = accountManagement.updateAccountPhoto(account.get("accountId"),imagBlob);

            if(judge){
                result.put("res","success");
                return result;
            }
        }
        result.put("res","failure");
        return result;
    }



    @PostMapping(value = "tempLogin")
    @ResponseBody
    @CrossOrigin(value = "http://localhost:3000")
    Object tempLogin(@RequestBody Map<String,String> result) throws IOException, SQLException {

        String accountId = result.get("accountId");
        String passowrd = result.get("password");

        return  accountManagement.tempLogin(accountId,passowrd);
    }

}
