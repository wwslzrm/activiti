package cn.lockinlife.activiti.core.mapper;

import cn.lockinlife.activiti.core.entity.ActNodeInfo;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ActNodeInfoMapper {
    @InsertProvider(type = ActNodeInfoSQL.class, method = "insertEntity")
    public Integer insertEntity(@Param("param") ActNodeInfo curNode);
}
