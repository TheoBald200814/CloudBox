package cloudbox.file.Controller;


import cloudbox.file.Bean.File;
import cloudbox.file.Service.FileManagement;
import cloudbox.file.ServiceImpl.TokenRedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FileController {

    @Autowired
    private FileManagement fileManagement;

    @Autowired
    private TokenRedisUtil tokenRedisUtil;


    /**
     * 新建文件控制器
     * @param token 账户令牌
     * @param fileName 文件名
     * @param file 文件数据
     * @return 若新建成功，返回文件success-url；若新建失败，返回failure-null
     * @throws IOException
     */
    @PostMapping(value = "createFile")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    Object createFile(@RequestParam String token, @RequestParam String fileName, @RequestParam MultipartFile file) throws IOException {

        Map<String,String> account = tokenRedisUtil.tokenCheck(token);
        //身份校验
        Map<String,String> result = new HashMap<>();
        //响应结果
        if(account != null){
            //若查有此人
            String fileId = account.get("accountId");
            //获取账户Id（文件Id）
            long fileSize = file.getSize();
            //获取文件大小
            String [] fileTypeAndName = fileName.split("\\.");
            //获取文件类型
            Timestamp nowTime = new Timestamp(System.currentTimeMillis());
            //获取当前系统时间戳
            String url = fileManagement.createFile(fileTypeAndName[0],fileId,fileSize,fileTypeAndName[1],nowTime,0,file);
            //创建FCB，保存文件至分配空间，返回url
            if(url != null){
                //如果创建成功
                result.put("res","success");
                result.put("url",url);
            }else {
                //如果创建失败
                result.put("res","failure");
                result.put("url",null);
            }
            return result;
        }else {
            //若查无此人
            result.put("res","failure");
            result.put("url",null);
            return result;
        }
    }


    /**
     * 更新文件名控制器
     * @param token 账户令牌
     * @param fileName 文件名
     * @param newFileName 新文件名
     * @return 若更新成功，返回success；若更新失败，返回failure
     */
    @PostMapping(value = "updateFileName")
    @ResponseBody
    @CrossOrigin
    Object updateFileName(@RequestParam String token, @RequestParam String fileName, @RequestParam String newFileName){

        Map<String,String> account = tokenRedisUtil.tokenCheck(token);
        //身份校验
        Map<String,String> result = new HashMap<>();
        //响应结果
        if(account != null){
            //若查有此人
            String fileId = account.get("accountId");
            //获取账户Id（文件Id）
            boolean judgeFileName = fileManagement.updateFileName(fileName,fileId,newFileName);
            //修改文件名
            Timestamp nowTime = new Timestamp(System.currentTimeMillis());
            //获取当前系统时间戳
            boolean judgeFileDate = fileManagement.updateFileDate(fileName,fileId,nowTime);
            //修改文件更新日期
            if(judgeFileName && judgeFileDate){
                //若修改成功
                result.put("res","success");
            }else {
                //若修改失败
                result.put("res","failure");
            }
            return result;
        }else {
            //若查无此人
            result.put("res","failure");
            return result;
        }
    }


    /**
     * 更新文件类型控制器
     * @param token 账户令牌
     * @param fileName 文件名
     * @param newFileType 新文件类型
     * @return 若更新成功，返回success；若更新失败，返回failure
     * @return
     */
    @PostMapping(value = "updateFileType")
    @ResponseBody
    @CrossOrigin
    Object updateFileType(@RequestParam String token, @RequestParam String fileName, @RequestParam String newFileType){

        Map<String,String> account = tokenRedisUtil.tokenCheck(token);
        //身份校验
        Map<String,String> result = new HashMap<>();
        //响应结果
        if(account != null){
            //若查有此人
            String fileId = account.get("accountId");
            //获取账户Id（文件Id）
            boolean judgeFileType = fileManagement.updateFileType(fileName,fileId,newFileType);
            //修改文件名
            Timestamp nowTime = new Timestamp(System.currentTimeMillis());
            //获取当前系统时间戳
            boolean judgeFileDate = fileManagement.updateFileDate(fileName,fileId,nowTime);
            //修改文件更新日期
            if(judgeFileType && judgeFileDate){
                //若修改成功
                result.put("res","success");
            }else {
                //若修改失败
                result.put("res","failure");
            }
            return result;
        }else {
            //若查无此人
            result.put("res","failure");
            return result;
        }
    }


    /**
     * 文件下载控制器
     * @param token 账户令牌
     * @param fileName 文件名
     * @param response 下载响应
     * @throws IOException
     */
    @PostMapping(value = "DownloadFile")
    @CrossOrigin
    void DownloadFile(@RequestParam String token, @RequestParam String fileName, HttpServletResponse response) throws IOException {

        Map<String,String> account = tokenRedisUtil.tokenCheck(token);
        //身份校验
        if(account != null){
            //若查有此人
            String fileId = account.get("accountId");
            //获取账户Id（文件Id）
            fileManagement.downloadFile(fileName,fileId,response);
            //文件下载
            int count = fileManagement.readFileDownloadCount(fileName,fileId);
            //获取原始下载次数
            fileManagement.updateFileDownloadCount(fileName,fileId,++count);
            //更新下载次数
        }
    }


    /**
     * 查看个人文件列表控制器
     * @param token 账户令牌
     * @return 若检查成功，返回文件列表；若检查失败，返回失败信息res-failure
     */
    @PostMapping(value = "readFileList")
    @ResponseBody
    @CrossOrigin
    Object readFileList(@RequestParam String token){

        Map<String,String> account = tokenRedisUtil.tokenCheck(token);
        //身份校验
        if(account != null){
            //若查有此人
            String fileId = account.get("accountId");
            //获取账户Id（文件Id）
            return fileManagement.readFileList(fileId);
            //返回文件列表
        }else {
            //若查无此人
            Map<String,String> result = new HashMap<>();
            result.put("res","failure");
            return result;
            //返回失败响应
        }
    }


    /**
     * 删除文件控制器
     * @param token 账户令牌
     * @param fileName 待删除文件名
     * @return 若删除成功，返回res-success；若删除失败，返回res-failure
     * @throws IOException
     */
    @PostMapping(value = "deleteFile")
    @ResponseBody
    @CrossOrigin
    Object deleteFile(@RequestParam String token, @RequestParam String fileName) throws IOException {

        Map<String,String> account = tokenRedisUtil.tokenCheck(token);
        //身份校验
        Map<String,String> result = new HashMap<>();
        //响应结果
        if(account != null){
            //若查有此人
            String fileId = account.get("accountId");
            //获取账户Id（文件Id）
            boolean judge = fileManagement.deleteFCB(fileName,fileId);
            //删除FCB及文件
            if(judge){
                result.put("res","sccuess");
                return result;
            }else {
                result.put("res","failure");
                return result;
            }
        }else {
            //若查无此人
            result.put("res","failure");
            return result;
        }
    }

}
