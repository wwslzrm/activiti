package cn.lockinlife.activiti.core.strategy.assignrule;

import cn.lockinlife.activiti.core.entity.ActRoleAssign;

import java.util.List;

/**
 * 具体分配规则接口
 */
public interface RoleAssignRuleI {
    String assignTask(List<String>users);
}
