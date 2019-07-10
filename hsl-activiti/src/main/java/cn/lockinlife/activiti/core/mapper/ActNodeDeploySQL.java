package cn.lockinlife.activiti.core.mapper;

import cn.lockinlife.activiti.core.entity.ActNodeDeploy;
import cn.lockinlife.util.LockInLifeUtil;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ActNodeDeploySQL {

    private static final Logger logger = LoggerFactory.getLogger(ActNodeDeploySQL.class);

    public String insertEntity(Map<String, Object> para) {
        if (!LockInLifeUtil.vaildParams(para.get("param"))) {
            logger.info("param is null");
            return null;
        }
        if (para.get("param") instanceof ActNodeDeploy) {
            ActNodeDeploy entity = (ActNodeDeploy) para.get("param");
            return new SQL(){{
                INSERT_INTO("act_node_deploy");
                if(LockInLifeUtil.vaildParams(entity.getProcessName())){
                    VALUES("processName", getValue(entity.getProcessName()));
                }
                if (LockInLifeUtil.vaildParams(entity.getProcessTag())) {
                    VALUES("processTag", getValue(entity.getProcessTag()));
                }
                if (LockInLifeUtil.vaildParams(entity.getEname())){
                    VALUES("ename", getValue(entity.getEname()));
                }
                if (LockInLifeUtil.vaildParams(entity.getCname())) {
                    VALUES("cname", getValue(entity.getCname()));
                }
                if (LockInLifeUtil.vaildParams(entity.getMethod())){
                    VALUES("method", getValue(entity.getMethod()));
                }
                if (LockInLifeUtil.vaildParams(entity.getClazz())) {
                    VALUES("clazz", getValue(entity.getClazz()));
                }
                if (LockInLifeUtil.vaildParams(entity.getType())){
                    VALUES("type", getValue(entity.getType()));
                }
                if(LockInLifeUtil.vaildParams(entity.getNodeOrder())) {
                    VALUES("nodeOrder",getValue(entity.getNodeOrder()));
                }
                if (LockInLifeUtil.vaildParams(entity.getOprDep())) {
                    VALUES("oprDep", getValue(entity.getOprDep()));
                }
            }}.toString();
        } else {
            logger.info("the param is not the type of ActNodeDeploy");
            return null;
        }
    }

    private String getValue(Object obj) {
        String result = obj.toString();
        if (obj instanceof Date) {
            result = getTime((Date) obj);
        }
        if (obj instanceof  Integer) {
            return result;
        }
        result = "'" + result + "'";
        return result;
    }

    private String getTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

}
