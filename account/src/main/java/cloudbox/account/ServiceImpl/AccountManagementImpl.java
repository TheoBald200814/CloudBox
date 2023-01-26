package cloudbox.account.ServiceImpl;

import cloudbox.account.Bean.Account;
import cloudbox.account.Service.AccountManagement;
import cloudbox.account.mapper.Mapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.util.DigestUtils;
import javax.validation.constraints.*;
import java.sql.Blob;




@Service
@Validated
public class AccountManagementImpl implements AccountManagement {

    @Autowired
    private Mapper.AccountMapper accountMapper;

    @Override
    public boolean createAccount(@NotBlank @Size(min = 10, max = 30) @Email String accountId, @NotBlank @Size(min = 10, max = 32) String password, @Size(min = 1, max = 10) String nickname, Blob photo) {



        if(accountMapper.selectOne(new QueryWrapper<Account>().select("account_id").apply("account_id={0}",accountId))!=null){
            return false;
        }else{
            String passwordByMD5=encryptPasswordByDM5(password);

            Account account=new Account();
            account.setId(accountId);
            account.setPassword(passwordByMD5);
            if(nickname!=null){
                account.setNickname(nickname);
            }
            if(photo!=null){
                account.setPhoto(photo);
            }
            account.setAuthority((byte) 0);
            account.setDeleted((byte) 1);
            account.setAccountEmpty(100000);
            accountMapper.insert(account);
            return true;
        }
    }

    @Override
    public String readAccountId(@NotBlank @Size(min = 10, max = 30) @Email String accountId) {

//        Account account= accountMapper.selectAccountId(accountId);

        Account account=accountMapper.selectOne(new QueryWrapper<Account>().select("account_id").apply("account_id={0}",accountId));

        if(account!=null){
            return account.getId();
        }else {
            return "error2read";
        }
    }

    @Override
    public String readAccountPassword(@NotBlank @Size(min = 10, max = 30) @Email String accountId) {

//        Account account=accountMapper.selectAccountPassword(accountId);

        Account account=accountMapper.selectOne(new QueryWrapper<Account>().select("password").apply("account_id={0}",accountId));

        if(account!=null){
            return account.getPassword();
        }else {
            return "error2read";
        }
    }

    @Override
    public String readAccountNickname(@NotBlank @Size(min = 10, max = 30) @Email String accountId) {

//        Account account=accountMapper.selectAccountNickname(accountId);

        Account account=accountMapper.selectOne(new QueryWrapper<Account>().select("nickname").apply("account_id={0}",accountId));

        if(account!=null){
            return account.getNickname();
        }else{
            return null;
        }
    }

    @Override
    public Blob readAccountPhoto(@NotBlank @Size(min = 10, max = 30) @Email String accountId) {

//        Account account=accountMapper.selectAccountPhoto(accountId);

        Account account=accountMapper.selectOne(new QueryWrapper<Account>().select("photo").apply("account_id={0}",accountId));

        if(account!=null){
            return account.getPhoto();
        }else {
            return null;
        }
    }

    @Override
    public byte readAccountAuthority(@NotBlank @Size(min = 10, max = 30) @Email String accountId) {

//        Account account=accountMapper.selectAccountAuthority(accountId);

        Account account=accountMapper.selectOne(new QueryWrapper<Account>().select("authority").apply("account_id={0}",accountId));

        if(account!=null){
            return account.getAuthority();
        }else {
            return -1;
        }
    }

    @Override
    public byte readAccountDeleted(@NotBlank @Size(min = 10, max = 30) @Email String accountId) {

//        Account account=accountMapper.selectAccountDeleted(accountId);

        Account account=accountMapper.selectOne(new QueryWrapper<Account>().select("deleted").apply("account_id={0}",accountId));

        if(account!=null){
            return account.getDeleted();
        }else {
            return -1;
        }
    }

    @Override
    public long readAccountEmpty(@NotBlank @Size(min = 10, max = 30) @Email String accountId) {

//        Account account=accountMapper.selectAccountEmpty(accountId);

        Account account=accountMapper.selectOne(new QueryWrapper<Account>().select("account_empty").apply("account_id={0}",accountId));

        if(account!=null){
            return account.getAccountEmpty();
        }else {
            return -1;
        }
    }

    @Override
    public boolean updateAccountPassword(@NotBlank @Size(min = 10, max = 30) @Email String accountId, @NotBlank @Size(min = 10, max = 32) String newPassword) {

        String passwordByDM5=encryptPasswordByDM5(newPassword);
        UpdateWrapper<Account> updateWrapper=new UpdateWrapper<Account>();
        updateWrapper.eq("account_id",accountId).set("password",passwordByDM5);
        Integer rows=accountMapper.update(null,updateWrapper);
        if(rows>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateAccountNickname(@NotBlank @Size(min = 10, max = 30) @Email String accountId, @NotBlank @Size(min = 1, max = 10) String newNickname) {

        UpdateWrapper<Account> updateWrapper=new UpdateWrapper<Account>();
        updateWrapper.eq("account_id",accountId).set("nickname",newNickname);
        Integer rows=accountMapper.update(null,updateWrapper);
        if(rows>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateAccountPhoto(@NotBlank @Size(min = 10, max = 30) @Email String accountId, Blob newPhoto) {

        UpdateWrapper<Account> updateWrapper=new UpdateWrapper<Account>();
        updateWrapper.eq("account_id",accountId).set("photo", newPhoto);
        Integer rows=accountMapper.update(null,updateWrapper);
        if(rows>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateAccountAuthority(@NotBlank @Size(min = 10, max = 30) @Email String accountId, @NotNull @Min(0) @Max(3) byte newAuthority) {

        UpdateWrapper<Account> updateWrapper=new UpdateWrapper<Account>();
        updateWrapper.eq("account_id",accountId).set("authority", newAuthority);
        Integer rows=accountMapper.update(null,updateWrapper);
        if(rows>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateAccountDeleted(@NotBlank @Size(min = 10, max = 30) @Email String accountId, @NotNull @Min(0) @Max(1) byte newDeleted) {

        UpdateWrapper<Account> updateWrapper=new UpdateWrapper<Account>();
        updateWrapper.eq("account_id",accountId).set("deleted", newDeleted);
        Integer rows=accountMapper.update(null,updateWrapper);
        if(rows>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateAccountEmpty(@NotBlank @Size(min = 10, max = 30) @Email String accountId, @NotNull @Min(0) @Max(1000000) int newAccountEmpty) {

        UpdateWrapper<Account> updateWrapper=new UpdateWrapper<Account>();
        updateWrapper.eq("account_id",accountId).set("account_empty", newAccountEmpty);
        Integer rows=accountMapper.update(null,updateWrapper);
        if(rows>0){
            return true;
        }else {
            return false;
        }
    }

    private String encryptPasswordByDM5(String password){
        String data=password+"aa7aa8a8fa604c60866413f52563b70c";
        return DigestUtils.md5DigestAsHex(data.getBytes());
    }
}
