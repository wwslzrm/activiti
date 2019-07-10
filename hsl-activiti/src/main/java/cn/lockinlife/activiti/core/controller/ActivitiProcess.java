package cn.lockinlife.activiti.core.controller;

import cn.lockinlife.activiti.core.entity.ActApproveHis;
import cn.lockinlife.activiti.core.entity.ActHandleInfo;
import cn.lockinlife.activiti.core.entity.ActNodeInfo;
import cn.lockinlife.activiti.core.service.ActApproveHisServiceI;
import cn.lockinlife.activiti.core.service.ActHandleInfoServiceI;
import cn.lockinlife.activiti.core.service.ActProcessInfoServiceI;
import cn.lockinlife.activiti.core.strategy.RoleAssignStrategy;
import cn.lockinlife.activiti.core.threadpool.ThreadPoolUtil;
import cn.lockinlife.activiti.core.util.ActivitiDD;
import cn.lockinlife.entity.LockData;
import cn.lockinlife.util.ErrCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.Callable;

@Component
public class ActivitiProcess {

    private static final Logger logger = LoggerFactory.getLogger(ActivitiProcess.class);

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ActProcessInfoServiceI actProcessInfoService;
    @Autowired
    private ActApproveHisServiceI actApproveHisService;
    @Autowired
    private ActivitiProcess activitiProcess;
    @Autowired
    private ActHandleInfoServiceI actHandleInfoService;
    @Autowired
    private RoleAssignStrategy roleAssignStrategy;

    public Object executeNextAutoNode(ActApproveHis record) {
       /* ActNodeInfo currentNode = actProcessInfoService.getCurNode(record.getProcessId(), record.getCurDep());*/
        ActNodeInfo currentNode = actProcessInfoService.getNodeByCurId(record.getCurId(), record.getProcessId());
        Object clazz = context.getBean(currentNode.getClazz());
        if (clazz == null) {
            logger.info("applcNum[{}]class[{}]type[{}]工作流节点设置有问题，请重新生成工作流信息", record.getApplcNum(), record.getCurDep());
            return new LockData(false, ErrCode.unknown_err, "工作流节点设置有问题，请重新生成工作流信息");
        }
        try {
            Method method = clazz.getClass().getMethod(currentNode.getMethod(), String.class, String.class);
            return method.invoke(clazz, record.getApplcNum(), record.getProcessId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("applcNum[{}]class[{}]type[{}] method[{}]工作流节点设置有问题，请重新生成工作流信息", record.getApplcNum(), record.getCurDep(), currentNode.getMethod());
            return new LockData(false, ErrCode.unknown_err, "工作流节点方法设置有问题，请确认或者重新生成工作流信息");
        }
    }


    public Object jumpToNextNode(Object obj, String applcNum, String processId, String srcDep, Integer srcId, String curDep, Integer curId, String errorDetail) {
        Callable<LockData> task = new Callable<LockData>() {
            @Override
            public LockData call() throws Exception {
                ActApproveHis curApproveRs = actApproveHisService.getCurApproveRs(applcNum);
                logger.info("srcId[{}]", srcId);
                ActNodeInfo nextNode = actProcessInfoService.getNextNodeBySrcId(processId, srcId);
                ActNodeInfo endNode = actProcessInfoService.getEndNode(processId);
                //如果是end节点且无异常， 则直接跳转
                if (srcDep.equals(endNode.getName()) && !ActivitiDD.handleExp.equals(curDep)) {
                    logger.info("applcNum[{}]srcDep[{}]srcId[{}] nextDep[{}]", applcNum, srcDep,srcId, srcDep);
                    return LockData.successResponse();
                }
                if (curDep != null) {
                    if (curDep.equals(ActivitiDD.handleExp)) {
                        //增加异常记录
                        ActHandleInfo actHandleInfo = new ActHandleInfo();
                        actHandleInfo.setSrcDep(srcDep);
                        actHandleInfo.setCreateTime(new Date());
                        String operator = roleAssignStrategy.assignTask(applcNum, ActivitiDD.handleExp);
                        actHandleInfo.setOperator(operator);
                        actHandleInfo.setApplcNum(applcNum);
                        actHandleInfo.setReson(errorDetail);
                        actHandleInfoService.insertEntity(actHandleInfo);
                        logger.info("applcNum[{}]srcDep[{}]nextDep[{}]", applcNum, srcDep, curDep);
                        nextNode = actProcessInfoService.getHandleExp();
                    } else {
                        logger.info("applcNum[{}]srcDep[{}]nextDep[{}]", applcNum, srcDep, curDep);
                        nextNode = actProcessInfoService.getNodeByCurId(curId, processId);
                    }
                }
                if (curApproveRs.getApprvRs().equals(ActivitiDD.ApproveState.Disaggree)) {
                    //审批拒绝，导致直接跳转到end节点
                    ActApproveHis actApproveHis = new ActApproveHis();
                    actApproveHis.setSrcDep(srcDep);
                    actApproveHis.setSrcId(srcId);
                    actApproveHis.setCurDep(endNode.getName());
                    actApproveHis.setCurId(endNode.getNodeOrder());
                    actApproveHis.setCreatTime(new Date());
                    actApproveHis.setProcessId(processId);
                    actApproveHis.setOperator("auto");
                    actApproveHis.setApplcNum(applcNum);
                    actApproveHisService.inertEntity(actApproveHis);
                    //do the auto node's logic
                    logger.info("applcNum[{}]srcDep[{}]srcId[{}]nextDep[{}]nextDepId[{}]apprvRs[{}]",
                            applcNum, srcDep,srcId, endNode.getName(), endNode.getNodeOrder(), "D");
                    activitiProcess.executeNextAutoNode(actApproveHis);
                    return LockData.successResponse();
                }
                //自动节点
                if (nextNode != null) {
                    if (nextNode.getType().equals("auto")) {
                        ActApproveHis actApproveHis = new ActApproveHis();
                        actApproveHis.setSrcDep(srcDep);
                        actApproveHis.setSrcId(srcId);
                        actApproveHis.setCurDep(nextNode.getName());
                        actApproveHis.setCurId(nextNode.getNodeOrder());
                        actApproveHis.setCreatTime(new Date());
                        actApproveHis.setProcessId(processId);
                        actApproveHis.setOperator("auto");
                        actApproveHis.setApplcNum(applcNum);
                        actApproveHisService.inertEntity(actApproveHis);
                        logger.info("applcNum[{}]srcDep[{}]srcId[{}]nextDep[{}]nextDepId[{}]apprvRs[{}]nextType[{}]",
                                applcNum, srcDep, srcId, nextNode.getName(), nextNode.getNodeOrder(),ActivitiDD.ApproveState.Agree, nextNode.getType());
                        activitiProcess.executeNextAutoNode(actApproveHis);
                        return LockData.successResponse();
                    } else if (nextNode.getType().equals("manual")) {
                        //流入人工岗
                        String operator = roleAssignStrategy.assignTask(applcNum, nextNode.getOprDep());
                        //增加待审批记录
                        ActApproveHis actApproveHis = new ActApproveHis();
                        actApproveHis.setSrcDep(srcDep);
                        actApproveHis.setSrcId(srcId);
                        actApproveHis.setCurDep(nextNode.getName());
                        actApproveHis.setCurId(nextNode.getNodeOrder());
                        actApproveHis.setCreatTime(new Date());
                        actApproveHis.setProcessId(processId);
                        actApproveHis.setOperator(operator);
                        actApproveHis.setApplcNum(applcNum);
                        actApproveHisService.inertEntity(actApproveHis);
                        logger.info("applcNum[{}]srcDep[{}]srcId[{}]nextDep[{}]nextDepId[{}]apprvRs[{}]nextType[{}]",
                                applcNum, srcDep, srcId, nextNode.getName(), nextNode.getNodeOrder(), ActivitiDD.ApproveState.Agree, nextNode.getType());
                        return LockData.successResponse();
                    }
                } else {
                    String operator = roleAssignStrategy.assignTask(applcNum, ActivitiDD.handleExp);
                    //下一个岗位为空，且非结束节点，则进入异常岗
                    ActApproveHis actApproveHis = new ActApproveHis();
                    actApproveHis.setSrcDep(srcDep);
                    actApproveHis.setSrcId(srcId);
                    actApproveHis.setCurDep(ActivitiDD.handleExp);
                    actApproveHis.setCurId(ActivitiDD.handleExpId);
                    actApproveHis.setCreatTime(new Date());
                    actApproveHis.setProcessId(processId);
                    actApproveHis.setOperator(operator);
                    actApproveHis.setApplcNum(applcNum);
                    actApproveHisService.inertEntity(actApproveHis);
                    //增加异常记录
                    logger.info("srcDep[{}]to[{}], errorDetail[nextNode not exist]", srcDep, ActivitiDD.handleExp);
                    ActHandleInfo actHandleInfo = new ActHandleInfo();
                    actHandleInfo.setSrcDep(srcDep);
                    actHandleInfo.setSrcId(srcId);
                    actHandleInfo.setCreateTime(new Date());
                    actHandleInfo.setOperator(operator);
                    actHandleInfo.setApplcNum(applcNum);
                    actHandleInfo.setReson("errorDetail[nextNode not exist]");
                    actHandleInfoService.insertEntity(actHandleInfo);
                    return LockData.successResponse();
                }
                return new LockData(false, ErrCode.unknown_err, "");
            }
        };
        try {
            ThreadPoolUtil.getThreadPool().submit(task);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return LockData.successResponse();
    }

    public Object jumpToNextNode(Object obj, String applcNum, String processId, String srcDep, String curDep, String errorDetail) {
        Callable<LockData> task = new Callable<LockData>() {
            @Override
            public LockData call() throws Exception {
                ActApproveHis curApproveRs = actApproveHisService.getCurApproveRs(applcNum);
                ActNodeInfo nextNode = actProcessInfoService.getNextNode(processId, srcDep);
                ActNodeInfo endNode = actProcessInfoService.getEndNode(processId);
                //如果是end节点且无异常， 则直接跳转
                if (srcDep.equals(endNode.getName()) && !ActivitiDD.handleExp.equals(curDep)) {
                    logger.info("applcNum[{}]srcDep[{}]nextDep[{}]", applcNum, srcDep, srcDep);
                    return LockData.successResponse();
                }
                if (curDep != null) {
                    if (curDep.equals(ActivitiDD.handleExp)) {
                        //增加异常记录
                        ActHandleInfo actHandleInfo = new ActHandleInfo();
                        actHandleInfo.setSrcDep(srcDep);
                        actHandleInfo.setCreateTime(new Date());
                        String operator = roleAssignStrategy.assignTask(applcNum, ActivitiDD.handleExp);
                        actHandleInfo.setOperator(operator);
                        actHandleInfo.setApplcNum(applcNum);
                        actHandleInfo.setReson(errorDetail);
                        actHandleInfoService.insertEntity(actHandleInfo);
                        logger.info("applcNum[{}]srcDep[{}]nextDep[{}]", applcNum, srcDep, curDep);
                        nextNode = actProcessInfoService.getHandleExp();
                    } else {
                        logger.info("applcNum[{}]srcDep[{}]nextDep[{}]", applcNum, srcDep, curDep);
                        nextNode = actProcessInfoService.getNodeByName(curDep, processId);
                    }
                }
                if (curApproveRs.getApprvRs().equals(ActivitiDD.ApproveState.Disaggree)) {
                    //审批拒绝，导致直接跳转到end节点
                    ActApproveHis actApproveHis = new ActApproveHis();
                    actApproveHis.setSrcDep(srcDep);
                    actApproveHis.setCurDep(endNode.getName());
                    actApproveHis.setCreatTime(new Date());
                    actApproveHis.setProcessId(processId);
                    actApproveHis.setOperator("auto");
                    actApproveHis.setApplcNum(applcNum);
                    actApproveHisService.inertEntity(actApproveHis);
                    //do the auto node's logic
                    logger.info("applcNum[{}]srcDep[{}]nextDep[{}]apprvRs[{}]", applcNum, srcDep, endNode.getName(), "D");
                    activitiProcess.executeNextAutoNode(actApproveHis);
                    return LockData.successResponse();
                }
                //自动节点
                if (nextNode != null) {
                    if (nextNode.getType().equals("auto")) {
                        ActApproveHis actApproveHis = new ActApproveHis();
                        actApproveHis.setSrcDep(srcDep);
                        actApproveHis.setCurDep(nextNode.getName());
                        actApproveHis.setCreatTime(new Date());
                        actApproveHis.setProcessId(processId);
                        actApproveHis.setOperator("auto");
                        actApproveHis.setApplcNum(applcNum);
                        actApproveHisService.inertEntity(actApproveHis);
                        logger.info("applcNum[{}]srcDep[{}]nextDep[{}]apprvRs[{}]nextType[{}]", applcNum, srcDep, endNode.getName(), ActivitiDD.ApproveState.Agree, nextNode.getType());
                        activitiProcess.executeNextAutoNode(actApproveHis);
                        return LockData.successResponse();
                    } else if (nextNode.getType().equals("manual")) {
                        //流入人工岗
                        String operator = roleAssignStrategy.assignTask(applcNum, nextNode.getOprDep());
                        //增加待审批记录
                        ActApproveHis actApproveHis = new ActApproveHis();
                        actApproveHis.setSrcDep(srcDep);
                        actApproveHis.setCurDep(nextNode.getName());
                        actApproveHis.setCreatTime(new Date());
                        actApproveHis.setProcessId(processId);
                        actApproveHis.setOperator(operator);
                        actApproveHis.setApplcNum(applcNum);
                        actApproveHisService.inertEntity(actApproveHis);
                        logger.info("applcNum[{}]srcDep[{}]nextDep[{}]apprvRs[{}]nextType[{}]", applcNum, srcDep, endNode.getName(), ActivitiDD.ApproveState.Agree, nextNode.getType());
                        return LockData.successResponse();
                    }
                } else {
                    String operator = roleAssignStrategy.assignTask(applcNum, ActivitiDD.handleExp);
                    //下一个岗位为空，且非结束节点，则进入异常岗
                    ActApproveHis actApproveHis = new ActApproveHis();
                    actApproveHis.setSrcDep(srcDep);
                    actApproveHis.setCurDep(ActivitiDD.handleExp);
                    actApproveHis.setCreatTime(new Date());
                    actApproveHis.setProcessId(processId);
                    actApproveHis.setOperator(operator);
                    actApproveHis.setApplcNum(applcNum);
                    actApproveHisService.inertEntity(actApproveHis);
                    //增加异常记录
                    logger.info("srcDep[{}]to[{}], errorDetail[nextNode not exist]", srcDep, ActivitiDD.handleExp);
                    ActHandleInfo actHandleInfo = new ActHandleInfo();
                    actHandleInfo.setSrcDep(srcDep);
                    actHandleInfo.setCreateTime(new Date());
                    actHandleInfo.setOperator(operator);
                    actHandleInfo.setApplcNum(applcNum);
                    actHandleInfo.setReson("errorDetail[nextNode not exist]");
                    actHandleInfoService.insertEntity(actHandleInfo);
                    return LockData.successResponse();
                }
                return new LockData(false, ErrCode.unknown_err, "");
            }
        };
        try {
            ThreadPoolUtil.getThreadPool().submit(task);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return LockData.successResponse();
    }


}
