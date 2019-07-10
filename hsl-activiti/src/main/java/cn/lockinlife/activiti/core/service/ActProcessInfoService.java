package cn.lockinlife.activiti.core.service;

import cn.lockinlife.activiti.core.entity.ActNodeDeploy;
import cn.lockinlife.activiti.core.entity.ActNodeInfo;
import cn.lockinlife.activiti.core.entity.ActProcessInfoEntity;
import cn.lockinlife.activiti.core.mapper.ActNodeInfoMapper;
import cn.lockinlife.activiti.core.mapper.ActProcessInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ActProcessInfoService implements ActProcessInfoServiceI {

    @Autowired
    private ActProcessInfoMapper actProcessInfoMapper;

    @Autowired
    private ActNodeInfoMapper actNodeInfoMapper;

    @Override
    public List<ActNodeInfo> getProcessNodes(String type) {
        return actProcessInfoMapper.getProcessNodes(type);
    }

    @Override
    public List<ActNodeInfo> getProcessNodesByProcessId(String processId) {
        return actProcessInfoMapper.getProcessNodesByProcessId(processId);
    }

    @Override
    public ActNodeInfo getNextNode(String processId, String value) {
        return actProcessInfoMapper.getNextNode( processId, value);
    }

    @Override
    public ActNodeInfo getCurNode(String processId, String value) {
        return actProcessInfoMapper.getCurNode(processId, value);
    }

    @Override
    public ActNodeInfo getEndNode(String processId) {
        return actProcessInfoMapper.getEndNode(processId);
    }

    @Override
    public ActNodeInfo getNodeByProcessIdAndNodeName(String processId, String nodeName) {
        return actProcessInfoMapper.getNodeByProcessIdAndNodeName(processId, nodeName);
    }

    @Override
    public ActProcessInfoEntity getProcessInfoByProcessId(String processId) {
        return actProcessInfoMapper.getProcessInfoByProcessId(processId);
    }

    @Override
    public Integer createCurDefaultProcess(List<ActNodeDeploy> nodes) {
        String type = nodes.get(0).getProcessName();
        String cname = nodes.get(0).getProcessTag();
        //更新act_process_info记录为历史记录
        actProcessInfoMapper.updateStateByType(type);
        //增加当前act_process_info记录
        ActProcessInfoEntity actProcessInfoEntity = new ActProcessInfoEntity();
        actProcessInfoEntity.setType(type);
        actProcessInfoEntity.setCreateTime(new Date());
        String uuid = UUID.randomUUID().toString();
        actProcessInfoEntity.setUuid(uuid);
        actProcessInfoEntity.setState("0");
        actProcessInfoEntity.setCname(cname);
        actProcessInfoMapper.insertEntity(actProcessInfoEntity);
        //增加当前act_node_info记录
        for (ActNodeDeploy node : nodes) {
            ActNodeInfo curNode = new ActNodeInfo();
            curNode.setProcessId(uuid);
            curNode.setName(node.getEname());
            curNode.setCname(node.getCname());
            curNode.setMethod(node.getMethod());
            curNode.setClazz(node.getClazz());
            curNode.setType(node.getType());
            curNode.setNodeOrder(node.getNodeOrder());
            curNode.setOprDep(node.getOprDep());
            actNodeInfoMapper.insertEntity(curNode);
        }
        return 1;
    }

    @Override
    public ActNodeInfo getHandleExp() {
        return actProcessInfoMapper.getHandleExp();
    }

    @Override
    public ActNodeInfo getNodeByName(String name, String processId) {
        return actProcessInfoMapper.getNodeByName(name, processId);
    }

    @Override
    public ActNodeInfo getBeginNode(String processId) {
        return actProcessInfoMapper.getBeginNode(processId);
    }

    @Override
    public ActNodeInfo getNextNodeBySrcId(String processId, Integer srcId) {
        return actProcessInfoMapper.getNextNodeBySrcId(processId, srcId);
    }

    @Override
    public ActNodeInfo getNodeByCurId(Integer curId, String processId) {
        return actProcessInfoMapper.getNodeByCurId(curId, processId);
    }
}
