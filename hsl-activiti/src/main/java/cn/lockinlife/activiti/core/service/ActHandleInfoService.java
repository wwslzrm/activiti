package cn.lockinlife.activiti.core.service;

import cn.lockinlife.activiti.core.entity.ActHandleInfo;
import cn.lockinlife.activiti.core.mapper.ActHandleInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActHandleInfoService implements ActHandleInfoServiceI {

    @Autowired
    private ActHandleInfoMapper actHandleInfoMapper;

    @Override
    public ActHandleInfo getHandleByApplcNum(String applcNum) {
        return actHandleInfoMapper.getHandleByApplcNum(applcNum);
    }

    @Override
    public Integer updateEntity(ActHandleInfo handleInfo) {
        return actHandleInfoMapper.updateEntity(handleInfo);
    }

    @Override
    public Integer insertEntity(ActHandleInfo actHandleInfo) {
        return actHandleInfoMapper.insertEntity(actHandleInfo);
    }
}
