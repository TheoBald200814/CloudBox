package cloudbox.notice.Bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Timestamp;

/**
 * 账户实体类，用于处理MySQL中CloudBox库notice表数据。
 * @author TheoBald
 * @version 0.0.1
 */
@TableName("notice")
public class Notice {

    @TableId(type = IdType.INPUT)
    private String noticeId;
    //公告Id（10-30位邮箱格式的字符串，主键非空）

    private String noticeTitle;
    //公告标题（1-30位字符串，主键非空）

    private String noticeData;
    //公告内容（0-255位字符串，非空）

    private Timestamp noticeDate;
    //更新日期（非空）

    private boolean deleted;
    //有效位（true/false，非空）

    private String noticeModify;
    //公告修改人（10-30位邮箱格式字符串，可空）



    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeData() {
        return noticeData;
    }

    public void setNoticeData(String noticeData) {
        this.noticeData = noticeData;
    }

    public Timestamp getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(Timestamp noticeDate) {
        this.noticeDate = noticeDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getNoticeModify() {
        return noticeModify;
    }

    public void setNoticeModify(String noticeModify) {
        this.noticeModify = noticeModify;
    }



}
