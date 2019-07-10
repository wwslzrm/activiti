package cn.lockinlife.activiti.core.mapper;

import cn.lockinlife.activiti.core.entity.ActNodeInfo;
import cn.lockinlife.activiti.core.entity.ActProcessInfoEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ActProcessInfoMapper {

    @Select("SELECT B.* FROM act_process_info A INNER JOIN act_node_info B ON A.uuid = B.processId " +
            "WHERE A.type=#{type} AND A.state= 0 ORDER BY B.nodeOrder asc; ")
    List<ActNodeInfo> getProcessNodes(@Param("type") String type);

    @Select("SELECT * FROM act_node_info B WHERE B.processId=#{processId} ORDER BY B.nodeOrder asc; ")
    List<ActNodeInfo> getProcessNodesByProcessId(@Param("processId") String processId);

    @Select("SELECT * FROM `act_node_info` WHERE processId = #{processId} AND nodeOrder > (\n" +
            "SELECT nodeOrder FROM `act_node_info` WHERE processId = #{processId} and name = #{name}\n) ORDER BY nodeOrder asc limit 0,1;")
    ActNodeInfo getNextNode(@Param("processId") String processId, @Param("name") String name);

    @Select("SELECT * FROM `act_node_info` WHERE processId = #{processId} and name = #{name}")
    ActNodeInfo getCurNode(@Param("processId") String processId, @Param("name") String name);

    @Select("SELECT * FROM `act_node_info` WHERE processId = #{processId} ORDER BY nodeOrder DESC limit 0,1")
    ActNodeInfo getEndNode(@Param("processId") String processId);

    @Select("SELECT * FROM `act_node_info` WHERE processId = #{processId} AND name=#{name} ORDER BY nodeOrder DESC limit 0,1")
    ActNodeInfo getNodeByProcessIdAndNodeName(@Param("processId") String processId, @Param("name") String name);

    @Select("SELECT * FROM act_process_info WHERE uuid = #{uuid}")
    ActProcessInfoEntity getProcessInfoByProcessId(String uuid);

    @Update("UPDATE act_process_info SET state=1 WHERE state=0 AND type=#{type}")
    Integer updateStateByType(String type);

    @InsertProvider(type = ActProcessInfoSQL.class, method = "insertEntity")
    Integer insertEntity(@Param("param") ActProcessInfoEntity actProcessInfoEntity);

    @Select("SELECT * FROM act_node_info WHERE name='handleExp'")
    ActNodeInfo getHandleExp();

    @Select("SELECT * FROM act_node_info WHERE name=#{name} AND processId=#{processId}")
    ActNodeInfo getNodeByName(@Param("name") String name, @Param("processId") String processId);

    @Select("SELECT * FROM `act_node_info` WHERE processId = #{processId} ORDER BY nodeOrder ASC limit 0,1")
    ActNodeInfo getBeginNode(String processId);

    @Select("SELECT * FROM `act_node_info` WHERE processId = #{processId} AND nodeOrder > #{srcId} ORDER BY nodeOrder asc limit 0,1;")
    ActNodeInfo getNextNodeBySrcId(@Param("processId") String processId, @Param("srcId") Integer srcId);

    @Select("SELECT * FROM act_node_info WHERE nodeOrder=#{nodeOrder} AND processId=#{processId}")
    ActNodeInfo getNodeByCurId(@Param("nodeOrder") Integer nodeOrder, @Param("processId") String processId);
}
