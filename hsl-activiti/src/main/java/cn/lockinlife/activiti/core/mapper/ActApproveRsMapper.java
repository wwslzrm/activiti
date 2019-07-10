package cn.lockinlife.activiti.core.mapper;

import cn.lockinlife.activiti.core.entity.ActApproveRs;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@Mapper
public interface ActApproveRsMapper {

    @InsertProvider(type = ActApproveRsSQL.class, method = "insertEntity")
    public Integer insertEntity(@Param("param") ActApproveRs actApproveRs);

    @Update("UPDATE act_approve_rs SET apprvRs=#{apprvRs}, curDep=#{curDep}, endTime=#{endTime} WHERE applcNum = #{applcNum}")
    Integer updateRsByApplcNum(@Param("applcNum") String applcNum, @Param("apprvRs") String apprvRs, @Param("endTime") Date endTime, @Param("curDep") String curDep);

    @Update("UPDATE act_approve_rs SET apprvRs=#{apprvRs}, curDep=#{curDep}, endTime=#{endTime}, apprvReason=#{apprvReason} WHERE applcNum = #{applcNum}")
    Integer updateRsAndReasonByApplcNum(@Param("applcNum") String applcNum, @Param("apprvReason") String apprvReason, @Param("apprvRs") String apprvRs, @Param("endTime") Date endTime, @Param("curDep") String curDep);

    @Select("SELECT * FROM act_approve_rs WHERE applcNum=#{applcNum}")
    ActApproveRs getApproveInfoByApplcNum(@Param("applcNum") String applcNum);
}
