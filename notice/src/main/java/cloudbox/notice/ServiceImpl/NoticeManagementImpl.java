package cloudbox.notice.ServiceImpl;


import cloudbox.notice.Bean.Notice;
import cloudbox.notice.Service.NoticeManagement;
import cloudbox.notice.mapper.Mapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;

/**
 * 公告管理实现类
 * @author TheoBald
 * @version 0.0.1
 */
@Service
@Validated
public class NoticeManagementImpl implements NoticeManagement {

    @Autowired
    private Mapper.NoticeMapper noticeMapper;

    /**
     * 新建公告
     *
     * @param noticeId     公告Id（管理员账户：10-30位Email格式字符串，非空主键）
     * @param noticeTitle  公告标题（1-30位字符串，非空主键）
     * @param noticeData   公告内容（0-255位字符串，非空）
     * @param noticeDate   公告更新日期（日期格式，非空）
     * @param deleted      公告有效位（true有效/false无效，非空）
     * @param noticeModify 公告修改人（管理员账户：10-30位Email格式字符串）
     * @return 新建成功返回true；新建失败返回false
     */
    @Override
    public boolean createNotice(@NotBlank @Size(min = 10, max = 30) @Email String noticeId,
                                @NotBlank @Size(min = 1, max = 30) String noticeTitle,
                                @NotBlank @Size(min = 0, max = 255) String noticeData,
                                @NotNull Timestamp noticeDate,
                                @NotNull boolean deleted,
                                @Size(min = 10, max = 30) String noticeModify) {

        Notice notice = new Notice();
        notice.setNoticeId(noticeId);
        notice.setNoticeTitle(noticeTitle);
        notice.setNoticeData(noticeData);
        notice.setNoticeDate(noticeDate);
        notice.setDeleted(deleted);
        if(noticeModify != null){
            notice.setNoticeModify(noticeModify);
        }
        int result = noticeMapper.insert(notice);
        if(result == 1){
            System.out.println("公告新建成功");
            return true;
        }else {
            System.out.println("公告新建失败");
            return false;
        }
    }

    /**
     * 全体公告查看
     *
     * @return 全体公告
     */
    @Override
    public List<Notice> readNoticeAll() {
        return noticeMapper.selectList(new QueryWrapper<Notice>());
    }

    /**
     * 特定公告查看
     *
     * @param noticeId    公告Id（管理员账户：10-30位Email格式字符串，非空主键）
     * @param noticeTitle 公告标题（1-30位字符串，非空主键）
     * @return 特定公告对象
     */
    @Override
    public Notice readNotice(@NotBlank @Size(min = 10, max = 30) @Email String noticeId,
                             @NotBlank @Size(min = 1, max = 30) String noticeTitle) {
        return noticeMapper.selectOne(new QueryWrapper<Notice>().apply("notice_id = {0} and notice_title = {1}",noticeId,noticeTitle));
    }

    /**
     * 公告内容更新
     *
     * @param noticeId      公告Id（管理员账户：10-30位Email格式字符串，非空主键）
     * @param noticeTitle   公告标题（1-30位字符串，非空主键）
     * @param newNoticeData 待更新内容
     * @return 更新成功返回true；更新失败返回false
     */
    @Override
    public boolean updateNoticeData(@NotBlank @Size(min = 10, max = 30) @Email String noticeId, @NotBlank @Size(min = 1, max = 30) String noticeTitle, @NotBlank @Size(min = 0, max = 255) String newNoticeData) {
        return false;
    }

    /**
     * 公告日期更新
     *
     * @param noticeId    公告Id（管理员账户：10-30位Email格式字符串，非空主键）
     * @param noticeTitle 公告标题（1-30位字符串，非空主键）
     * @return 更新成功返回true；更新失败返回false
     */
    @Override
    public boolean updateNoticeDate(@NotBlank @Size(min = 10, max = 30) @Email String noticeId,
                                    @NotBlank @Size(min = 1, max = 30) String noticeTitle) {

        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        //获取当前时间
        UpdateWrapper<Notice> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("notice_id",noticeId).eq("notice_title",noticeTitle).set("notice_date",nowTime);

        Integer rows = noticeMapper.update(null,updateWrapper);
        //更新
        if(rows > 0){
            System.out.println("公告日期更新成功");
            return true;
        }else {
            System.out.println("公告日期更新失败");
            return false;
        }
    }

    /**
     * 公告有效位更新
     *
     * @param noticeId    公告Id（管理员账户：10-30位Email格式字符串，非空主键）
     * @param noticeTitle 公告标题（1-30位字符串，非空主键）
     * @param newDeleted  待更新公告有效位
     * @return 更新成功返回true；更新失败返回false
     */
    @Override
    public boolean updateNoticeDeleted(@NotBlank @Size(min = 10, max = 30) @Email String noticeId,
                                       @NotBlank @Size(min = 1, max = 30) String noticeTitle,
                                       @NotNull boolean newDeleted) {

        UpdateWrapper<Notice> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("notice_id",noticeId).eq("notice_title",noticeTitle).set("deleted",newDeleted);

        Integer rows = noticeMapper.update(null,updateWrapper);
        //更新
        if(rows > 0){
            System.out.println("公告有效位更新成功");
            return true;
        }else {
            System.out.println("公告有效位更新失败");
            return false;
        }
    }

    /**
     * 公告修改人更新
     *
     * @param noticeId        公告Id（管理员账户：10-30位Email格式字符串，非空主键）
     * @param noticeTitle     公告标题（1-30位字符串，非空主键）
     * @param newNoticeModify 待更新公告修改人
     * @return 更新成功返回true；更新失败返回false
     */
    @Override
    public boolean updateNoticeModify(@NotBlank @Size(min = 10, max = 30) @Email String noticeId,
                                      @NotBlank @Size(min = 1, max = 30) String noticeTitle,
                                      @NotBlank @Size(min = 10, max = 30) @Email String newNoticeModify) {

        UpdateWrapper<Notice> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("notice_id",noticeId).eq("notice_title",noticeTitle).set("notice_modify",newNoticeModify);

        Integer rows = noticeMapper.update(null,updateWrapper);
        //更新
        if(rows > 0){
            System.out.println("公告修改人更新成功");
            return true;
        }else {
            System.out.println("公告修改人更新失败");
            return false;
        }
    }
}
