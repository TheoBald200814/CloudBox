package cloudbox.file.Bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Timestamp;


/**
 * 文件实体类，用于处理MySQL中CloudBox库file表数据。
 * @author TheoBald
 * @version 0.0.1
 */
@TableName("file")
public class File {

    private String fileName;
    //文件名（1-20位字符串，主键非空）

    @TableId(type = IdType.INPUT)
    private String fileId;
    //文件Id（10-30位字符串，外键非空）

    private long fileSize;
    //文件大小（非空）

    private String fileType;
    //文件类型（1-10位字符串，非空）

    private Timestamp fileDate;
    //更新日期（日期格式，非空）

    private int downloadCount;
    //下载次数（非空，默认0）

    private String fileUrl;
    //文件路径（1-255位字符串，非空）

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Timestamp getFileDate() {
        return fileDate;
    }

    public void setFileDate(Timestamp fileDate) {
        this.fileDate = fileDate;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }



}
