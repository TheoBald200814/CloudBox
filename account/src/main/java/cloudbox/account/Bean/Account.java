package cloudbox.account.Bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.sql.Blob;


/**
 * 账户实体类，用于处理MySQL中CloudBox库account表数据。
 * @author TheoBald
 * @version 0.0.1
 */
@TableName("account")
public class Account {

    @TableId(type = IdType.INPUT)
    private String accountId;
    //账户Id（10-30位邮箱格式的字符串，主键非空）

    private String password;
    //账户密码（原始密码为10-30为字符串，MD5加密后为32为定长字符串，非空）

    private String nickname;
    //账户昵称（1-10位字符串，可空）

    private Blob photo;
    //账户头像（二进制文件，大小限制待定）

    private byte authority;
    //账户权限（采用2bit位记录：00为普通用户，01为管理员，10为超级管理员）

    private byte deleted;
    //删除位/有效位（有效账户为1，注销账户为0）

    private int accountEmpty;
    //账户云盘容量（默认100000）

    /**
     * 账户Id get方法
     * @return accountId(账户Id)
     */
    public String getId() {
        return accountId;
    }

    /**
     * 账户Id set方法
     * @param accountId（账户Id）
     */
    public void setId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * 账户密码 get方法
     * @return password（账户密码）
     */
    public String getPassword() {
        return password;
    }

    /**
     * 账户密码 set方法
     * @param password（账户密码）
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 账户昵称 get方法
     * @return nickname（账户昵称）
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 账户昵称 set方法
     * @param nickname（账户昵称）
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 账户头像 get方法
     * @return photo（账户头像）
     */
    public Blob getPhoto() {
        return photo;
    }

    /**
     * 账户头像 set方法
     * @param photo（账户头像）
     */
    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

    /**
     * 账户权限 get方法
     * @return authority（账户权限）
     */
    public byte getAuthority() {
        return authority;
    }

    /**
     * 账户权限 set方法
     * @param authority（账户权限）
     */
    public void setAuthority(byte authority) {
        this.authority = authority;
    }

    /**
     * 账户有效位 get方法
     * @return deleted（账户有效位）
     */
    public byte getDeleted() {
        return deleted;
    }

    /**
     * 账户有效位 set方法
     * @param deleted（账户有效位）
     */
    public void setDeleted(byte deleted) {
        this.deleted = deleted;
    }

    /**
     * 账户容量 get方法
     * @return accountEmpty（账户容量）
     */
    public int getAccountEmpty() {
        return accountEmpty;
    }

    /**
     * 账户容量 get方法
     * @param accountEmpty（账户容量）
     */
    public void setAccountEmpty(int accountEmpty) {
        this.accountEmpty = accountEmpty;
    }
}
