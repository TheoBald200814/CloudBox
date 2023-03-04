package cloudbox.notice.Service;


import cloudbox.notice.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * 公告管理服务
 * @author TheoBald
 * @version 0.0.1
 */
@Service
@Validated
public interface NoticeManagement {

    /**
     * 新建公告
     * @param noticeId 公告Id（管理员账户：10-30位Email格式字符串，非空主键）
     * @param noticeTitle 公告标题（1-30位字符串，非空主键）
     * @param noticeData 公告内容（0-255位字符串，非空）
     * @param noticeDate 公告更新日期（日期格式，非空）
     * @param deleted 公告有效位（true有效/false无效，非空）
     * @param noticeModify 公告修改人（管理员账户：10-30位Email格式字符串）
     * @return 新建成功返回true；新建失败返回false
     */
    boolean createNotice(@NotBlank @Size(min = 10,max = 30) @Email String noticeId,
                         @NotBlank @Size(min = 1,max = 30) String noticeTitle,
                         @NotBlank @Size(min = 0,max = 255) String noticeData,
                         @NotNull Timestamp noticeDate,
                         @NotNull boolean deleted,
                         @Size(min = 10,max = 30) String noticeModify);
    



}
