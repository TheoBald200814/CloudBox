package cloudbox.identity.controller;

import cloudbox.identity.entity.dto.Account;
import cloudbox.identity.entity.vo.ResponseAccount;
import cloudbox.identity.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 身份认证控制类
 * @author TheoBald
 * @version 0.0.1
 */
@RestController
@CrossOrigin(value = "http://localhost:3000")
public class IdentityController {

    @Autowired
    private IdentityService identityService;

    /**
     * 用户登陆接口
     * @param result 登陆数据包
     * @return 账户对象
     */
    @PostMapping("login")
    @ResponseBody
    Object login(@RequestBody Map<String,String> result) {
        Account account = identityService.userLogin(result.get("accountId"),result.get("password"));
        if (account != null) {
            account.setRes("success");
            return account;
        } else {
            Account account1 = new Account();
            account1.setRes("failure");
            return account1;
        }
    }





}
