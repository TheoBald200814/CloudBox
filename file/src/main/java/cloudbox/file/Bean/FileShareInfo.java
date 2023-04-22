package cloudbox.file.Bean;

import lombok.Data;

/**
 * 文件共享元数据包
 * @author TheoBald
 * @version 0.0.1
 */
@Data
public class FileShareInfo {

    /**
     * 账户令牌
     */
    private String token;

    /**
     * 文件共享链接
     */
    private String link;

    /**
     * 文件ID
     */
    private String fileID;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件URL
     */
    private String url;

    /**
     * 生命周期类型
     */
    private String lifeType;

    /**
     * 周期一
     */
    private String timeOne;

    /**
     * 周期二
     */
    private String timeTwo;

    /**
     * 周期三
     */
    private String timeThree;

}
