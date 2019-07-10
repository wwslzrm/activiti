package cn.lockinlife.activiti.core.service;

import org.springframework.stereotype.Service;

@Service
public interface ActivitiInitializeServiceI {

    void findProcessNodesByPath(String type);

    void createDefaultProcess(String type);
}
