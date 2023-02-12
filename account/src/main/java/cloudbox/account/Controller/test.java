package cloudbox.account.Controller;

import cloudbox.account.Bean.Account;
import cloudbox.account.Service.ServiceImpl.AccountRedisUtil;
import cloudbox.account.Service.AccountManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

@RestController
public class test {

    @Autowired
    private AccountManagement accountManagement;





    @RequestMapping(value = "test")
    String test() throws IOException, SQLException {


        String temp=accountManagement.readAccountId ("10011@qq.com");
        System.out.println(temp);

        String temp1=accountManagement.readAccountPassword ("10011@qq.com");
        System.out.println(temp1);

        String temp2=accountManagement.readAccountNickname ("10011@qq.com");
        System.out.println(temp2);

        long temp3=accountManagement.readAccountEmpty ("10011@qq.com");
        System.out.println(temp3);

        byte temp4=accountManagement.readAccountAuthority("10011@qq.com");
        System.out.println(temp4);

        byte temp5=accountManagement.readAccountDeleted ("10011@qq.com");
        System.out.println(temp5);

        Blob temp6=accountManagement.readAccountPhoto("10011@qq.com");
        System.out.println(temp6);








        return "abcd";
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public void handleValidationExceptions(ConstraintViolationException ex) {
        System.out.println("Service层数据格式校验未通过");
    }


}


