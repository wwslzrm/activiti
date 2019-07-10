package cn.lockinlife.activiti.core.mapper;

import cn.lockinlife.activiti.core.entity.ActRoleAssign;
import cn.lockinlife.util.LockInLifeUtil;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Yuck on 2019/6/26
 */
public class ActRoleAssignSQL {

    private static final Logger logger = LoggerFactory.getLogger(ActRoleAssignSQL.class);

    public String insertEntity(Map<String, Object> para) {
        if (!LockInLifeUtil.vaildParams(para.get("param"))) {
            logger.info("param is null");
            return null;
        }
        if (para.get("param") instanceof ActRoleAssign) {
            ActRoleAssign entity = (ActRoleAssign) para.get("param");
            String sql = new SQL() {{
                INSERT_INTO("act_role_assign");
                if (LockInLifeUtil.vaildParams(entity.getApplcNum())) {
                    VALUES("applcNum", getValue(entity.getApplcNum()));
                }
                if (LockInLifeUtil.vaildParams(entity.getDepartment())) {
                    VALUES("department", getValue(entity.getDepartment()));
                }
                if (LockInLifeUtil.vaildParams(entity.getTel())) {
                    VALUES("tel", getValue(entity.getTel()));
                }
            }}.toString();
            logger.info(sql);
            return sql;
        } else {
            logger.info("the param is not the type of ActRoleAssign");
            return null;
        }
    }

    private String getValue(Object obj) {
        String result = obj.toString();
        if (obj instanceof Date) {
            result = getTime((Date) obj);
        }
        if (obj instanceof Integer) {
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
