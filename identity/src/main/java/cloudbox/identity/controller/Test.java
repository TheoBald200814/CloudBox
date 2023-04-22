package cloudbox.identity.controller;

import cloudbox.identity.entity.dto.Account;
import cloudbox.identity.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(value = "http://localhost:3000")
public class Test {

    @Autowired
    private IdentityService identityService;

    @PostMapping("testRPC")
    @ResponseBody
    Object testRPC(@RequestBody Account account) {

        return identityService.userLogin(account.getAccountId(),account.getPassword());

    }
}
