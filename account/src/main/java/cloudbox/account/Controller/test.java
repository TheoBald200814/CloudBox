package cloudbox.account.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    @RequestMapping(value = "test")
    String test(){
        return "test_succesdsdsdssdsds";
    }
}
