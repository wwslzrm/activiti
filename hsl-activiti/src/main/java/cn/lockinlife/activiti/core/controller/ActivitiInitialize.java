package cn.lockinlife.activiti.core.controller;

import cn.lockinlife.activiti.core.service.ActivitiInitializeServiceI;
import cn.lockinlife.entity.LockData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "工作流初始化控制类", tags = {"工作流初始化控制类"})
@RestController
@RequestMapping("/activitiInitialize")
public class ActivitiInitialize {


    @Autowired
    private ActivitiInitializeServiceI activitiInitializeService;

    @ApiOperation("导入工作流节点")
    @RequestMapping(value = "findProcessNodes.do", method = RequestMethod.POST)
    public void findProcessNodes(String type){
        activitiInitializeService.findProcessNodesByPath(type);
    }


    @ApiOperation("创建默认的审批流程，所有节点按照默认的order进行执行")
    @ApiImplicitParams({
            @ApiImplicitParam(value="审批类型", name="type", dataType = "String", example = "unLockTaskProcess")
    })
    @RequestMapping(value = "createDefaultProcess.do", method = RequestMethod.POST)
    public void createDefaultProcess(String type) {
        activitiInitializeService.createDefaultProcess(type);
    }

    /**
     * 该功能由JEECG后台实现
     */
    /*@ApiOperation("创建工作流")
    @ApiImplicitParams({
            @ApiImplicitParam(value="审批类型", name="processName", dataType = "String", example = "Approval")
    })
    @RequestMapping(value = "createProcess.do", method = RequestMethod.POST)
    public LockData createProcess(String processName){
        return null;
    }*/

}
