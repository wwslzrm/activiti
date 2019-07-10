package cn.lockinlife.activiti.core.service;

import cn.lockinlife.activiti.core.entity.ActApproveHis;
import cn.lockinlife.activiti.core.entity.ActNodeInfo;
import cn.lockinlife.activiti.core.mapper.ActApproveHisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActApproveHisService implements ActApproveHisServiceI {

    @Autowired
    private ActApproveHisMapper actApproveHisMapper;

    @Override
    public Integer inertEntity(ActApproveHis actApproveHis) {
        return actApproveHisMapper.insertEntity(actApproveHis);
    }

    @Override
    public ActApproveHis getCurApproveRs(String applcNum) {
        return actApproveHisMapper.getCurApproveRs(applcNum);
    }

    @Override
    public ActApproveHis getApprvRs(String applcNum, String name) {
        return actApproveHisMapper.getApprvRs(applcNum, name);
    }

    @Override
    public Integer updateEntity(ActApproveHis currentApproveHis) {
        return actApproveHisMapper.updateEntity(currentApproveHis);
    }

}
