package cn.lockinlife.activiti.core.service;

import cn.lockinlife.activiti.core.entity.ActApproveHis;
import cn.lockinlife.activiti.core.entity.ActNodeInfo;

public interface ActApproveHisServiceI {
    Integer inertEntity(ActApproveHis actApproveHis);

    ActApproveHis getCurApproveRs(String applcNum);

    ActApproveHis getApprvRs(String applcNum, String name);

    Integer updateEntity(ActApproveHis currentApproveHis);
}
