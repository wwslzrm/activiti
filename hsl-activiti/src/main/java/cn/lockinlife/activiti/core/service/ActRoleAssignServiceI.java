package cn.lockinlife.activiti.core.service;

import cn.lockinlife.activiti.core.entity.ActRoleAssign;

import java.util.List;
import java.util.Map;

public interface ActRoleAssignServiceI {
    String assignTask(String applcNum, String oprDep);

    void createRole(ActRoleAssign actRoleAssign);

    String getUserCounts(List<String> users);
}
