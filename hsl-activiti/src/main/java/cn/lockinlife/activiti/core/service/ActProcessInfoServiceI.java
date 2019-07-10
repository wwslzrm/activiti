package cn.lockinlife.activiti.core.service;

import cn.lockinlife.activiti.core.entity.ActNodeDeploy;
import cn.lockinlife.activiti.core.entity.ActNodeInfo;
import cn.lockinlife.activiti.core.entity.ActProcessInfoEntity;

import java.util.List;

public interface ActProcessInfoServiceI {
    List<ActNodeInfo> getProcessNodes(String type);

    List<ActNodeInfo> getProcessNodesByProcessId(String processId);

    ActNodeInfo getNextNode(String processId, String value);

    ActNodeInfo getCurNode(String processId, String value);

    ActNodeInfo getEndNode(String processId);

    ActNodeInfo getNodeByProcessIdAndNodeName(String processId, String nodeName);

    ActProcessInfoEntity getProcessInfoByProcessId(String processId);

    Integer createCurDefaultProcess(List<ActNodeDeploy> nodes);

    ActNodeInfo getHandleExp();

    ActNodeInfo getNodeByName(String name, String processId);

    ActNodeInfo getBeginNode(String processId);

    ActNodeInfo getNextNodeBySrcId(String processId, Integer srcId);

    ActNodeInfo getNodeByCurId(Integer curId, String processId);
}
