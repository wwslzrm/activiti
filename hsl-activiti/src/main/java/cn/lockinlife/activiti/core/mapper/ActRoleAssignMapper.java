package cn.lockinlife.activiti.core.mapper;

import cn.lockinlife.activiti.core.entity.ActRoleAssign;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Mapper
public interface ActRoleAssignMapper {

    @Select("SELECT tel FROM act_role_assign WHERE applcNum=#{applcNum} AND department=#{department} limit 0,1")
    String assignTask(@Param("applcNum") String applcNum, @Param("department") String department);

    @InsertProvider(type = ActRoleAssignSQL.class, method = "insertEntity")
    void createRole(@Param("param") ActRoleAssign actRoleAssign);

    @Select("SELECT tel, num FROM (" +
            "SELECT tel, count(*) AS num FROM act_role_assign WHERE tel IN (#{condition}) GROUP BY tel " +
            ") AS A" +
            "ORDER BY num ASC limit 0,1")
    Map getLeastCountUser(@Param("condition") String conditon);
}
