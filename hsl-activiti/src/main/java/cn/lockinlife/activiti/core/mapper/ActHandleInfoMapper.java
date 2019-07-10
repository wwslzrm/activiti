package cn.lockinlife.activiti.core.mapper;

import cn.lockinlife.activiti.core.entity.ActHandleInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ActHandleInfoMapper {
    @Select("SELECT * FROM act_handle_info WHERE applcNum=#{applcNum} AND apprvRs is null ORDER BY id DESC limit 0,1; ")
    ActHandleInfo getHandleByApplcNum(@Param("applcNum") String applcNum);

    @UpdateProvider(type = ActHandleInfoSQL.class, method = "updateEntity")
    Integer updateEntity(@Param("param") ActHandleInfo handleInfo);

    @InsertProvider(type = ActHandleInfoSQL.class, method = "insertEntity")
    Integer insertEntity(@Param("param") ActHandleInfo actHandleInfo);
}
