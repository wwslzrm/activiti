package cn.lockinlife.activiti.core.controller;

import cn.lockinlife.activiti.core.entity.ActApproveHis;
import cn.lockinlife.activiti.core.entity.ActHandleInfo;
import cn.lockinlife.activiti.core.entity.ActNodeInfo;
import cn.lockinlife.activiti.core.entity.ActProcessInfoEntity;
import cn.lockinlife.activiti.core.service.ActApproveHisServiceI;
import cn.lockinlife.activiti.core.service.ActHandleInfoServiceI;
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

import java.util.Date;

@Api(value = "异常岗类", tags = {"异常岗类"})
@RestController
@RequestMapping("/app/V2.0.0/activitiHandleExpController")
public class ActivitiHandleExpController {

    public static final Logger logger = LoggerFactory.getLogger(ActivitiHandleExpController.class);

    @Autowired
    private ActApproveHisServiceI actApproveHisService;
    @Autowired
    private ActivitiProcess activitiProcess;
    @Autowired
    private ActProcessInfoServiceI actProcessInfoService;
    @Autowired
    private ActivitiController activitiController;
    @Autowired
    private ActHandleInfoServiceI actHandleInfoService;


    /**
     * 重新提交回原岗位
     * @return
     */
    @ApiOperation("重新提交回原岗位")
    @ApiImplicitParams({
            @ApiImplicitParam(value="手机号", name="tel", dataType = "String"),
            @ApiImplicitParam(value="token", name="token", dataType = "String"),
            @ApiImplicitParam(value="申请编号", name="applcNum", dataType = "String")
    })
    @RequestMapping(value = "resubmit.do", method = RequestMethod.POST)
    public LockData resubmit(String tel, String token, String applcNum){
        //查询当前审批记录是否存在
        ActApproveHis approveInfo = actApproveHisService.getCurApproveRs(applcNum);
        ActHandleInfo handleInfo = actHandleInfoService.getHandleByApplcNum(applcNum);
        if(! LockInLifeUtil.vaildParams(approveInfo)) {
            return new LockData(false, ErrCode.user_no_renting, "当前审批任务不存在");
        }
        if (! tel.equals(approveInfo.getOperator())) {
            return new LockData(false, ErrCode.unknown_err, "该用户无审批权限");
        }
        //修改任务结果--重新提交表示异常岗审批通过了
        approveInfo.setApprvRs(ActivitiDD.ApproveState.NoOption);
        approveInfo.setEndTime(new Date());
        actApproveHisService.updateEntity(approveInfo);
        //更新异常记录
        handleInfo.setApprvRs(ActivitiDD.ApproveState.NoOption);
        handleInfo.setEndTime(new Date());
        actHandleInfoService.updateEntity(handleInfo);
        //跳转回原来的岗位
        activitiProcess.jumpToNextNode(null, applcNum, approveInfo.getProcessId(),
                approveInfo.getCurDep(), approveInfo.getCurId(),
                approveInfo.getSrcDep(),approveInfo.getSrcId(), null);
        return LockData.successResponse();
    }

    @ApiOperation("异常岗直接拒绝")
    @ApiImplicitParams({
            @ApiImplicitParam(value="手机号", name="tel", dataType = "String"),
            @ApiImplicitParam(value="token", name="token", dataType = "String"),
            @ApiImplicitParam(value="申请编号", name="applcNum", dataType = "String")
    })
    @RequestMapping(value = "handleReject.do", method = RequestMethod.POST)
    public LockData handleReject(String tel, String token, String applcNum) {
        //查询当前审批记录是否存在
        ActApproveHis approveInfo = actApproveHisService.getCurApproveRs(applcNum);
        if(! LockInLifeUtil.vaildParams(approveInfo)) {
            return new LockData(false, ErrCode.user_no_renting, "当前审批任务不存在");
        }
        if (! tel.equals(approveInfo.getOperator())) {
            return new LockData(false, ErrCode.unknown_err, "该用户无审批权限");
        }
        //修改任务结果--重新提交表示异常岗审批通过了
        approveInfo.setApprvRs(ActivitiDD.ApproveState.Disaggree);
        approveInfo.setEndTime(new Date());
        actApproveHisService.updateEntity(approveInfo);
        //更新异常记录
        ActHandleInfo handleInfo = actHandleInfoService.getHandleByApplcNum(applcNum);
        if (handleInfo == null){
            return new LockData(false, ErrCode.unknown_err, "该用户无审批权限");
        }
        handleInfo.setApprvRs(ActivitiDD.ApproveState.Disaggree);
        handleInfo.setEndTime(new Date());
        actHandleInfoService.updateEntity(handleInfo);

        //跳转到END节点
        ActNodeInfo endNode = actProcessInfoService.getEndNode(approveInfo.getProcessId());
        activitiProcess.jumpToNextNode(null, applcNum, approveInfo.getProcessId(),
                approveInfo.getCurDep(), approveInfo.getCurId(),
                endNode.getName(), endNode.getNodeOrder(), null);
        return LockData.successResponse();
    }

    @ApiOperation("异常岗强制审批通过")
    @ApiImplicitParams({
            @ApiImplicitParam(value="手机号", name="tel", dataType = "String"),
            @ApiImplicitParam(value="token", name="token", dataType = "String"),
            @ApiImplicitParam(value="申请编号", name="applcNum", dataType = "String")
    })
    @RequestMapping(value = "handleAgree.do", method = RequestMethod.POST)
    public LockData handleAgree(String tel, String token, String applcNum) {
        //查询当前审批记录是否存在
        ActApproveHis approveInfo = actApproveHisService.getCurApproveRs(applcNum);
        if(! LockInLifeUtil.vaildParams(approveInfo)) {
            return new LockData(false, ErrCode.user_no_renting, "当前审批任务不存在");
        }
        if (! tel.equals(approveInfo.getOperator())) {
            return new LockData(false, ErrCode.unknown_err, "该用户无审批权限");
        }
        //修改任务结果--重新提交表示异常岗审批通过了
        approveInfo.setApprvRs(ActivitiDD.ApproveState.Agree);
        approveInfo.setEndTime(new Date());
        actApproveHisService.updateEntity(approveInfo);

        //更新异常记录
        ActHandleInfo handleInfo = actHandleInfoService.getHandleByApplcNum(applcNum);
        handleInfo.setApprvRs(ActivitiDD.ApproveState.Agree);
        handleInfo.setEndTime(new Date());
        actHandleInfoService.updateEntity(handleInfo);

        //跳转到END节点
        ActNodeInfo endNode = actProcessInfoService.getEndNode(approveInfo.getProcessId());
        activitiProcess.jumpToNextNode(null, applcNum, approveInfo.getProcessId(),
                approveInfo.getCurDep(), approveInfo.getCurId(),
                endNode.getName(), endNode.getNodeOrder(), null);
        return LockData.successResponse();
    }


    @ApiOperation("异常岗重新审批（暂未开发完成）")
    @ApiImplicitParams({
            @ApiImplicitParam(value="手机号", name="tel", dataType = "String"),
            @ApiImplicitParam(value="token", name="token", dataType = "String"),
            @ApiImplicitParam(value="申请编号", name="applcNum", dataType = "String")
    })
    @RequestMapping(value = "resumbitApproval.do", method = RequestMethod.POST)
    public LockData resumbitApproval(String tel, String token, String applcNum){
        //查询当前审批记录是否存在
        ActApproveHis approveInfo = actApproveHisService.getCurApproveRs(applcNum);
        if(! LockInLifeUtil.vaildParams(approveInfo)) {
            return new LockData(false, ErrCode.user_no_renting, "当前审批任务不存在");
        }
        if (! tel.equals(approveInfo.getOperator())) {
            return new LockData(false, ErrCode.unknown_err, "该用户无审批权限");
        }
        //修改任务结果--重新提交表示异常岗审批通过了
        approveInfo.setApprvRs(ActivitiDD.ApproveState.NoOption);
        approveInfo.setEndTime(new Date());
        actApproveHisService.updateEntity(approveInfo);
        //更新act_handle_info异常岗的信息
        ActHandleInfo actHandleInfo = actHandleInfoService.getHandleByApplcNum(applcNum);
        actHandleInfo.setApprvRs(ActivitiDD.ApproveState.NoOption);
        actHandleInfo.setEndTime(new Date());
        actHandleInfoService.updateEntity(actHandleInfo);
        //跳到BEGIN节点的下一个节点
        ActNodeInfo beginNode = actProcessInfoService.getBeginNode(approveInfo.getProcessId());
        /*ActNodeInfo nextNode = actProcessInfoService.getNextNode(approveInfo.getProcessId(), beginNode.getName());*/
        ActNodeInfo nextNode = actProcessInfoService.getNodeByCurId(beginNode.getNodeOrder(), approveInfo.getProcessId());
        activitiProcess.jumpToNextNode(null, applcNum, approveInfo.getProcessId(),
                approveInfo.getCurDep(), approveInfo.getCurId(),
                nextNode.getName(), nextNode.getNodeOrder(), null);
        return LockData.successResponse();
    }

}
