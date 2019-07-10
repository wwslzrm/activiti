package cn.lockinlife.activiti.core.mapper;

import cn.lockinlife.activiti.core.entity.ActNodeDeploy;
import cn.lockinlife.activiti.core.entity.ActProcessInfoEntity;
import cn.lockinlife.util.LockInLifeUtil;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ActProcessInfoSQL {

    private static final Logger logger = LoggerFactory.getLogger(ActProcessInfoSQL.class);

    public String insertEntity(Map<String, Object> para) {
        if (!LockInLifeUtil.vaildParams(para.get("param"))) {
            logger.info("param is null");
            return null;
        }
        if (para.get("param") instanceof ActProcessInfoEntity) {
            ActProcessInfoEntity entity = (ActProcessInfoEntity) para.get("param");
            String sql =  new SQL(){{
                INSERT_INTO("act_process_info");
                if (LockInLifeUtil.vaildParams(entity.getType())) {
                    VALUES("type", getValue(entity.getType()));
                }
                if (LockInLifeUtil.vaildParams(entity.getCreateTime())) {
                    VALUES("createTime", getValue(entity.getCreateTime()));
                }
                if (LockInLifeUtil.vaildParams(entity.getUuid())){
                    VALUES("uuid", getValue(entity.getUuid()));
                }
                if (LockInLifeUtil.vaildParams(entity.getState())) {
                    VALUES("state", getValue(entity.getState()));
                }
                if (LockInLifeUtil.vaildParams(entity.getCname())){
                    VALUES("cname", getValue(entity.getCname()));
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
