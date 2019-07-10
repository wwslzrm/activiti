package cn.lockinlife.activiti.core.mapper;

import cn.lockinlife.activiti.core.entity.ActApproveHis;
import cn.lockinlife.util.LockInLifeUtil;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ActApproveHisSQL {

    private static final Logger logger = LoggerFactory.getLogger(ActApproveHisSQL.class);

    public String insertEntity(Map<String, Object> para) {
        if (!LockInLifeUtil.vaildParams(para.get("param"))) {
            logger.info("param is null");
            return null;
        }
        if (para.get("param") instanceof ActApproveHis) {
            ActApproveHis entity = (ActApproveHis) para.get("param");
            String sql = new SQL(){{
                INSERT_INTO("act_approve_his");
                if (LockInLifeUtil.vaildParams(entity.getSrcDep())) {
                    VALUES("srcDep", getValue(entity.getSrcDep()));
                }
                if (LockInLifeUtil.vaildParams(entity.getCurDep())) {
                    VALUES("curDep", getValue(entity.getCurDep()));
                }
                VALUES("createTime", getValue(new Date()));
                if (LockInLifeUtil.vaildParams(entity.getEndTime())) {
                    VALUES("endTime", getValue(entity.getEndTime()));
                }
                if (LockInLifeUtil.vaildParams(entity.getProcessId())) {
                    VALUES("processId", getValue(entity.getProcessId()));
                }
                if (LockInLifeUtil.vaildParams(entity.getApprvRs())) {
                    VALUES("apprvRs", getValue(entity.getApprvRs()));
                }
                if (LockInLifeUtil.vaildParams(entity.getOperator())) {
                    VALUES("operator", getValue(entity.getOperator()));
                }
                if (LockInLifeUtil.vaildParams(entity.getArea())) {
                    VALUES("area", getValue(entity.getArea()));
                }
                if (LockInLifeUtil.vaildParams(entity.getApplcNum())) {
                    VALUES("applcNum", getValue(entity.getApplcNum()));
                }
                if (LockInLifeUtil.vaildParams(entity.getApprvReason())){
                    VALUES("apprvReason", getValue(entity.getApprvReason()));
                }
                if (LockInLifeUtil.vaildParams(entity.getSrcId())) {
                    VALUES("srcId", getValue(entity.getSrcId()));
                }
                if (LockInLifeUtil.vaildParams(entity.getCurId())) {
                    VALUES("curId", getValue(entity.getCurId()));
                }
            }}.toString();
            logger.info(sql);
            return sql;
        } else {
            logger.info("the param is not the type of ActApproveHis");
            return null;
        }
    }

    public String updateEntity(Map<String, Object> para) {
        if (para.size() <= 0){
            logger.info("param is null");
            return null;
        }
        if (para.get("param") instanceof ActApproveHis) {
            ActApproveHis entity = (ActApproveHis) para.get("param");
            String sql = new SQL(){{
                if (LockInLifeUtil.vaildParams(entity.getId())) {
                    UPDATE("act_approve_his");
                    if (LockInLifeUtil.vaildParams(entity.getEndTime())) {
                        SET("endTime = "+ getValue(entity.getEndTime()));
                    }
                    if (LockInLifeUtil.vaildParams(entity.getApprvRs())) {
                        SET("apprvRs = " + getValue(entity.getApprvRs()));
                    }
                    if (LockInLifeUtil.vaildParams(entity.getApprvReason())){
                        SET("apprvReason = " + getValue(entity.getApprvReason()));
                    }
                    WHERE("id=" + getValue(entity.getId()));
                } else {
                    logger.info("the key is null");
                }
            }}.toString();
            logger.info(sql);
            return sql;
        } else {
            logger.info("the param is not ActApproveHis");
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
