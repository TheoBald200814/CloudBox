package cloudbox.notice.mapper;


import cloudbox.notice.Bean.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Service;

@org.apache.ibatis.annotations.Mapper
public class Mapper {

    public interface NoticeMapper extends BaseMapper<Notice>{

    }
}
