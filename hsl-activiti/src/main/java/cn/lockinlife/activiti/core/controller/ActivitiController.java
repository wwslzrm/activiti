package cn.lockinlife.activiti.core.controller;

import cn.lockinlife.activiti.core.entity.ActApproveHis;
import cn.lockinlife.activiti.core.entity.ActApproveRs;
import cn.lockinlife.activiti.core.entity.ActNodeInfo;
import cn.lockinlife.activiti.core.service.ActApproveHisServiceI;
import cn.lockinlife.activiti.core.service.ActApproveRsServiceI;
import cn.lockinlife.activiti.core.service.ActProcessInfoServiceI;
import cn.lockinlife.activiti.core.util.ActivitiDD;
import cn.lockinlife.entity.LockData;
import cn.lockinlife.util.ErrCode;
import cn.lockinlife.util.LockInLifeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

@Api(value = "工作流控制类", tags = {"工作流控制类"})
@RestController
@RequestMapping("/app/V2.0.0/activitiController")
public class ActivitiController {

    private static final Logger logger = LoggerFactory.getLogger(ActivitiController.class);

   @Autowired
   private ActProcessInfoServiceI actProcessInfoService;
   @Autowired
   private ActApproveHisServiceI actApproveHisService;
   @Autowired
   private WebApplicationContext context;
   @Autowired
   private ActivitiProcess activitiProcess;
   @Autowired
   private ActApproveRsServiceI actApproveRsService;

    @ApiOperation("开始审批流程")
    @ApiImplicitParams({
            @ApiImplicitParam(value="手机号", name="tel", dataType = "String"),
            @ApiImplicitParam(value="token", name="token", dataType = "String"),
            @ApiImplicitParam(value="审批类型", name="type", dataType = "String"),
            @ApiImplicitParam(value="申请编号", name="applcNum", dataType = "String")
    })
    @RequestMapping(value = "beginApprove.do", method = RequestMethod.POST)
    public LockData beginApprove(String tel, String token, String type, String applcNum){
        ActApproveRs rs = actApproveRsService.getApproveInfoByApplcNum(applcNum);
        if (rs != null) {
            return new LockData(false, ErrCode.unknown_err, "该申请件已经存在，请勿重复提交");
        }
        List<ActNodeInfo> actNodeInfo = actProcessInfoService.getProcessNodes(type);
        if (!LockInLifeUtil.vaildParams(actNodeInfo)) {
            return new LockData(false, ErrCode.unknown_err, "该流程不存在");
        }
        //调用第一个start接口
        ActNodeInfo startNode = actNodeInfo.get(0);
        Object clazz = context.getBean(startNode.getClazz());
        if (clazz == null || !"auto".equals(startNode.getType())) {
            logger.info("applcNum[{}]tel[{}]class[{}]type[{}]工作流节点设置有问题，请重新生成工作流信息", applcNum, tel, startNode.getClazz(), startNode.getType());
            return new LockData(false, ErrCode.unknown_err, "工作流节点设置有问题，请重新生成工作流信息");
        }
        try {
            Method method = clazz.getClass().getMethod(startNode.getMethod(), String.class, String.class);
            method.invoke(clazz, applcNum, startNode.getProcessId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("applcNum[{}]tel[{}]class[{}]method[{}]工作流节点方法设置有问题，请确认或者重新生成工作流信息errorInfo[{}]",
                    applcNum, tel, startNode.getClazz(), startNode.getMethod(), e.getMessage());
            return new LockData(false, ErrCode.unknown_err, "工作流节点方法设置有问题，请确认或者重新生成工作流信息");
        }
        return new LockData(true, ErrCode.success, "");
    }

    @ApiOperation("人工审批")
    @ApiImplicitParams({
            @ApiImplicitParam(value="手机号", name="tel", dataType = "String"),
            @ApiImplicitParam(value="token", name="token", dataType = "String"),
            @ApiImplicitParam(value="申请编号", name="applcNum", dataType = "String"),
            @ApiImplicitParam(value="审批结果", name="apprvRs", dataType = "String"),
            @ApiImplicitParam(value = "审批意见", name = "apprvReason", dataType = "String")
    })
    @RequestMapping(value = "manualApprove.do", method = RequestMethod.POST)
    public LockData manualApprove(String tel, String token, String applcNum, String apprvRs, String apprvReason){
        String nextNode = null;
        String errorDetail = null;
        Integer nextNodeId = 0;
        // 查询当前申请编号是否存在
        ActApproveHis approveInfo = actApproveHisService.getCurApproveRs(applcNum);
        if (!LockInLifeUtil.vaildParams(approveInfo)) {
            return new LockData(false, ErrCode.user_no_renting, "当前审批任务不存在");
        }
        //判断当前任务是否需要改用户去审批
        if (!tel.equals(approveInfo.getOperator())) {
            //否， 则返回该用户无审批该任务的权限
            return new LockData(false, ErrCode.unknown_err, "该用户无审批权限");
        }
        try {
            //修改任务结果
            approveInfo.setApprvRs(apprvRs);
            approveInfo.setEndTime(new Date());
            approveInfo.setApprvReason(apprvReason);
            actApproveHisService.updateEntity(approveInfo);
        } catch (Exception e){
            logger.error("tel[{}]apprvRs[{}]人工提交失败,errorDetail[{}]", tel, apprvRs, e.getMessage());
            //跳转到异常岗
            nextNode = ActivitiDD.handleExp;
            nextNodeId = ActivitiDD.handleExpId;
            errorDetail = e.getMessage();
        }
        //系统自动下一个岗位的记录
        activitiProcess.jumpToNextNode(null, applcNum, approveInfo.getProcessId(), approveInfo.getCurDep(), approveInfo.getCurId(), nextNode, nextNodeId, errorDetail);
        return LockData.successResponse();
    }


    @ApiOperation("人工审批")
    @ApiImplicitParams({
            @ApiImplicitParam(value="手机号", name="tel", dataType = "String"),
            @ApiImplicitParam(value="token", name="token", dataType = "String"),
            @ApiImplicitParam(value="审批岗位", name="department", dataType = "String"),
            @ApiImplicitParam(value="申请编号", name="applcNum", dataType = "String"),
            @ApiImplicitParam(value="审批结果", name="apprvRs", dataType = "String"),
            @ApiImplicitParam(value = "审批意见", name = "apprvReason", dataType = "String")
    })
    @RequestMapping(value = "manualApproveWithDep.do", method = RequestMethod.POST)
    public LockData manualApproveWithDep(String tel, String token, String applcNum, String department, String apprvRs, String apprvReason){
        String nextNode = null;
        String errorDetail = null;
        Integer nextNodeId = 0;
        // 查询当前申请编号是否存在
        ActApproveHis approveInfo = actApproveHisService.getCurApproveRs(applcNum);
        if (!LockInLifeUtil.vaildParams(approveInfo)) {
            return new LockData(false, ErrCode.user_no_renting, "当前审批任务不存在");
        }
        if (department == null || ! department.equals(approveInfo.getCurDep())) {
            String obj = String.format("当前流程在%s,请勿重复提交审批", approveInfo.getCurDep());
            return new LockData(false, ErrCode.unknown_err, obj);
        }
        //判断当前任务是否需要改用户去审批
//        if (!tel.equals(approveInfo.getOperator())) {
//            //否， 则返回该用户无审批该任务的权限
//            return new LockData(false, ErrCode.unknown_err, "该用户无审批权限");
//        }
        try {
            //修改任务结果
            approveInfo.setApprvRs(apprvRs);
            approveInfo.setEndTime(new Date());
            approveInfo.setApprvReason(apprvReason);
            actApproveHisService.updateEntity(approveInfo);
        } catch (Exception e){
            logger.error("tel[{}]apprvRs[{}]人工提交失败,errorDetail[{}]", tel, apprvRs, e.getMessage());
            //跳转到异常岗
            nextNode = ActivitiDD.handleExp;
            nextNodeId = ActivitiDD.handleExpId;
            errorDetail = e.getMessage();
        }
        //系统自动下一个岗位的记录
        /*activitiProcess.jumpToNextNode(null, applcNum, approveInfo.getProcessId(), approveInfo.getCurDep(), nextNode, errorDetail);*/
        activitiProcess.jumpToNextNode(null, applcNum, approveInfo.getProcessId(),
                approveInfo.getCurDep(), approveInfo.getCurId(),
                nextNode, nextNodeId, errorDetail);
        return LockData.successResponse();
    }




}
