package cn.lockinlife.activiti.core.strategy.assignrule;

import cn.lockinlife.activiti.core.entity.ActRoleAssign;
import cn.lockinlife.activiti.core.service.ActRoleAssignServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 总的审批件平均分配规则
 */
@Service
public class AvgRoleAssignRule implements RoleAssignRuleI  {

    @Autowired
    private ActRoleAssignServiceI actRoleAssignService;

    @Override
    public String assignTask(List<String> users) {
        return actRoleAssignService.getUserCounts(users);
    }
}
