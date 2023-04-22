package cloudbox.identity.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Blob;

/**
 * 账户实体类
 * @author TheoBald
 * @version 0.0.1
 */
@Data
public class Account implements Serializable {

    /**
     * 账户Id（10-30位邮箱格式的字符串，主键非空）
     */
    private String accountId;

    /**
     * 账户密码（原始密码为10-30为字符串，MD5加密后为32为定长字符串，非空）
     */
    private String password;

    /**
     * 账户昵称（1-10位字符串，可空）
     */
    private String nickname;

    /**
     * 账户头像（二进制文件，大小限制待定）
     */
    private Blob photo;

    /**
     * 账户权限（采用2bit位记录：00为普通用户，01为管理员，10为超级管理员）
     */
    private byte authority;

    /**
     * 删除位/有效位（有效账户为1，注销账户为0）
     */
    private byte deleted;

    /**
     * 账户云盘容量（默认100000）
     */
    private int accountEmpty;

    /**
     * 登陆结果
     */
    private String res;
}
