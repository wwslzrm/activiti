package cn.lockinlife.activiti.core.service;

import cn.lockinlife.activiti.core.entity.ActApproveHis;
import cn.lockinlife.activiti.core.entity.ActApproveRs;
import cn.lockinlife.activiti.core.mapper.ActApproveRsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ActApproveRsService implements ActApproveRsServiceI {

    @Autowired
    private ActApproveRsMapper actApproveRsMapper;

    @Override
    public Integer createWaitingApproval(String srcDep, String applcNum, String processId) {
        ActApproveRs actApproveRs = new ActApproveRs();
        actApproveRs.setApplcNum(applcNum);
        actApproveRs.setSrcDep(srcDep);
        actApproveRs.setProcessId(processId);
        return actApproveRsMapper.insertEntity(actApproveRs);
    }

    @Override
    public Integer updateRsByApplcNum(String applcNum, String apprvRs, String curDep) {
        return actApproveRsMapper.updateRsByApplcNum(applcNum, apprvRs, new Date(), curDep);
    }

    @Override
    public Integer updateRsAndReasonByApplcNum(String applcNum, String apprvReason, String apprvRs, String curDep) {
        return actApproveRsMapper.updateRsAndReasonByApplcNum(applcNum, apprvReason, apprvRs, new Date(), curDep);
    }

    @Override
    public ActApproveRs getApproveInfoByApplcNum(String applcNum) {
        return actApproveRsMapper.getApproveInfoByApplcNum(applcNum);
    }
}
