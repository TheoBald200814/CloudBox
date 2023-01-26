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

    private String password;

    private String nickname;

    private Blob photo;

    private byte authority;

    private byte deleted;

    private int accountEmpty;

    public String getId() {
        return accountId;
    }

    public void setId(String accountId) {
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

    public byte getAuthority() {
        return authority;
    }

    public void setAuthority(byte authority) {
        this.authority = authority;
    }

    public byte getDeleted() {
        return deleted;
    }

    public void setDeleted(byte deleted) {
        this.deleted = deleted;
    }

    public int getAccountEmpty() {
        return accountEmpty;
    }

    public void setAccountEmpty(int accountEmpty) {
        this.accountEmpty = accountEmpty;
    }
}
