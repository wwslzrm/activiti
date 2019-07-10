package cn.lockinlife.activiti.core.mapper;

import cn.lockinlife.activiti.core.entity.ActApproveRs;
import cn.lockinlife.util.LockInLifeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.jdbc.SQL;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ActApproveRsSQL {

    private static final Logger logger = LoggerFactory.getLogger(ActApproveRsSQL.class);

    public String insertEntity(Map<String, Object> para) {
        Object obj = para.get("param");
        if (!LockInLifeUtil.vaildParams(obj)){
            logger.info("param is null");
            return null;
        }
        if (obj instanceof ActApproveRs) {
            ActApproveRs entity = (ActApproveRs) obj;
            String sql = new SQL(){{
                INSERT_INTO("act_approve_rs");
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
                if (LockInLifeUtil.vaildParams(entity.getArea())) {
                    VALUES("area", getValue(entity.getArea()));
                }
                if (LockInLifeUtil.vaildParams(entity.getApplcNum())) {
                    VALUES("applcNum", getValue(entity.getApplcNum()));
                }
            }}.toString();
            logger.info(sql);
            return sql;
        } else {
            logger.info("the param is not the type of ActApproveRs");
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
