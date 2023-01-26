package cloudbox.account.Controller;

import cloudbox.account.Service.AccountManagement;
import cloudbox.account.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.ConstraintViolationException;
import java.sql.Blob;

@RestController
public class test {

    @Autowired
    private AccountManagement accountManagement;

    @Autowired
    private Mapper.AccountMapper accountMapper;


    @RequestMapping(value = "test")
    String test() {

        String account_id="12345@qq.com";

//        System.out.println(accountManagement.readAccountId(account_id)+"||"+
//                        accountManagement.readAccountPassword(account_id)+"||"+
//                        accountManagement.readAccountNickname(account_id)+"||"+
//                        accountManagement.readAccountPhoto(account_id)+"||"+
//                        accountManagement.readAccountAuthority(account_id)+"||"+
//                        accountManagement.readAccountDeleted(account_id)+"||"+
//                        accountManagement.readAccountEmpty(account_id)+"||"
//
//                );
//        accountManagement.updateAccountNickname(account_id,"popopopp");
//        accountManagement.updateAccountAuthority(account_id,(byte) 3);
//        accountManagement.updateAccountDeleted(account_id,(byte) 0);
//        accountManagement.updateAccountEmpty(account_id,500);
        accountManagement.updateAccountPassword("32323@qq.com","dshudhu38833");


        return "abcd";
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public void handleValidationExceptions(ConstraintViolationException ex) {
        System.out.println("哈哈哈");
    }


}


