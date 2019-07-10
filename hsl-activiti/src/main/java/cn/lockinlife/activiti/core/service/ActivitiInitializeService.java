package cn.lockinlife.activiti.core.service;

import cn.lockinlife.activiti.core.annotation.Activiti;
import cn.lockinlife.activiti.core.annotation.ActivitiNode;
import cn.lockinlife.activiti.core.entity.ActNodeDeploy;
import cn.lockinlife.activiti.core.entity.ActNodeInfo;
import cn.lockinlife.activiti.core.entity.ActProcessInfoEntity;
import cn.lockinlife.activiti.core.mapper.ActNodeDeployMapper;
import cn.lockinlife.util.LockInLifeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

@Service
public class ActivitiInitializeService implements ActivitiInitializeServiceI {

    private static final Logger logger = LoggerFactory.getLogger(ActivitiInitializeService.class);

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ActNodeDeployMapper actNodeDeployMapper;
    @Autowired
    private ActProcessInfoServiceI actProcessInfoService;

    @Override
    public void findProcessNodesByPath(String type) {
        actNodeDeployMapper.deleteAllProcess();
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(Activiti.class));
        Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents("cn.lockinlife.activiti.biz.process");
        for (BeanDefinition beanDefinition : beanDefinitions) {
            updateActivitiProcess(beanDefinition);
        }
    }

    /**
     * 创建默认的审批流程，所有节点按照默认的order进行排序
     * @param type
     */
    @Override
    public void createDefaultProcess(String type) {
        List<ActNodeDeploy> nodes = actNodeDeployMapper.getNodesByProcessName(type);
        actProcessInfoService.createCurDefaultProcess(nodes);
    }

    //更新工作流流程图
    private void updateActivitiProcess(BeanDefinition beanDefinition) {
        try {
            //获得Activiti注解信息
            Class clazz = Class.forName(beanDefinition.getBeanClassName());
            String beanName = firstLetterLowercase(clazz.getSimpleName());
            Object beanClazz = context.getBean(beanName);
            Activiti process = beanClazz.getClass().getAnnotation(Activiti.class);

            //获取方法上的注解信息
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                ActivitiNode node = method.getAnnotation(ActivitiNode.class);
                if (node!=null) {
                    ActNodeDeploy entity = new ActNodeDeploy();
                    entity.setProcessName(process.value());
                    entity.setProcessTag(process.tag());
                    entity.setEname(node.value());
                    entity.setCname(node.tag());
                    entity.setMethod(method.getName());
                    entity.setClazz(process.value());
                    entity.setType(node.type());
                    entity.setNodeOrder(node.order());
                    entity.setOprDep(node.oprDep());
                    actNodeDeployMapper.insertEntity(entity);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String firstLetterLowercase(String className) {
        char[] chars = className.toCharArray();
        chars[0] += 32;
        String var = String.valueOf(chars);
        logger.info("char[{}]", var);
        return var;
    }

}
