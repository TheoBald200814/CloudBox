package cloudbox.file.ServiceImpl;

import cloudbox.file.Bean.FileShareInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class ShareRedisUtil {

    @Resource(name = "ShareRedisTemplate")
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 共享链接查看
     * @param link 共享链接
     * @return 文件共享元数据包
     */
    FileShareInfo checkLink(String link) {
        Map<Object, Object> result = redisTemplate.opsForHash().entries(link);
        FileShareInfo fileShareInfo = new FileShareInfo();
        fileShareInfo.setLink(link);
        fileShareInfo.setFileID(result.get("fileID").toString());
        fileShareInfo.setFileName(result.get("fileName").toString());
//        fileShareInfo.setUrl(result.get("url").toString());
        fileShareInfo.setLifeType(result.get("lifeType").toString());
        fileShareInfo.setTimeOne(result.get("timeOne").toString());
        fileShareInfo.setTimeTwo(result.get("timeTwo").toString());
        fileShareInfo.setTimeThree(result.get("timeThree").toString());
        return fileShareInfo;
    }

    /**
     * 共享链接插入
     * @param fileShareInfo 共享链接及其元数据
     * @return 若插入成功，返回true；若插入失败，返回false；
     */
    void setLink(FileShareInfo fileShareInfo) {
        Map<String, String> hashValue = new HashMap<>();
        hashValue.put("fileID", fileShareInfo.getFileID());
        hashValue.put("fileName", fileShareInfo.getFileName());
        hashValue.put("url", fileShareInfo.getUrl());
        hashValue.put("lifeType", fileShareInfo.getLifeType());
        hashValue.put("timeOne", fileShareInfo.getTimeOne());
        hashValue.put("timeTwo", fileShareInfo.getTimeTwo());
        hashValue.put("timeThree", fileShareInfo.getTimeThree());
        redisTemplate.opsForHash().putAll(fileShareInfo.getLink(), hashValue);
        //TODO 生命周期策略
        if ("1".equals(fileShareInfo.getLifeType())) {
            //若该链接为次数受限型
            redisTemplate.opsForHash().getOperations().expire(fileShareInfo.getLink(),30, TimeUnit.DAYS);
            //设置其生命周期为30天
        } else {
            //若该链接为时间受限类型
            if ("minute".equals(fileShareInfo.getTimeTwo())) {
                //若时间类型为分钟
                Integer minutes = Integer.getInteger(fileShareInfo.getTimeOne());
                redisTemplate.opsForHash().getOperations().expire(fileShareInfo.getLink(), minutes, TimeUnit.MINUTES);
            } else {
                if ("day".equals(fileShareInfo.getTimeTwo())) {
                    //若时间类型为天
                    Integer days = Integer.getInteger(fileShareInfo.getTimeOne());
                    redisTemplate.opsForHash().getOperations().expire(fileShareInfo.getLink(), days, TimeUnit.DAYS);
                } else {
                    //若时间类型为月
                    Integer months = Integer.getInteger(fileShareInfo.getTimeOne());
                    redisTemplate.opsForHash().getOperations().expire(fileShareInfo.getLink(), months * 30, TimeUnit.DAYS);
                }
            }
        }

    }

    /**
     * 共享链接删除
     * @param link 共享链接
     * @return 若删除成功，返回true；若删除失败，返回false；
     */
    void deleteLink(String link) {
        redisTemplate.opsForHash().getOperations().expire(link, 1, TimeUnit.SECONDS);
    }

}
