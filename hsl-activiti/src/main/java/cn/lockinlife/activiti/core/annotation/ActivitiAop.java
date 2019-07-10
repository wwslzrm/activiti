package cn.lockinlife.activiti.core.annotation;

import cn.lockinlife.activiti.core.controller.ActivitiProcess;
import cn.lockinlife.activiti.core.entity.ActApproveHis;
import cn.lockinlife.activiti.core.service.ActApproveHisServiceI;
import cn.lockinlife.activiti.core.service.ActHandleInfoServiceI;
import cn.lockinlife.activiti.core.strategy.RoleAssignStrategy;
import cn.lockinlife.activiti.core.util.ActivitiDD;
import cn.lockinlife.entity.LockData;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;


@Aspect
@Component
public class ActivitiAop {

    private static final Logger logger = LoggerFactory.getLogger(ActivitiAop.class);

    @Autowired
    private ActivitiProcess activitiProcess;
    @Autowired
    private ActApproveHisServiceI actApproveHisService;
    @Autowired
    private RoleAssignStrategy roleAssignStrategy;
    @Autowired
    private ActHandleInfoServiceI actHandleInfoService;

    @Around("@annotation(ActivitiNode)")
    public Object around(ProceedingJoinPoint point) throws Throwable{

        Object[] args = point.getArgs();
        String applcNum = args[0].toString();
        String processId = args[1].toString();

        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        ActivitiNode node = method.getAnnotation(ActivitiNode.class);
        String nextNode = null;
        String errorDetail = null;
        Integer nextNodeId = 0;
        ActApproveHis curApproveRs = null;
        //执行代码逻辑
        Object obj = null;
        try {
            obj = point.proceed();
        } catch (Exception e) {
            /**
             * 当前节点设置为不处理的状态
             */
            curApproveRs = actApproveHisService.getCurApproveRs(applcNum);
            curApproveRs.setEndTime(new Date());
            curApproveRs.setApprvRs(ActivitiDD.ApproveState.NoOption);
            curApproveRs.setApprvReason("异常件，等待异常岗处理");
            actApproveHisService.updateEntity(curApproveRs);
            nextNode = ActivitiDD.handleExp;
            nextNodeId = ActivitiDD.handleExpId;
            errorDetail = e.getMessage();
        }
        if (curApproveRs == null) {
            curApproveRs = actApproveHisService.getCurApproveRs(applcNum);
        }
        //跳转到下一个岗位节点
        activitiProcess.jumpToNextNode(obj, applcNum, processId, node.value(), curApproveRs.getCurId(),  nextNode, nextNodeId, errorDetail);
        return LockData.successResponse();
    }
}
