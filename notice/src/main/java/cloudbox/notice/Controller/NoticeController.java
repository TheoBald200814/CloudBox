package cloudbox.notice.Controller;


import cloudbox.notice.Service.NoticeManagement;
import cloudbox.notice.ServiceImpl.TokenRedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * 公告控制类
 * @author TheoBald
 * @version 0.0.1
 */
@RestController
@CrossOrigin(value = "http://localhost:3000")
public class NoticeController {
    @Autowired
    private NoticeManagement noticeManagement;
    @Autowired
    private TokenRedisUtil tokenRedisUtil;

    /**
     * 公告广播控制器
     * @return 全体公告
     */
    @PostMapping(value = "readNoticeAll")
    @ResponseBody
    Object readNoticeAll(){
        return noticeManagement.readNoticeAll();
    }

    /**
     * 公告创建控制器
     * @param token 账户令牌
     * @param noticeTitle 公告标题
     * @param noticeData 公告内容
     * @return 若创建成功，返回success；若创建失败，返回failure
     */
    @PostMapping(value = "createNotice")
    @ResponseBody
    Object createNotice(String token, String noticeTitle, String noticeData) {
        Map<String,String> account = tokenRedisUtil.tokenCheck(token);
        //token校验检查
        Map<String,String> result = new HashMap<>();
        if (account != null && "1".equals(account.get("authority"))) {
            //若校验成功且该账户为管理员
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            //设置生成时间
            boolean judge = noticeManagement.createNotice(account.get("accountId"), noticeTitle, noticeData, timestamp, true, null);
            //创建公告
            if (judge) {
                //若创建成功
                System.out.println("公告创建成功");
                result.put("res","success");
                return result;
            } else {
                //若创建失败
                System.out.println("公告创建失败[SQL]");
                result.put("res","failure");
                return result;
            }
        } else {
            //若校验失败或账户权限不够
            System.out.println("公告创建失败[账户/权限]");
            result.put("res","failure");
            return result;
        }
    }

    /**
     * 公告修改控制器
     * @param token 账户令牌
     * @param noticeTitle 公告标题
     * @param noticeData 待修改的公告内容
     * @return 若修改成功，返回success；若修改失败，返回failure
     */
    @PostMapping(value = "updateNoticeData")
    @ResponseBody
    Object updateNoticeData(String token, String noticeTitle, String noticeData){
        Map<String,String> account = tokenRedisUtil.tokenCheck(token);
        //token校验检查
        Map<String,String> result = new HashMap<>();
        if (account != null && "1".equals(account.get("accountId"))) {
            //若校验成功且该账户为管理员
            boolean judge = noticeManagement.updateNoticeData(account.get("accountId"), noticeTitle, noticeData);
            //修改公告
            if (judge) {
                //若修改成功
                System.out.println("公告修改成功");
                result.put("res","success");
                return result;
            } else {
                //若修改失败
                System.out.println("公告修改失败[SQL]");
                result.put("res","failure");
                return result;
            }
        } else {
            //若校验失败或账户权限不够
            System.out.println("公告修改失败[账户/权限]");
            result.put("res","failure");
            return result;
        }
    }

    /**
     * 公告有效位修改控制器
     * @param token 账户令牌
     * @param noticeTitle 公告标题
     * @param noticeDeleted 待修改的公告有效位
     * @return 若修改成功，返回success；若修改失败，返回failure
     */
    @PostMapping(value = "updateNoticeDeleted")
    @ResponseBody
    Object updateNoticeDeleted(String token, String noticeTitle, boolean noticeDeleted) {
        Map<String,String> account = tokenRedisUtil.tokenCheck(token);
        //token校验检查
        Map<String,String> result = new HashMap<>();
        if (account != null && "1".equals(account.get("accountId"))) {
            //若校验成功且该账户为管理员
            boolean judge = noticeManagement.updateNoticeDeleted(account.get("accountId"), noticeTitle, noticeDeleted);
            //修改公告有效位
            if (judge) {
                //若修改成功
                System.out.println("公告有效位修改成功");
                result.put("res","success");
                return result;
            } else {
                //若修改失败
                System.out.println("公告有效位修改失败[SQL]");
                result.put("res","failure");
                return result;
            }
        } else {
            //若校验失败或账户权限不够
            System.out.println("公告有效位修改失败[账户/权限]");
            result.put("res","failure");
            return result;
        }
    }

}
