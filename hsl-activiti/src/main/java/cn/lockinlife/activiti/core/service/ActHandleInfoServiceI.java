package cn.lockinlife.activiti.core.service;

import cn.lockinlife.activiti.core.entity.ActHandleInfo;

public interface ActHandleInfoServiceI {
    ActHandleInfo getHandleByApplcNum(String applcNum);

    Integer updateEntity(ActHandleInfo handleInfo);

    Integer insertEntity(ActHandleInfo actHandleInfo);
}
