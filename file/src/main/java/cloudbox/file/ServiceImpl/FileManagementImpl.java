package cloudbox.file.ServiceImpl;

import cloudbox.file.Bean.File;
import cloudbox.file.Bean.FileShareInfo;
import cloudbox.file.Service.FileManagement;
import cloudbox.file.mapper.Mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 文件管理服务实现类
 * @author TheoBald
 * @version 0.0.1
 */
@Service
@Validated
public class FileManagementImpl implements FileManagement {

    @Autowired
    private Mapper.FileMapper fileMapper;

    @Autowired
    private FileRedisUtil fileRedisUtil;

    @Autowired
    private FtpClientUtil ftpClientUtil;

    @Autowired
    private ShareRedisUtil shareRedisUtil;


    /**
     * 创建文件控制块（FCB）
     *
     * @param fileName      文件名
     * @param fileId        文件Id（所有者）
     * @param fileSize      文件大小
     * @param fileType      文件类型
     * @param fileDate      更新日期
     * @param downloadCount 下载次数
     * @param file          文件数据
     * @return 文件url
     */
    @Override
    public String createFile(@NotBlank @Size(max = 20, min = 1) String fileName,
                             @NotBlank @Size(max = 30, min = 10) String fileId,
                             @NotNull long fileSize,
                             @NotBlank @Size(max = 10, min = 1) String fileType,
                             @NotNull Timestamp fileDate,
                             @NotNull int downloadCount,
                             MultipartFile file) throws IOException {

        Object temp = fileMapper.selectOne(new QueryWrapper<File>().select("file_name").apply("file_name={0} and file_id={1}",fileName,fileId));
        //检查是否有相同FCB存在
        if(temp != null){
            //若存在相同FCB，则创建失败
            System.out.println("文件已存在");
            return null;
        }
        String fileToken = fileId + fileName;
        //生成文件令牌（待优化）
        String url = fileId + "/" + fileToken;
        //url结构：/文件所述用户/文件令牌
        File fcb = new File();

        fcb.setFileId(fileId);
        fcb.setFileName(fileName);
        fcb.setFileSize(fileSize);
        fcb.setFileDate(fileDate);
        fcb.setFileType(fileType);
        fcb.setDownloadCount(downloadCount);
        fcb.setFileUrl(url);
        //FCB装箱
        boolean uploadJudge = ftpClientUtil.uploadFile(file, fileId + "/",fileToken);
        //文件数据上传
        if(uploadJudge){
            //如果文件上传成功
            System.out.println(fileToken + ":文件上传成功");
            //文件上传成功
            int judgeSQL = fileMapper.insert(fcb);
            //FCB插入MySQL
            if(judgeSQL == 1){
                //如果SQL执行成功
                boolean judgeRedis = fileRedisUtil.set(fcb);
                //录入Redis
                if(judgeRedis){
                    //如果录入Redis成功，返回文件路径
                    return url;
                }else {
                    System.out.println("创建FCB-录入Redis失败");
                    return url;
                    //Redis录入失败
                }
            }else{
                //SQL执行失败
                System.out.println("创建FCB-录入MySQL失败");
                System.out.println("[严重：文件数据已上传，正在撤回文件数据，回收空间]");

                //TODO...文件撤回策略
                ftpClientUtil.deleteFile(url);
                return null;
            }
        }else {
            //文件上传失败
            return null;
        }
    }


    /**
     * 文件名存在性检查
     *
     * @param fileName 文件名
     * @param fileId   文件所有者
     * @return 文件名查询结果：查询成功返回文件名；查询失败返回null
     */
    @Override
    public String readFileName(@NotBlank @Size(max = 20, min = 1) String fileName,
                               @NotBlank @Size(max = 30, min = 10) String fileId) {

        File file = readFile(fileName,fileId);

        if(file == null){

            return null;
        }else{

            return file.getFileName();
        }
    }


    /**
     * 文件Id存在性检查
     *
     * @param fileName 文件名
     * @param fileId   文件所有者
     * @return 文件Id查询结果：查询成功返回文件Id；查询失败返回null
     */
    @Override
    public String readFileId(@NotBlank @Size(max = 20, min = 1) String fileName,
                             @NotBlank @Size(max = 30, min = 10) String fileId) {

        File file = readFile(fileName,fileId);

        if(file == null){

            return null;
        }else{

            return file.getFileId();
        }
    }


    /**
     * 文件大小检查
     *
     * @param fileName 文件名
     * @param fileId   文件所有者
     * @return 查询成功，返回文件大小；查询失败，返回-1
     */
    @Override
    public long readFileSize(@NotBlank @Size(max = 20, min = 1) String fileName,
                             @NotBlank @Size(max = 30, min = 10) String fileId) {

        File file = readFile(fileName,fileId);

        if(file == null){

            return -1;
        }else{

            return file.getFileSize();
        }
    }


    /**
     * 文件类型检查
     *
     * @param fileName 文件名
     * @param fileId   文件所有者
     * @return 查询成功，返回文件类型；查询失败，返回null
     */
    @Override
    public String readFileType(@NotBlank @Size(max = 20, min = 1) String fileName,
                               @NotBlank @Size(max = 30, min = 10) String fileId) {

        File file = readFile(fileName,fileId);

        if(file == null){

            return null;
        }else{

            return file.getFileType();
        }
    }


    /**
     * 文件更新日期检查
     *
     * @param fileName 文件名
     * @param fileId   文件所有者
     * @return 查询成功，返回文件更新日期；查询失败，返回null
     */
    @Override
    public Timestamp readFileDate(@NotBlank @Size(max = 20, min = 1) String fileName,
                                  @NotBlank @Size(max = 30, min = 10) String fileId) {

        File file = readFile(fileName,fileId);

        if(file == null){

            return null;
        }else{

            return file.getFileDate();
        }
    }


    /**
     * 文件下载次数检查
     *
     * @param fileName 文件名
     * @param fileId   文件所有者
     * @return 查询成功，返回文件下载次数；查询失败，返回-1
     */
    @Override
    public int readFileDownloadCount(@NotBlank @Size(max = 20, min = 1) String fileName,
                                     @NotBlank @Size(max = 30, min = 10) String fileId) {

        File file = readFile(fileName,fileId);

        if(file == null){

            return -1;
        }else{

            return file.getDownloadCount();
        }
    }


    /**
     * 文件存储路径检查
     *
     * @param fileName 文件名
     * @param fileId   文件所有者
     * @return 查询成功，返回文件存储路径；查询失败，返回null
     */
    @Override
    public String readFileURL(@NotBlank @Size(max = 20, min = 1) String fileName,
                              @NotBlank @Size(max = 30, min = 10) String fileId) {

        File file = readFile(fileName,fileId);

        if(file == null){

            return null;
        }else{

            return file.getFileUrl();
        }
    }


    /**
     * 文件列表检查
     * @param fileId 文件所有者
     * @return 文件所有者文件列表
     */
    @Override
    public List<File> readFileList(@NotBlank @Size(max = 30, min = 10) String fileId){

        return fileMapper.selectList(new QueryWrapper<File>().select("file_name","file_size","file_type","file_date","download_count").apply("file_id={0}",fileId));
    }


    /**
     * 文件下载
     * @param fileName 文件名
     * @param fileId 文件所有者
     * @param response 下载响应
     */
    @Override
    public void downloadFile(@NotBlank @Size(max = 20, min = 1) String fileName,
                             @NotBlank @Size(max = 30, min = 10) String fileId,
                             HttpServletResponse response) throws IOException {

        String url = readFileURL(fileName,fileId);
        //获取文件路径
        String [] temp = url.split("/");
        //解析文件路径
        ftpClientUtil.downloadFile(temp[1], temp[0] + "/", fileName, response);
    }


    /**
     * FCB查找
     * @param fileId 文件Id
     * @param fileName 文件名
     * @return 若FCB存在，则返回该FCB；若FCB不存在，则返回空
     */
    private File readFile(String fileName, String fileId) {

        File file =fileRedisUtil.get(fileId,fileName);

        if(file != null){
            //若Redis命中
            System.out.println("read-Redis命中");

            return file;
        }else {
            //若Redis未命中
            System.out.println("read-Redis未命中");

            file = fileMapper.selectOne(new QueryWrapper<File>().apply("file_id={0} and file_name={1}",fileId,fileName));
            //访问MySQL

            System.out.println("teetetee");

            if(file != null){
                //若MySQL命中
                System.out.println("read-MySQL命中");

                fileRedisUtil.set(file);
                //录入Redis
                return file;
            }else {
                //若MySQL也未命中
                System.out.println("read-MySQL未命中");

                return null;
            }
        }
    }


    /**
     * 更新文件名
     *
     * @param fileName    文件名
     * @param fileId      文件所有者
     * @param newFileName 新文件名
     * @return 更新成功返回true；更新失败返回false
     */
    @Override
    public boolean updateFileName(@NotBlank @Size(max = 20, min = 1) String fileName,
                                  @NotBlank @Size(max = 30, min = 10) String fileId,
                                  @NotBlank @Size(max = 20, min = 1) String newFileName) {

        if(fileRedisUtil.get(fileId, fileName) != null){
            //若该账户目前在Redis缓存中
            fileRedisUtil.update(fileId,fileName,"fileName",newFileName);
            //更新Redis缓存
        }

        UpdateWrapper<File> updateWrapper=new UpdateWrapper<File>();

        updateWrapper.eq("file_id",fileId).eq("file_name",fileName).set("file_name",newFileName);

        Integer rows=fileMapper.update(null,updateWrapper);
        //更新MySQL
        if(rows>0){

            return true;
        }else {

            return false;
        }
    }

    /**
     * 更新文件大小
     *
     * @param fileName    文件名
     * @param fileId      文件所有者
     * @param newFileSize 新文件大小
     * @return 更新成功返回true；更新失败返回false
     */
    @Override
    public boolean updateFileSize(@NotBlank @Size(max = 20, min = 1) String fileName,
                                  @NotBlank @Size(max = 30, min = 10) String fileId,
                                  @NotNull long newFileSize) {

        if(fileRedisUtil.get(fileId, fileName) != null){
            //若该账户目前在Redis缓存中
            fileRedisUtil.update(fileId,fileName,"fileSize",String.valueOf(newFileSize));
            //更新Redis缓存
        }

        UpdateWrapper<File> updateWrapper=new UpdateWrapper<File>();

        updateWrapper.eq("file_id",fileId).eq("file_name",fileName).set("file_size",newFileSize);

        Integer rows=fileMapper.update(null,updateWrapper);
        //更新MySQL
        if(rows>0){

            return true;
        }else {

            return false;
        }
    }

    /**
     * 更新文件类型
     *
     * @param fileName    文件名
     * @param fileId      文件所有者
     * @param newFileType 新文件类型
     * @return 更新成功返回true；更新失败返回false
     */
    @Override
    public boolean updateFileType(@NotBlank @Size(max = 20, min = 1) String fileName,
                                  @NotBlank @Size(max = 30, min = 10) String fileId,
                                  @NotBlank @Size(max = 10, min = 1) String newFileType) {

        if(fileRedisUtil.get(fileId, fileName) != null){
            //若该账户目前在Redis缓存中
            fileRedisUtil.update(fileId,fileName,"fileType",newFileType);
            //更新Redis缓存
        }

        UpdateWrapper<File> updateWrapper=new UpdateWrapper<File>();

        updateWrapper.eq("file_id",fileId).eq("file_name",fileName).set("file_type",newFileType);

        Integer rows=fileMapper.update(null,updateWrapper);
        //更新MySQL
        if(rows>0){

            return true;
        }else {

            return false;
        }
    }

    /**
     * 更新文件日期
     *
     * @param fileName    文件名
     * @param fileId      文件所有者
     * @param newFileDate 新文件日期
     * @return 更新成功返回true；更新失败返回false
     */
    @Override
    public boolean updateFileDate(@NotBlank @Size(max = 20, min = 1) String fileName,
                                  @NotBlank @Size(max = 30, min = 10) String fileId,
                                  @NotNull Timestamp newFileDate) {

        if(fileRedisUtil.get(fileId, fileName) != null){
            //若该账户目前在Redis缓存中
            fileRedisUtil.update(fileId,fileName,"fileDate",String.valueOf(newFileDate));
            //更新Redis缓存
        }

        UpdateWrapper<File> updateWrapper=new UpdateWrapper<File>();

        updateWrapper.eq("file_id",fileId).eq("file_name",fileName).set("file_date",newFileDate);

        Integer rows=fileMapper.update(null,updateWrapper);
        //更新MySQL
        if(rows>0){

            return true;
        }else {

            return false;
        }
    }

    /**
     * 更新文件下载次数
     *
     * @param fileName             文件名
     * @param fileId               文件所有者
     * @param newFileDownloadCount 新文件下载次数
     * @return 更新成功返回true；更新失败返回false
     */
    @Override
    public boolean updateFileDownloadCount(@NotBlank @Size(max = 20, min = 1) String fileName,
                                           @NotBlank @Size(max = 30, min = 10) String fileId,
                                           @NotNull int newFileDownloadCount) {

        if(fileRedisUtil.get(fileId, fileName) != null){
            //若该账户目前在Redis缓存中
            fileRedisUtil.update(fileId,fileName,"fileDownloadCount",String.valueOf(newFileDownloadCount));
            //更新Redis缓存
        }
        UpdateWrapper<File> updateWrapper=new UpdateWrapper<File>();

        updateWrapper.eq("file_id",fileId).eq("file_name",fileName).set("download_count",newFileDownloadCount);

        Integer rows=fileMapper.update(null,updateWrapper);
        //更新MySQL
        if(rows>0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 更新文件路径
     *
     * @param fileName             文件名
     * @param fileId               文件所有者
     * @param newFileUrl 新文件下载次数
     * @return 更新成功返回true；更新失败返回false
     */
    @Override
    public boolean updateFileURL(@NotBlank @Size(max = 20, min = 1) String fileName,
                                 @NotBlank @Size(max = 30, min = 10) String fileId,
                                 @NotBlank String newFileUrl) throws IOException {

        String url;

        File redisResult = fileRedisUtil.get(fileId,fileName);

        if(redisResult != null){
            //若该账户目前在Redis缓存中
            url =redisResult.getFileUrl();
            //暂存原始url
            fileRedisUtil.update(fileId,fileName,"fileUrl",String.valueOf(newFileUrl));
            //更新Redis缓存
        }else {
            //若Redis未命中FCB
            url = readFileURL(fileName,fileId);
            //从MySQL获取原始url
        }
        UpdateWrapper<File> updateWrapper=new UpdateWrapper<File>();
        updateWrapper.eq("file_id",fileId).eq("file_name",fileName).set("file_url",newFileUrl);

        Integer rows=fileMapper.update(null,updateWrapper);
        //更新MySQL
        if(rows>0){
            //如果MySQL更新成功
            ftpClientUtil.updateFileToken(url,newFileUrl);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 删除文件控制块（FCB）
     *
     * @param fileName 文件名
     * @param fileId   文件所有者
     * @return 删除成，返回true；删除失败，返回false
     */
    @Override
    public boolean deleteFCB(@NotBlank @Size(max = 20, min = 1) String fileName,
                             @NotBlank @Size(max = 30, min = 10) String fileId) throws IOException {

        File redisResult = fileRedisUtil.get(fileId,fileName);

        String url;

        if(redisResult != null){
            //若该账户目前在Redis缓存中
            url = redisResult.getFileUrl();
            //通过redisResult获取原始url
            fileRedisUtil.delete(fileId,fileName);
            //删除Redis缓存
        }else {
            //若Redis未命中FCB
            url = readFileURL(fileName,fileId);
            //从MySQL中获取原始url
        }
        int resultMySQL = fileMapper.delete(new QueryWrapper<File>().apply("file_id={0} and file_name={1}",fileId,fileName));
        //删除MySQL中的记录
        boolean resultFtp = ftpClientUtil.deleteFile(url);
        //删除文件数据
        if(resultMySQL == 1 && resultFtp){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 共享连接获取文件服务-下载
     *
     * @param link     链接
     * @param response 文件下载
     */
    @Override
    public void fileShareSericeDownload(String link, HttpServletResponse response) throws IOException {
        FileShareInfo fileShareInfo = shareRedisUtil.checkLink(link);
        String fileName = fileShareInfo.getFileName();
        String fileId = fileShareInfo.getFileID();
        downloadFile(fileName,fileId,response);
        //文件下载
        int count = readFileDownloadCount(fileName,fileId);
        //获取原始下载次数
        updateFileDownloadCount(fileName,fileId,++count);
        //更新下载次数

    }

    /**
     * 生成自己文件共享链接
     *
     * @param fileShareInfo    dto
     * @return 共享链接
     */
    @Override
    public String generateFileShareLink1(FileShareInfo fileShareInfo) {
        String fileId = fileShareInfo.getFileID();
        //获取账户ID（同时也是文件ID）
        fileShareInfo.setFileID(fileId);
        String param = fileShareInfo.getFileID() + fileShareInfo.getFileName() + "sdahwu2h8hhhP)(83u2n/.j" + System.currentTimeMillis();
        String link = DigestUtils.md5DigestAsHex(param.getBytes());
        //生成链接字符串
        fileShareInfo.setLink(link);
        shareRedisUtil.setLink(fileShareInfo);
        //文件共享链接录入Redis缓存
        return link;
    }

    /**
     * 共享连接获取文件服务-上传
     *
     * @param token 账户令牌
     * @param link  链接
     * @param file  待上传文件
     */
    @Override
    public void fileShareServiceUpdate(String token, String link, MultipartFile file) {

    }
}
