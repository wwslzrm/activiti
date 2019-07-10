package cn.lockinlife.activiti.core.mapper;

import cn.lockinlife.activiti.core.entity.ActHandleInfo;
import cn.lockinlife.util.LockInLifeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.jdbc.SQL;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ActHandleInfoSQL {

    private static final Logger logger = LoggerFactory.getLogger(ActHandleInfoSQL.class);

    public String insertEntity(Map<String, Object> para) {
        Object obj = para.get("param");
        if (obj == null) {
            logger.info("the param is null");
            return null;
        }
        if (obj instanceof ActHandleInfo) {
            ActHandleInfo entity = (ActHandleInfo) obj;
            String sql = new SQL(){{
                INSERT_INTO("act_handle_info");
                if (LockInLifeUtil.vaildParams(entity.getSrcDep())) {
                    VALUES("srcDep", getValue(entity.getSrcDep()));
                }
                if (LockInLifeUtil.vaildParams(entity.getCreateTime())) {
                    VALUES("createTime", getValue(entity.getCreateTime()));
                }
                if (LockInLifeUtil.vaildParams(entity.getOperator())) {
                    VALUES("operator", getValue(entity.getOperator()));
                }
                if (LockInLifeUtil.vaildParams(entity.getApplcNum())) {
                    VALUES("applcNum", getValue(entity.getApplcNum()));
                }
                if (LockInLifeUtil.vaildParams(entity.getReson())) {
                    VALUES("reason", getValue(entity.getReson()));
                }
                if (LockInLifeUtil.vaildParams(entity.getSrcId())){
                    VALUES("srcId", getValue(entity.getSrcId()));
                }
            }}.toString();
            logger.info(sql);
            return sql;
        } else {
            logger.info("the param is not the type of ActHandleInfo");
            return null;
        }
    }


    public String updateEntity(Map<String, Object> para) {
        Object obj = para.get("param");
        if (obj == null) {
            logger.info("the param is null");
            return null;
        }
        if (obj instanceof ActHandleInfo) {
            ActHandleInfo entity = (ActHandleInfo) obj;
            String sql = new SQL(){{
                UPDATE("act_handle_info");
                if (LockInLifeUtil.vaildParams(entity.getEndTime())) {
                    SET("endTime =" + getValue(entity.getEndTime()));
                }
                if (LockInLifeUtil.vaildParams(entity.getApprvRs())) {
                    SET("apprvRs =" + getValue(entity.getApprvRs()));
                }
                WHERE("id = " + entity.getId());
            }}.toString();
            logger.info(sql);
            return sql;
        } else {
            logger.info("the param is not the type of ActHandleInfo");
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
