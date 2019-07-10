package cn.lockinlife.activiti.core.service;


import cn.lockinlife.activiti.core.entity.ActApproveRs;

public interface ActApproveRsServiceI {
    Integer createWaitingApproval(String srcDep, String applcNum, String processId);

    Integer updateRsByApplcNum(String applcNum, String apprvRs, String curDep);

    Integer updateRsAndReasonByApplcNum(String applcNum, String apprvReason, String apprvRs, String curDep);

    ActApproveRs getApproveInfoByApplcNum(String applcNum);
}
