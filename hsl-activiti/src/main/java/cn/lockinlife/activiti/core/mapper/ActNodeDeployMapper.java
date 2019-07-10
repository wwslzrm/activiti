package cn.lockinlife.activiti.core.mapper;

import cn.lockinlife.activiti.core.entity.ActNodeDeploy;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ActNodeDeployMapper {

    @Delete("DELETE FROM act_node_deploy WHERE processName=#{processName}")
    Integer deleteProcessByProcessName(@Param("processName") String processName);

    @Delete("TRUNCATE act_node_deploy")
    Integer deleteAllProcess();


    @InsertProvider(type = ActNodeDeploySQL.class, method = "insertEntity")
    Integer insertEntity(@Param("param") ActNodeDeploy entity);

    @Select("SELECT * FROM act_node_deploy WHERE processName=#{processName}")
    List<ActNodeDeploy> getNodesByProcessName(@Param("processName") String processName);
}
