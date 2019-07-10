本项目是基于activit流程思想，开发的一款基于注解方式实现的工作流。


备注：
    以下提到的岗位和节点，都是同一个含义。


1. demo演示
1.1 执行dist目录下面的actitivi.sql
1.2 修改activit项目spring-dev.properties文件中
spring.datasource.url， spring.datasource.username，spring.datasource.password配置数据连接地址.
1.3 运行HslActivitiApp中的main函数,启动项目程序
1.4 登录 http://localhost:7022/swagger-ui.html目录，查看API接口
1.3.执行findProcessNodes.do方法，导入工作流节点（工作流节点信息来自cn.hsl.activiti.biz.process目录下，被Activiti注解的类）
至此可在ACT_NODE_DEPLOY表中产生不同节点信息（例如开始节点，结束节点， 人工审批节点 -- 可类比于activiti工作流中的人工节点和自动节点）
1.4 执行createDefaultProcess.do方法，产生默认的审批流程
至此将之前生成的节点，按order顺序由小到大， 生成一个审批流程。
1.5 执行beginApprove.do 开始审批流程
至此可查看act_approve_his审批历史轨迹表，查看审批轨迹和当前节点位置。
1.6 如有人工节点，执行manualApprove.do， 进行人工审批。
1.7 直到所有节点都执行完毕，可在act_approve_rs审批结果表中，查看最终的审批结果
1.8 节点在流程中执行若遇到网络问题或者其他问题，会自动进入异常岗（该岗位节点主要是处理审批过程中有异常的审批件，例如网络问题导致节点无法执行等）
至此，会在act_handle_info中插入一条异常记录
1.9 异常岗专员可以通过执行resubmit.do重新提交会原来的节点（例如审批由于网络异常从节点A进入异常岗，重新提交之后， 会会到A节点，重新执行A的逻辑，保证审批能够顺利的执行下去）； 异常岗专员可以通过执行handleAgree.do / handleReject.do 直接结束该审批。

数据表说明
act_approve_his表: 审批轨迹表
act_approve_rs表：审批结果表
act_node_deploy: 流程控件表（存储流程所有可用的节点）
act_process_info: 审批流程表(存储审批的流程信息，当前流程和历史流程）
act_node_info： 审批流程子表（存储审批流程中所有流程节点信息）
act_handle_info: 异常记录表
act_role_assign: 分件表（存储审批过程中不同人工节点审批者的信息）


开发文档说明：
1. 执行findProcessNodes.do 通过ClassPathScanningCandidateComponentProvider方法，查询对应目录下面，被ACTIVITI注解注释的类，并获得Activiti，AcivitiNode注解信息， 得到可用工作流节点控件信息。
2. 执行createDefaultProcess.do, 根据可用工作流节点控件信息，按照activitiNode定义的ORDER顺序，生成可用的审批流程
3. 执行beginApprove.do, 开始审批流程，根据审批类型（type登录activiti注解的value值）开始审批， 通过springIOC容器得到第一个节点的类和方法，并执行。
4. 在执行完节点的代码逻辑之后，通过spring AOP的方式（调用ActivitiAOP方法），决定下一个节点的信息（若下一个节点为自动节点，则通过线程池的方式，执行下一个节点的方法，提高给前端反馈的效率）。
    若当前节点出错，在异常捕获中获取异常信息，并进入异常岗；
    若当前节点为结束节点，则直接结束审批流程；
    若当前节点审批结果为拒绝，则直接进入结束节点；
    若当前节点审批结果为同意， 则判断下一个节点是自动节点，还是人工节点：
        若是人工节点： 则在act_approve_his审批轨迹表中增加下一个节点的记录；
        若是人工节点； 则在act_approve_his审批轨迹表中增加下一个节点的记录，并通过线程池方式，异步调用下一个节点的逻辑；
5. 分件规则可分为审批前指定和审批中指定。
    审批前指定： 表示用户在发起审批的时候，同步在act_assign分件表中设置对应人工节点的处理人（类似钉钉请假审批，由请假者指定审批人）
    审批中指定： 表示只有审批到达该节点的时候，才会分配人员。（例如银行信用卡审批，根据平均原则，将审批分给不同的审批员）
    现有规则如下： 若当前审批到达该节点，从act_assign分件表中查询该节点审批人员是否存在。
        若存在，则直接指定预先分配的审批员。
        若不存在，则通过一定的策略，分批审批员。