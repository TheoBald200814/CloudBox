package cloudbox.account.mapper;

import cloudbox.account.Bean.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author TheoBald
 */
@org.apache.ibatis.annotations.Mapper
public class Mapper {

    public interface AccountMapper extends BaseMapper<Account> {


//        @Select("SELECT account_id FROM account WHERE account_id = #{accountId}")
//        @Results({
//                @Result(property = "accountId", column = "account_id")
//        })
//        Account selectAccountId(@Param("accountId") String accountId);
//
//
//        @Select("SELECT password FROM account WHERE account_id = #{accountId}")
//        @Results({
//                @Result(property = "password", column = "password")
//        })
//        Account selectAccountPassword(@Param("accountId") String accountId);
//
//
//        @Select("SELECT nickname FROM account WHERE account_id = #{accountId}")
//        @Results({
//                @Result(property = "nickname", column = "nickname")
//        })
//        Account selectAccountNickname(@Param("accountId") String accountId);
//
//
//        @Select("SELECT photo FROM account WHERE account_id = #{accountId}")
//        @Results({
//                @Result(property = "photo", column = "photo",typeHandler = MyBlobTypeHandler.class)
//        })
//        Account selectAccountPhoto(@Param("accountId") String accountId);
//
//
//        @Select("SELECT authority FROM account WHERE account_id = #{accountId}")
//        @Results({
//                @Result(property = "authority", column = "authority")
//        })
//        Account selectAccountAuthority(@Param("accountId") String accountId);
//
//
//        @Select("SELECT deleted FROM account WHERE account_id = #{accountId}")
//        @Results({
//                @Result(property = "deleted", column = "deleted")
//        })
//        Account selectAccountDeleted(@Param("accountId") String accountId);
//
//
//        @Select("SELECT account_empty FROM account WHERE account_id = #{accountId}")
//        @Results({
//                @Result(property = "accountEmpty", column = "account_empty")
//        })
//        Account selectAccountEmpty(@Param("accountId") String accountId);

    }
}