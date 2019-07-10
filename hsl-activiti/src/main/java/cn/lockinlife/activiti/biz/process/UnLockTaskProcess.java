package cn.lockinlife.activiti.biz.process;

import cn.lockinlife.activiti.core.annotation.Activiti;
import cn.lockinlife.activiti.core.annotation.ActivitiNode;
import cn.lockinlife.activiti.core.entity.ActApproveHis;
import cn.lockinlife.activiti.core.entity.ActNodeInfo;
import cn.lockinlife.activiti.core.service.ActApproveHisServiceI;
import cn.lockinlife.activiti.core.service.ActApproveRsServiceI;
import cn.lockinlife.activiti.core.service.ActProcessInfoServiceI;
import cn.lockinlife.activiti.core.util.ActivitiDD;
import cn.lockinlife.entity.LockData;
import cn.lockinlife.util.ErrCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Activiti(value = "unLockTaskProcess", tag = "开锁任务工作流")
public class UnLockTaskProcess {

    private static final Logger logger = LoggerFactory.getLogger(UnLockTaskProcess.class);

    @Autowired
    private ActApproveHisServiceI actApproveHisService;
    @Autowired
    private ActProcessInfoServiceI actProcessInfoService;
    @Autowired
    private ActApproveRsServiceI actApproveRsService;

    @ActivitiNode(value = "beginNode", tag = "开始节点", order = 0, type = "auto")
    public LockData beginNode(String applcNum, String processId){
        // 1. 创建审批过程表
        List<ActNodeInfo> nodes = actProcessInfoService.getProcessNodesByProcessId(processId);
        ActNodeInfo beginNode = nodes.get(0);
        ActApproveHis actApproveHis = new ActApproveHis(applcNum, processId, beginNode.getName(), 0, beginNode.getName(), 0, "auto");
        Date date = new Date();
        actApproveHis.setEndTime(date);
        actApproveHis.setApprvRs(ActivitiDD.ApproveState.Agree);
        Integer hisInsertRs = actApproveHisService.inertEntity(actApproveHis);
        logger.info("apprvHis result[{}]", hisInsertRs);
        // 2. 创建审批结果表--待审批状态
        actApproveRsService.createWaitingApproval("beginNode", applcNum, processId);
        return new LockData(true, ErrCode.success, "");
    }

    @ActivitiNode(value = "endNode", tag = "结束节点", order = 999, type = "auto")
    public LockData endNode(String applcNum, String processId) {
        //1.增加审批过程表 -跳转到end节点，审批结果与来源岗位审批结果一致
        ActApproveHis apprvRs = actApproveHisService.getApprvRs(applcNum, "endNode");
        ActApproveHis currentApproveHis = actApproveHisService.getCurApproveRs(applcNum);
        currentApproveHis.setEndTime(new Date());
        currentApproveHis.setApprvRs(apprvRs.getApprvRs());
        actApproveHisService.updateEntity(currentApproveHis);
        //2.更新审批结果表 -- 审批结果
        actApproveRsService.updateRsAndReasonByApplcNum(applcNum, apprvRs.getApprvReason(), apprvRs.getApprvRs(), "endNode");
        return new LockData(true, ErrCode.success, "");
    }


    @ActivitiNode(value = "errorNode", tag = "errorNode", order = 1, type = "auto", oprDep = "jingli")
    public LockData errorNode(String applcNum, String processId) {
        Random random = new Random();
        int i = random.nextInt(2);
        System.out.println(i);
        if (i == 0) {
            throw new RuntimeException("123");
        } else {
            ActApproveHis currentApproveHis = actApproveHisService.getCurApproveRs(applcNum);
            currentApproveHis.setEndTime(new Date());
            currentApproveHis.setApprvRs("A");
            actApproveHisService.updateEntity(currentApproveHis);
        }

        //return new LockData(true, ErrCode.success, "");
        return null;
    }

    @ActivitiNode(value = "testNode", tag = "测试节点", order = 2, type = "manual", oprDep = "jingli")
    public LockData testNode(String applcNum, String processId) {
        return new LockData(true, ErrCode.success, "");
    }

    @ActivitiNode(value = "test2Node", tag = "测试节点", order = 3, type = "manual", oprDep = "jingli2")
    public LockData test2Node(String applcNum, String processId) {
        return new LockData(true, ErrCode.success, "");
    }

}
