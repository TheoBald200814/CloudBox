package cloudbox.account.Service;

import cloudbox.account.Bean.Account;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
/*
* Blob和account_empty的检查待完善*/

/**
 * 账户管理接口
 * @author TheoBald
 * @version 0.0.2
 */
public interface AccountManagement {


    /**
     *账户注册
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @param password 账户密码（10-30位字符串）非空
     * @param nickname 账户昵称（1-10位字符串）可空
     * @param photo 账户头像（二进制文件，大小限制待定）可空
     * @return 注册成功返回true，失败返回false
     * @throws SQLException
     * @throws IOException
     */
    boolean createAccount(@NotBlank @Size(min=10,max=30) @Email String accountId,
                          @NotBlank @Size(min=10,max=32) String password,
                          @Size(min=1,max=10) String nickname,
                          Blob photo) throws SQLException, IOException;


    /**
     * 账户Id存在性检查
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @return accountId（若存在该账户，则返回其accountId；若不存在，则返回null）
     */
    String readAccountId(@NotBlank @Size(min=10,max=30) @Email String accountId) throws IOException, SQLException;


    /**
     * 账户密码检查
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @return password（若存在该账户，则返回其加密后的password；若不存在，则返回null）
     */
    String readAccountPassword(@NotBlank @Size(min=10,max=30) @Email String accountId) throws IOException, SQLException;


    /**
     * 账户昵称检查
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @return nickname（若存在该账户，则返回其nickname；若不存在，则返回null）
     */
    String readAccountNickname(@NotBlank @Size(min=10,max=30) @Email String accountId) throws IOException, SQLException;


    /**
     * 账户头像检查
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @return photo（若存在该账户，则返回其photo；若不存在，则返回null）
     */
    Blob readAccountPhoto(@NotBlank @Size(min=10,max=30) @Email String accountId) throws IOException, SQLException;


    /**
     * 账户权限检查
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @return authority（若存在该账户，则返回其authority；若不存在，则返回-1）
     */
    byte readAccountAuthority(@NotBlank @Size(min=10,max=30) @Email String accountId) throws IOException, SQLException;


    /**
     * 账户有效性检查
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @return deleted（若存在该账户，则返回其deleted；若不存在，则返回-1）
     */
    byte readAccountDeleted(@NotBlank @Size(min=10,max=30) @Email String accountId) throws IOException, SQLException;


    /**
     * 账户云盘容量检查（默认容量为100000）
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @return accountEmpty（若存在该账户，则返回其accountEmpty；若不存在，则返回-1）
     */
    long readAccountEmpty(@NotBlank @Size(min=10,max=30) @Email String accountId) throws IOException, SQLException;


    /**
     * 账户列表检查
     * @return 账户列表
     */
    List<Account> readAccountList(@NotNull @Min(0) @Max(1) byte authority);


    /**
     * 账户密码更新
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @param newPassword 账户密码（10-30位字符串）非空
     * @return 更新成功返回true，更新失败返回false
     */
    boolean updateAccountPassword(@NotBlank @Size(min=10,max=30) @Email String accountId,
                                  @NotBlank @Size(min=10,max=30) String newPassword);


    /**
     * 账户昵称更新
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @param newNickname 账户昵称（1-10位字符串）非空
     * @return 更新成功返回true，更新失败返回false
     */
    boolean updateAccountNickname(@NotBlank @Size(min=10,max=30) @Email String accountId,
                                  @NotBlank @Size(min=1,max=10) String newNickname);


    /**
     * 账户头像更新
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @param newPhoto 账户头像（二进制文件，大小待定）非空
     * @return 更新成功返回true，更新失败返回false
     */
    boolean updateAccountPhoto(@NotBlank @Size(min=10,max=30) @Email String accountId,
                               Blob newPhoto) throws SQLException, IOException;


    /**
     * 账户权限更新
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @param newAuthority 账户权限（0/1/2）非空
     * @return 更新成功返回true，更新失败返回false
     */
    boolean updateAccountAuthority(@NotBlank @Size(min=10,max=30) @Email String accountId,
                                   @NotNull @Min(0) @Max(2) byte newAuthority);


    /**
     * 账户有效位更新
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @param newDeleted 账户有效位（0/1）非空
     * @return 更新成功返回true，更新失败返回false
     */
    boolean updateAccountDeleted(@NotBlank @Size(min=10,max=30) @Email String accountId,
                                 @NotNull @Min(0) @Max(1) byte newDeleted);


    /**
     * 账户容量更新
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @param newAccountEmpty 账户容量更新（0-1000000）非空
     * @return 更新成功返回true，更新失败返回false
     */
    boolean updateAccountEmpty(@NotBlank @Size(min=10,max=30) @Email String accountId,
                               @NotNull @Min(0) @Max(1000000) int newAccountEmpty);


    /**
     * 账户登陆（测试用）
     * @param accountId 账户Id
     * @param password 账户密码
     * @return 若登陆成功，返回账户token；若登陆失败，返回null
     */
    Object tempLogin(@NotBlank @Size(min=10,max=30) @Email String accountId,
                     @NotBlank @Size(min=10,max=32) String password) throws IOException, SQLException;
}


