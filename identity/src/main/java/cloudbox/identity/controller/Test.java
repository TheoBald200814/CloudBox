package cloudbox.identity.controller;

import cloudbox.identity.entity.Account;
import cloudbox.identity.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Test {

    @Autowired
    private IdentityService identityService;

    @PostMapping("testRPC")
    @ResponseBody
    Object testRPC(@RequestBody Account account) {

        return identityService.userLogin(account.getAccountId(),account.getPassword());


    }
}
