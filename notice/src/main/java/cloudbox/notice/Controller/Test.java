package cloudbox.notice.Controller;


import cloudbox.notice.Bean.Notice;
import cloudbox.notice.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@CrossOrigin(value = "http://localhost:3000")
public class Test {

    @Autowired
    private Mapper.NoticeMapper noticeMapper;



    @PostMapping(value = "test")
    @ResponseBody
    Object test(){

        return null;


    }
}
