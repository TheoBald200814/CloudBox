package cloudbox.account.Service;

import cloudbox.account.Bean.Account;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.sql.Blob;
import java.sql.SQLException;
/*
* Blob和account_empty的检查待完善*/


public interface AccountManagement {

    boolean createAccount(@NotBlank @Size(min=10,max=30) @Email String accountId,
                          @NotBlank @Size(min=10,max=32) String password,
                          @Size(min=1,max=10) String nickname,
                          Blob photo);
    //账户创建，其中nickname和photo可空，其余参数要求非空

    String readAccountId(@NotBlank @Size(min=10,max=30) @Email String accountId);
    //账户ID存在性检查

    String readAccountPassword(@NotBlank @Size(min=10,max=30) @Email String accountId);
    //账户密码检查（注：数据库中反馈的密码是MD5加密版本）

    String readAccountNickname(@NotBlank @Size(min=10,max=30) @Email String accountId);
    //账户昵称检查

    Blob readAccountPhoto(@NotBlank @Size(min=10,max=30) @Email String accountId);
    //账户头像检查

    byte readAccountAuthority(@NotBlank @Size(min=10,max=30) @Email String accountId);
    //账户权限检查（0为普通用户，1为管理员，2为超级管理员）

    byte readAccountDeleted(@NotBlank @Size(min=10,max=30) @Email String accountId);
    //账户有效性检查（1为有效，0为无效）

    long readAccountEmpty(@NotBlank @Size(min=10,max=30) @Email String accountId);
    //账户云盘容量检查（默认容量为100000）

    boolean updateAccountPassword(@NotBlank @Size(min=10,max=30) @Email String accountId,
                                  @NotBlank @Size(min=10,max=32) String newPassword);
    //账户密码更新

    boolean updateAccountNickname(@NotBlank @Size(min=10,max=30) @Email String accountId,
                                  @NotBlank @Size(min=1,max=10) String newNickname);
    //账户昵称更新

    boolean updateAccountPhoto(@NotBlank @Size(min=10,max=30) @Email String accountId,
                               Blob newPhoto);
    //账户头像更新

    boolean updateAccountAuthority(@NotBlank @Size(min=10,max=30) @Email String accountId,
                                   @NotNull @Min(0) @Max(3) byte newAuthority);
    //账户权限更新

    boolean updateAccountDeleted(@NotBlank @Size(min=10,max=30) @Email String accountId,
                                 @NotNull @Min(0) @Max(1) byte newDeleted);
    //账户有效位更新

    boolean updateAccountEmpty(@NotBlank @Size(min=10,max=30) @Email String accountId,
                               @NotNull @Min(0) @Max(1000000) int newAccountEmpty);
    //账户容量更新
}
