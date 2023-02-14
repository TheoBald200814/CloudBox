package cloudbox.account.Service.ServiceImpl;

import cloudbox.account.Bean.Account;
import cloudbox.account.Service.AccountManagement;
import cloudbox.account.Mapper.Mapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.util.DigestUtils;
import javax.validation.constraints.*;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

/**
 * 账户管理服务接口实现
 * @author TheoBald
 * @version 0.0.2
 */
@Service
@Validated
public class AccountManagementImpl implements AccountManagement {

    @Autowired
    private Mapper.AccountMapper accountMapper;

    @Autowired
    private AccountRedisUtil accountRedisUtil;


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
    @Override
    public boolean createAccount(@NotBlank @Size(min = 10, max = 30) @Email String accountId,
                                 @NotBlank @Size(min = 10, max = 32) String password,
                                 @Size(min = 1, max = 10) String nickname, Blob photo) throws SQLException, IOException {

        if(accountMapper.selectOne(new QueryWrapper<Account>().select("account_id").apply("account_id={0}",accountId)) != null){

            System.out.println("账户已存在");

            return false;
            //若该账户已存在，则注册失败
        }else{

            String passwordByMD5 = encryptPasswordByDM5(password);
            //密码使用MD5加密
            Account account = new Account();

            account.setId(accountId);

            account.setPassword(passwordByMD5);

            if(nickname!=null){

                account.setNickname(nickname);
                //若附带昵称，则录入昵称
            }

            if(photo != null){

                account.setPhoto(photo);
                //若附带头像，则录入头像
            }
            account.setAuthority((byte) 0);
            //设置权限为普通用户
            account.setDeleted((byte) 1);
            //设置有效位为1
            account.setAccountEmpty(100000);
            //设置账户云盘空间为100000
            int chargeSQL = accountMapper.insert(account);
            //执行SQL

            if(chargeSQL == 1){
                //如果SQL执行成功
                boolean chargeRedis = accountRedisUtil.set(account);
                //录入Redis
                if(chargeRedis){

                    return true;
                }else {

                    System.out.println("创建账户-录入Redis失败");

                    return false;
                    //Redis录入失败
                }
            }else{

                System.out.println("创建账户-录入MySQL失败");

                return false;
                //SQL执行失败
            }
        }
    }


    /**
     * 账户Id存在性检查
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @return accountId（若存在该账户，则返回其accountId；若不存在，则返回null）
     */
    @Override
    public String readAccountId(@NotBlank @Size(min = 10, max = 30) @Email String accountId) throws IOException, SQLException {

//        Account account= accountMapper.selectAccountId(accountId);

//        Account account=accountMapper.selectOne(new QueryWrapper<Account>().select("account_id").apply("account_id={0}",accountId));
//
//        if(account!=null){
//            return account.getId();
//        }else {
//            return "error2read";
//        }
        Account account = readAccount(accountId);

        if(account == null){

            return null;
        }else{

            return account.getId();
        }
    }


    /**
     * 账户密码检查
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @return password（若存在该账户，则返回其加密后的password；若不存在，则返回null）
     */
    @Override
    public String readAccountPassword(@NotBlank @Size(min = 10, max = 30) @Email String accountId) throws IOException, SQLException {

//        Account account=accountMapper.selectAccountPassword(accountId);

//        Account account=accountMapper.selectOne(new QueryWrapper<Account>().select("password").apply("account_id={0}",accountId));
//
//        if(account!=null){
//            return account.getPassword();
//        }else {
//            return "error2read";
//        }
        Account account = readAccount(accountId);

        if(account == null){

            return null;
        }else{

            return account.getPassword();
        }

    }


    /**
     * 账户昵称检查
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @return nickname（若存在该账户，则返回其nickname；若不存在，则返回null）
     */
    @Override
    public String readAccountNickname(@NotBlank @Size(min = 10, max = 30) @Email String accountId) throws IOException, SQLException {

//        Account account=accountMapper.selectAccountNickname(accountId);

//        Account account=accountMapper.selectOne(new QueryWrapper<Account>().select("nickname").apply("account_id={0}",accountId));
//
//        if(account!=null){
//            return account.getNickname();
//        }else{
//            return null;
//        }

        Account account = readAccount(accountId);

        if(account == null){

            return null;
        }else{

            return account.getNickname();
        }
    }


    /**
     * 账户头像检查
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @return photo（若存在该账户，则返回其photo；若不存在，则返回null）
     */
    @Override
    public Blob readAccountPhoto(@NotBlank @Size(min = 10, max = 30) @Email String accountId) throws IOException, SQLException {

//        Account account=accountMapper.selectAccountPhoto(accountId);

//        Account account=accountMapper.selectOne(new QueryWrapper<Account>().select("photo").apply("account_id={0}",accountId));
//
//        if(account!=null){
//            return account.getPhoto();
//        }else {
//            return null;
//        }

        Account account = readAccount(accountId);

        if(account == null){

            return null;
        }else{

            return account.getPhoto();
        }
    }


    /**
     * 账户权限检查
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @return authority（若存在该账户，则返回其authority；若不存在，则返回-1）
     */
    @Override
    public byte readAccountAuthority(@NotBlank @Size(min = 10, max = 30) @Email String accountId) throws IOException, SQLException {

//        Account account=accountMapper.selectAccountAuthority(accountId);

//        Account account=accountMapper.selectOne(new QueryWrapper<Account>().select("authority").apply("account_id={0}",accountId));
//
//        if(account!=null){
//            return account.getAuthority();
//        }else {
//            return -1;
//        }

        Account account = readAccount(accountId);

        if(account == null){

            return -1;
        }else{

            return account.getAuthority();
        }
    }


    /**
     * 账户有效性检查
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @return deleted（若存在该账户，则返回其deleted；若不存在，则返回-1）
     */
    @Override
    public byte readAccountDeleted(@NotBlank @Size(min = 10, max = 30) @Email String accountId) throws IOException, SQLException {

//        Account account=accountMapper.selectAccountDeleted(accountId);

//        Account account=accountMapper.selectOne(new QueryWrapper<Account>().select("deleted").apply("account_id={0}",accountId));
//
//        if(account!=null){
//            return account.getDeleted();
//        }else {
//            return -1;
//        }

        Account account = readAccount(accountId);

        if(account == null){

            return -1;
        }else{

            return account.getDeleted();
        }
    }


    /**
     * 账户云盘容量检查（默认容量为100000）
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @return accountEmpty（若存在该账户，则返回其accountEmpty；若不存在，则返回-1）
     */
    @Override
    public long readAccountEmpty(@NotBlank @Size(min = 10, max = 30) @Email String accountId) throws IOException, SQLException {

//        Account account=accountMapper.selectAccountEmpty(accountId);

//        Account account=accountMapper.selectOne(new QueryWrapper<Account>().select("account_empty").apply("account_id={0}",accountId));
//
//        if(account!=null){
//            return account.getAccountEmpty();
//        }else {
//            return -1;
//        }

        Account account = readAccount(accountId);

        if(account == null){

            return -1;
        }else{

            return account.getAccountEmpty();
        }
    }


    /**
     * 账户列表检查
     * @return 账户列表
     */
    @Override
    public List<Account> readAccountList(@NotNull @Min(0) @Max(1) byte authority){

        return  accountMapper.selectList(new QueryWrapper<Account>().select("account_id","account_empty","deleted").apply("authority = {0}",authority));
    }


    /**
     * 账户查找
     * @param accountId 账户Id
     * @return 若账户存在，则返回该账户；若账户不存在，则返回空
     */
    private Account readAccount(String accountId) throws IOException, SQLException {

        Account account = accountRedisUtil.get(accountId);

        if(account != null){
            //若Redis命中
            System.out.println("read-Redis命中");

            return account;
        }else {
            //若Redis未命中
            System.out.println("read-Redis未命中");

            account = accountMapper.selectById(accountId);
            //访问MySQL
            if(account!=null){
                //若MySQL命中
                System.out.println("read-MySQL命中");

                accountRedisUtil.set(account);
                //录入Redis
                return account;
            }else {
                //若MySQL也未命中
                System.out.println("read-MySQL未命中");

                return null;
            }
        }

    }


    /**
     * 账户密码更新
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @param newPassword 账户密码（10-30位字符串）非空
     * @return 更新成功返回true，更新失败返回false
     */
    @Override
    public boolean updateAccountPassword(@NotBlank @Size(min = 10, max = 30) @Email String accountId, @NotBlank @Size(min = 10, max = 30) String newPassword) {

        String passwordByDM5=encryptPasswordByDM5(newPassword);
        //原始密码加密
        if(accountRedisUtil.get(accountId) != null){
            //若该账户目前在Redis缓存中
            accountRedisUtil.update(accountId,"passwordByMD5",passwordByDM5);
            //更新Redis缓存
        }
        UpdateWrapper<Account> updateWrapper=new UpdateWrapper<Account>();

        updateWrapper.eq("account_id",accountId).set("password",passwordByDM5);

        Integer rows=accountMapper.update(null,updateWrapper);
        //更新MySQL
        if(rows>0){

            return true;
        }else {

            return false;
        }
    }


    /**
     * 账户昵称更新
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @param newNickname 账户昵称（1-10位字符串）非空
     * @return 更新成功返回true，更新失败返回false
     */
    @Override
    public boolean updateAccountNickname(@NotBlank @Size(min = 10, max = 30) @Email String accountId, @NotBlank @Size(min = 1, max = 10) String newNickname) {

        if(accountRedisUtil.get(accountId) != null){
            //若该账户目前在Redis缓存中
            accountRedisUtil.update(accountId,"nickname",newNickname);
            //更新Redis缓存
        }

        UpdateWrapper<Account> updateWrapper=new UpdateWrapper<Account>();

        updateWrapper.eq("account_id",accountId).set("nickname",newNickname);

        Integer rows=accountMapper.update(null,updateWrapper);
        //更新MySQL
        if(rows>0){

            return true;
        }else {

            return false;
        }
    }


    /**
     * 账户头像更新
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @param newPhoto 账户头像（二进制文件，大小待定）非空
     * @return 更新成功返回true，更新失败返回false
     */
    @Override
    public boolean updateAccountPhoto(@NotBlank @Size(min = 10, max = 30) @Email String accountId, Blob newPhoto) throws SQLException, IOException {

        if(accountRedisUtil.get(accountId) != null){
            //若该账户目前在Redis缓存中
            accountRedisUtil.update(accountId,"photo",new String(newPhoto.getBinaryStream().readAllBytes()));
            //更新Redis缓存
        }

        UpdateWrapper<Account> updateWrapper=new UpdateWrapper<Account>();

        updateWrapper.eq("account_id",accountId).set("photo", newPhoto);

        Integer rows=accountMapper.update(null,updateWrapper);
        //更新MySQL
        if(rows>0){

            return true;
        }else {

            return false;
        }
    }


    /**
     * 账户权限更新
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @param newAuthority 账户权限（0/1/2）非空
     * @return 更新成功返回true，更新失败返回false
     */
    @Override
    public boolean updateAccountAuthority(@NotBlank @Size(min = 10, max = 30) @Email String accountId, @NotNull @Min(0) @Max(2) byte newAuthority) {

        if(accountRedisUtil.get(accountId) != null){
            //若该账户目前在Redis缓存中
            accountRedisUtil.update(accountId,"authority",String.valueOf(newAuthority));
            //更新Redis缓存
        }

        UpdateWrapper<Account> updateWrapper=new UpdateWrapper<Account>();

        updateWrapper.eq("account_id",accountId).set("authority", newAuthority);

        Integer rows=accountMapper.update(null,updateWrapper);
        //更新MySQL
        if(rows>0){

            return true;
        }else {

            return false;
        }
    }


    /**
     * 账户有效位更新
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @param newDeleted 账户有效位（0/1）非空
     * @return 更新成功返回true，更新失败返回false
     */
    @Override
    public boolean updateAccountDeleted(@NotBlank @Size(min = 10, max = 30) @Email String accountId, @NotNull @Min(0) @Max(1) byte newDeleted) {

        if(accountRedisUtil.get(accountId) != null){
            //若该账户目前在Redis缓存中
            accountRedisUtil.update(accountId,"deleted",String.valueOf(newDeleted));
            //更新Redis缓存
        }

        UpdateWrapper<Account> updateWrapper=new UpdateWrapper<Account>();

        updateWrapper.eq("account_id",accountId).set("deleted", newDeleted);

        Integer rows=accountMapper.update(null,updateWrapper);
        //更新MySQL
        if(rows>0){

            return true;
        }else {

            return false;
        }
    }


    /**
     * 账户容量更新
     * @param accountId 账户Id（10-30位电子邮件格式的字符串）非空
     * @param newAccountEmpty 账户容量更新（0-1000000）非空
     * @return 更新成功返回true，更新失败返回false
     */
    @Override
    public boolean updateAccountEmpty(@NotBlank @Size(min = 10, max = 30) @Email String accountId, @NotNull @Min(0) @Max(1000000) int newAccountEmpty) {

        if(accountRedisUtil.get(accountId) != null){
            //若该账户目前在Redis缓存中
            accountRedisUtil.update(accountId,"account_empty",String.valueOf(newAccountEmpty));
            //更新Redis缓存
        }

        UpdateWrapper<Account> updateWrapper=new UpdateWrapper<Account>();

        updateWrapper.eq("account_id",accountId).set("account_empty", newAccountEmpty);

        Integer rows=accountMapper.update(null,updateWrapper);
        //更新MySQL
        if(rows>0){

            return true;
        }else {

            return false;
        }
    }


    /**
     * 原始密码加密（MD5算法）
     * @param password 原始密码
     * @return 加密后的32位字符串
     */
    private String encryptPasswordByDM5(String password){
        String data=password+"aa7aa8a8fa604c60866413f52563b70c";
        return DigestUtils.md5DigestAsHex(data.getBytes());
    }

}
