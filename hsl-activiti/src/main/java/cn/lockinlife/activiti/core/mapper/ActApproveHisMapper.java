package cn.lockinlife.activiti.core.mapper;

import cn.lockinlife.activiti.core.entity.ActApproveHis;
import cn.lockinlife.activiti.core.entity.ActNodeInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ActApproveHisMapper {

    @InsertProvider(type = ActApproveHisSQL.class, method = "insertEntity")
    Integer insertEntity(@Param("param") ActApproveHis actApproveHis);

    @Select("SELECT * FROM act_approve_his WHERE applcNum=#{applcNum} ORDER BY id desc limit 0,1 ")
    ActApproveHis getCurApproveRs(@Param("applcNum") String applcNum);

    @Select("SELECT * FROM act_approve_his WHERE applcNum=#{applcNum} AND curDep <> #{name} ORDER BY id desc limit 0,1 ")
    ActApproveHis getApprvRs(@Param("applcNum") String applcNum, @Param("name") String name);


    @UpdateProvider(type = ActApproveHisSQL.class, method = "updateEntity")
    Integer updateEntity(@Param("param") ActApproveHis currentApproveHis);
}
