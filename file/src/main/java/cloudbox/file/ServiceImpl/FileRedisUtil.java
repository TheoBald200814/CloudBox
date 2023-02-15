package cloudbox.file.ServiceImpl;


import cloudbox.file.Bean.File;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Resource;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Component
public class FileRedisUtil {

    @Resource(name = "FileRedisTemplate")
    RedisTemplate<String, Object> redisTemplate;


    /**
     * FCB缓存插入
     * @param file FCB对象
     * @return 若更新成功，返回true；若更新失败，返回false
     */
    boolean set(File file) {

        Map<String,String> redisFile = new HashMap<>();
        //初始化HashMap
        redisFile.put("fileSize",String.valueOf(file.getFileSize()));
        //集合插入文件大小
        redisFile.put("fileType",file.getFileType());
        //集合插入文件类型
        redisFile.put("fileDownloadCount",String.valueOf(file.getDownloadCount()));
        //集合插入文件下载次数
        redisFile.put("fileUrl",file.getFileUrl());
        //集合插入文件路径
        redisFile.put("fileDate",String.valueOf(file.getFileDate()));
        //集合插入文件修改日期
        redisTemplate.opsForHash().putAll(file.getFileId() + "&%&" + file.getFileName(),redisFile);
        //FCB插入Redis 3号库
        redisTemplate.opsForHash().getOperations().expire(file.getFileId() + "&%&" + file.getFileName(),600, TimeUnit.SECONDS);
        //录入缓存（key：fileId + "&%&" + fileName，value：HashMap）

        if(redisTemplate.opsForHash().hasKey(file.getFileId() + "&%&" + file.getFileName(),"fileSize")){
            //存储成功
            return true;
        }else {
            //存储失败
            return false;
        }
    }


    /**
     * FCB缓存查找
     * @param fileId 账户Id
     * @return 若缓存命中，则返回该FCB对象；若缓存未命中，则返回null
     */
    public File get(String fileId, String fileName){

        Object file= redisTemplate.opsForHash().entries(fileId + "&%&" + fileName);
        //缓存查找
        LinkedHashMap<String,String> temp=(LinkedHashMap<String, String>) file;
        //类型转换
        if(temp.size()==0){
            //查找失败
            return null;
        }else{
            //查找成功
            redisTemplate.opsForHash().getOperations().expire(fileId + "&%&" + fileName,600,TimeUnit.SECONDS);
            //刷新该账户在缓存中的有效期
            File result = new File();
            //初始化账户对象
            result.setFileId(fileId);

            result.setFileName(fileName);

            result.setFileSize(Long.valueOf(temp.get("fileSize")));

            result.setFileType(temp.get("fileType"));

            result.setFileDate(Timestamp.valueOf(temp.get("fileDate")));

            result.setDownloadCount(Integer.valueOf(temp.get("fileDownloadCount")));

            result.setFileUrl(temp.get("fileUrl"));

            return result;
        }
    }


    /**
     * FCB缓存更新
     * @param fileId 文件Id（Redis中作为Key前半部分）
     * @param fileName 文件名（Redis中作为Key后半部分）
     * @param hashKey FCB属性（Redis中作为HashKey）
     * @param hashValue FCB属性值（Redis中作为HashValue）
     */
    void update(String fileId, String fileName, String hashKey, String hashValue){

        redisTemplate.opsForHash().put(fileId + "&%&" + fileName,hashKey,hashValue);
        //缓存查找
        redisTemplate.opsForHash().getOperations().expire(fileId + "&%&" + fileName,600,TimeUnit.SECONDS);
        //缓存有效期刷新
    }


    /**
     * FCB缓存删除
     * @param fileId 文件Id（Redis中作为Key前半部分）
     * @param fileName 文件名（Redis中作为Key后半部分）
     */
    void delete(String fileId, String fileName){

        redisTemplate.opsForHash().delete(fileId + "&%&" + fileName,"fileType","fileSize","fileUrl","fileDate","fileDownloadCount");
    }

}
