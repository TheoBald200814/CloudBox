package cloudbox.account.Controller;


import cloudbox.account.Service.AccountManagement;
import cloudbox.account.Service.ServiceImpl.TokenRedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(value = "http://localhost:3000")
public class SeniorManagementAccountController {

    @Autowired
    private AccountManagement accountManagement;

    @Autowired
    private TokenRedisUtil tokenRedisUtil;


    /**
     * 查看管理员账户（列表）
     * @param token 超级管理员账户当前token
     * @return 成功，返回管理员账户列表；失败，返回failure
     */
    @PostMapping(value = "selectManagementList")
    @ResponseBody
    Object selectManagementList(@RequestParam String token){

        Map<String,String> result = new HashMap<>();

        Map<String,String> account = tokenRedisUtil.tokenCheck(token);

        if(account !=null && account.get("authority") != "2"){

            System.out.println("拦截：权限泄漏");

            result.put("res","failure");
            return result;
        }
        return accountManagement.readAccountList((byte) 1);
    }


    /**
     * 重置管理员账户密码（特定）
     * @param token 超级管理员账户当前token
     * @param id 待重置的管理员账户id
     * @return 成功，返回success；失败，返回failure
     */
    @PostMapping(value = "updateManagementPassword")
    @ResponseBody
    Object updateManagementPassword(@RequestParam String token, @RequestParam String id){

        Map<String,String> result = new HashMap<>();

        Map<String,String> account = tokenRedisUtil.tokenCheck(token);

        if(account !=null && account.get("authority") != "2"){

            System.out.println("拦截：权限泄漏");

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
     * 注销管理员账户（特定）
     * @param token 超级管理员账户当前token
     * @param id 待注销的管理员账户id
     * @return 成功，返回success；失败，返回failure
     */
    @PostMapping(value = "deleteManagementAccount")
    @ResponseBody
    Object deleteManagementAccount(@RequestParam String token, @RequestParam String id){

        Map<String,String> result = new HashMap<>();

        Map<String,String> account = tokenRedisUtil.tokenCheck(token);

        if(account !=null && account.get("authority") != "2"){

            System.out.println("拦截：权限泄漏");

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


    /**
     * 修改账户权限（00/01）
     * @param token 超级管理员账户当前token
     * @param id 待修改权限的管理员账户id
     * @param authority 权限（00/01/10）
     * @return 成功，返回success；失败，返回failure
     */
    @PostMapping(value = "updateAuthority")
    @ResponseBody
    Object updateAuthority(@RequestParam String token, @RequestParam String id, @RequestParam byte authority){

        Map<String,String> result = new HashMap<>();

        Map<String,String> account = tokenRedisUtil.tokenCheck(token);

        if(account !=null && account.get("authority") != "2"){

            System.out.println("拦截：权限泄漏");

            result.put("res","failure");
            return result;
        }
        boolean judge = accountManagement.updateAccountAuthority(id,authority);

        if(judge){

            result.put("res","success");
            return result;
        }
        result.put("res","failure");
        return result;
    }

}
