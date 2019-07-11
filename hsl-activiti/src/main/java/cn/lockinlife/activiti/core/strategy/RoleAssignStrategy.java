package cn.lockinlife.activiti.core.strategy;

import java.util.List;

public interface RoleAssignStrategy {

    String assignTask(String applcNum, String oprDep);

    String assignTaskWithRule(String applcNum, String oprDep, List<String> users);
	
	String assignTaskByOrder(String applcNum, String oprDep, Integer nodeOrder);

}
