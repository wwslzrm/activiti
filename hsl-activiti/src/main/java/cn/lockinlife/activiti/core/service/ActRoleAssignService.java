package cn.lockinlife.activiti.core.service;

import cn.lockinlife.activiti.core.entity.ActRoleAssign;
import cn.lockinlife.activiti.core.mapper.ActRoleAssignMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ActRoleAssignService implements ActRoleAssignServiceI {

    @Autowired
    private ActRoleAssignMapper actRoleAssignMapper;

    @Override
    public String assignTask(String applcNum, String oprDep) {
        return actRoleAssignMapper.assignTask(applcNum, oprDep);
    }

    @Override
    public void createRole(ActRoleAssign actRoleAssign) {
        actRoleAssignMapper.createRole(actRoleAssign);
    }

    @Override
    public String getUserCounts(List<String> users) {
        if (users.size() == 1) {
            return users.get(0);
        }
        StringBuilder conditon = new StringBuilder();
        for (int i = 0; i < users.size(); i++) {
            if (i > 0) {
                conditon.append(",");
            }
            conditon.append("'").append(users.get(i)).append("'");
        }
       Map result = actRoleAssignMapper.getLeastCountUser(conditon.toString());
        if (result == null) {
            return users.get(0);
        } else {
            return result.get("tel").toString();
        }
    }
}
