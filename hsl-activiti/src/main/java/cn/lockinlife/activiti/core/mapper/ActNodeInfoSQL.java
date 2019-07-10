package cn.lockinlife.activiti.core.mapper;

import cn.lockinlife.activiti.core.entity.ActNodeInfo;
import cn.lockinlife.util.LockInLifeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.jdbc.SQL;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ActNodeInfoSQL {

    private static final Logger logger = LoggerFactory.getLogger(ActNodeInfoSQL.class);

    public String insertEntity(Map<String,Object> para) {
        if (!LockInLifeUtil.vaildParams(para.get("param"))) {
            logger.info("param is null");
            return null;
        }
        if (para.get("param") instanceof ActNodeInfo) {
            ActNodeInfo entity = (ActNodeInfo) para.get("param");
            String sql = new SQL(){{
                INSERT_INTO("act_node_info");
                if (LockInLifeUtil.vaildParams(entity.getProcessId())) {
                    VALUES("processId", getValue(entity.getProcessId()));
                }
                if (LockInLifeUtil.vaildParams(entity.getName())){
                    VALUES("name", getValue(entity.getName()));
                }
                if (LockInLifeUtil.vaildParams(entity.getCname())) {
                    VALUES("cname", getValue(entity.getCname()));
                }
                if (LockInLifeUtil.vaildParams(entity.getMethod())) {
                    VALUES("method", getValue(entity.getMethod()));
                }
                if(LockInLifeUtil.vaildParams(entity.getClazz())) {
                    VALUES("clazz", getValue(entity.getClazz()));
                }
                if (LockInLifeUtil.vaildParams(entity.getType())){
                    VALUES("type", getValue(entity.getType()));
                }
                if (LockInLifeUtil.vaildParams(entity.getNodeOrder())) {
                    VALUES("nodeOrder", getValue(entity.getNodeOrder()));
                }
                if (LockInLifeUtil.vaildParams(entity.getOprDep())){
                    VALUES("oprDep", getValue(entity.getOprDep()));
                }
            }}.toString();
            logger.info(sql);
            return sql;
        } else {
            logger.info("the param is not the type of ActProcessInfoEntity");
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
