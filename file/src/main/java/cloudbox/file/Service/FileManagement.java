package cloudbox.file.Service;


import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * 文件管理接口
 * @author TheoBald
 * @version 0.0.2
 */
@Service
public interface FileManagement {

    /**
     * 创建文件控制块（FCB）
     * @param fileName 文件名
     * @param fileId 文件Id（所有者）
     * @param fileSize 文件大小
     * @param fileType 文件类型
     * @param fileDate 更新日期
     * @param downloadCount 下载次数
     * @return 文件url
     */
    String createFile(@NotBlank @Size(max = 20, min = 1) String fileName,
                      @NotBlank @Size(max = 30, min = 10) String fileId,
                      @NotNull long fileSize,
                      @NotBlank @Size(max = 10, min = 1) String fileType,
                      @NotNull Timestamp fileDate,
                      @NotNull int downloadCount,
                      @NotBlank String url);


    /**
     * 文件名存在性检查
     * @param fileName 文件名
     * @param fileId 文件所有者
     * @return 文件名查询结果：查询成功返回文件名；查询失败返回null
     */
    String readFileName(@NotBlank @Size(max = 20, min = 1) String fileName,
                        @NotBlank @Size(max = 30, min = 10) String fileId);


    /**
     * 文件Id存在性检查
     * @param fileName 文件名
     * @param fileId 文件所有者
     * @return 文件Id查询结果：查询成功返回文件Id；查询失败返回null
     */
    String readFileId(@NotBlank @Size(max = 20, min = 1) String fileName,
                      @NotBlank @Size(max = 30, min = 10) String fileId);


    /**
     * 文件大小检查
     * @param fileName 文件名
     * @param fileId 文件所有者
     * @return 查询成功，返回文件大小；查询失败，返回-1
     */
    long readFileSize(@NotBlank @Size(max = 20, min = 1) String fileName,
                      @NotBlank @Size(max = 30, min = 10) String fileId);


    /**
     * 文件类型检查
     * @param fileName 文件名
     * @param fileId 文件所有者
     * @return 查询成功，返回文件类型；查询失败，返回null
     */
    String readFileType(@NotBlank @Size(max = 20, min = 1) String fileName,
                        @NotBlank @Size(max = 30, min = 10) String fileId);


    /**
     * 文件更新日期检查
     * @param fileName 文件名
     * @param fileId 文件所有者
     * @return 查询成功，返回文件更新日期；查询失败，返回null
     */
    Timestamp readFileDate(@NotBlank @Size(max = 20, min = 1) String fileName,
                           @NotBlank @Size(max = 30, min = 10) String fileId);


    /**
     * 文件下载次数检查
     * @param fileName 文件名
     * @param fileId 文件所有者
     * @return 查询成功，返回文件下载次数；查询失败，返回-1
     */
    int readFileDownloadCount(@NotBlank @Size(max = 20, min = 1) String fileName,
                              @NotBlank @Size(max = 30, min = 10) String fileId);


    /**
     * 文件存储路径检查
     * @param fileName 文件名
     * @param fileId 文件所有者
     * @return 查询成功，返回文件存储路径；查询失败，返回null
     */
    String readFileURL(@NotBlank @Size(max = 20, min = 1) String fileName,
                       @NotBlank @Size(max = 30, min = 10) String fileId);


    /**
     * 更新文件名
     * @param fileName 文件名
     * @param fileId 文件所有者
     * @param newFileName 新文件名
     * @return 更新成功返回true；更新失败返回false
     */
    boolean updateFileName(@NotBlank @Size(max = 20, min = 1) String fileName,
                           @NotBlank @Size(max = 30, min = 10) String fileId,
                           @NotBlank @Size(max = 20, min = 1) String newFileName);


    /**
     * 更新文件大小
     * @param fileName 文件名
     * @param fileId 文件所有者
     * @param newFileSize 新文件大小
     * @return 更新成功返回true；更新失败返回false
     */
    boolean updateFileSize(@NotBlank @Size(max = 20, min = 1) String fileName,
                           @NotBlank @Size(max = 30, min = 10) String fileId,
                           @NotNull long newFileSize);


    /**
     * 更新文件类型
     * @param fileName 文件名
     * @param fileId 文件所有者
     * @param newFileType 新文件类型
     * @return 更新成功返回true；更新失败返回false
     */
    boolean updateFileType(@NotBlank @Size(max = 20, min = 1) String fileName,
                           @NotBlank @Size(max = 30, min = 10) String fileId,
                           @NotBlank @Size(max = 10, min = 1) String newFileType);


    /**
     * 更新文件日期
     * @param fileName 文件名
     * @param fileId 文件所有者
     * @param newFileDate 新文件日期
     * @return 更新成功返回true；更新失败返回false
     */
    boolean updateFileDate(@NotBlank @Size(max = 20, min = 1) String fileName,
                           @NotBlank @Size(max = 30, min = 10) String fileId,
                           @NotNull Timestamp newFileDate);


    /**
     * 更新文件下载次数
     * @param fileName 文件名
     * @param fileId 文件所有者
     * @param newFileDownloadCount 新文件下载次数
     * @return 更新成功返回true；更新失败返回false
     */
    boolean updateFileDownloadCount(@NotBlank @Size(max = 20, min = 1) String fileName,
                                    @NotBlank @Size(max = 30, min = 10) String fileId,
                                    @NotNull int newFileDownloadCount);


    /**
     * 更新文件路径
     * @param fileName 文件名
     * @param fileId 文件所有者
     * @param newFileDownloadCount 新文件下载次数
     * @return 更新成功返回true；更新失败返回false
     */
    boolean updateFileURL(@NotBlank @Size(max = 20, min = 1) String fileName,
                          @NotBlank @Size(max = 30, min = 10) String fileId,
                          @NotBlank String newFileDownloadCount);


    /**
     * 删除文件控制块（FCB）
     * @param fileName 文件名
     * @param fileId 文件所有者
     * @return 删除成，返回true；删除失败，返回false
     */
    boolean deleteFCB(@NotBlank @Size(max = 20, min = 1) String fileName,
                      @NotBlank @Size(max = 30, min = 10) String fileId);

}
