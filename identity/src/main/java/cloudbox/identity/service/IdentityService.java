package cloudbox.identity.service;

import cloudbox.identity.entity.dto.Account;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 身份认证服务
 * @author theobald
 */
@Service
@Validated
public interface IdentityService {

    /**
     * 用户身份认证
     * @param accountId 账户ID
     * @param password 账户密码
     * @return 账户对象
     */
     Account userLogin(@NotBlank @Email @Size(min = 10, max = 30) String accountId,
                       @NotBlank @Size(min = 10, max = 30) String password);

    /**
     * 服务模块登陆
     */
    void serverLogin();
}
