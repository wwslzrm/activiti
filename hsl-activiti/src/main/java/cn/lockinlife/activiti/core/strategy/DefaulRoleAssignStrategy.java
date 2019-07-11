package cn.lockinlife.activiti.core.strategy;

import cn.lockinlife.activiti.core.service.ActRoleAssignServiceI;
import cn.lockinlife.activiti.core.strategy.assignrule.RoleAssignRuleI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaulRoleAssignStrategy implements RoleAssignStrategy{

    @Autowired
    private ActRoleAssignServiceI actRoleAssignService;

    @Autowired
    private RoleAssignRuleI roleAssignRule;

    @Override
    public String assignTask(String applcNum, String oprDep) {
        String role = actRoleAssignService.assignTask(applcNum, oprDep);
        return role == null ? "admin" : role;
    }

    @Override
    public String assignTaskWithRule(String applcNum, String oprDep, List<String> users) {
        return roleAssignRule.assignTask(users);
    }
	
	@Override
    public String assignTaskByOrder(String applcNum, String oprDep, Integer nodeOrder) {
        String role = actRoleAssignService.assignTaskByOrder(applcNum, oprDep,nodeOrder);
        return role == null ? "admin" : role;
    }


}
