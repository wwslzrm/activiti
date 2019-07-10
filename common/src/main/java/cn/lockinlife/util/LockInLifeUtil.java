package cn.lockinlife.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 锁生活工具类
 *
 * @author Administrator
 */
public class LockInLifeUtil {

    private static final Logger logger = LoggerFactory.getLogger(LockInLifeUtil.class);



    public static boolean vaildParams(Object ...pname){
        if (pname == null || pname.length == 0) {
            throw new IllegalArgumentException("pname should not be empty");
        }
        for (Object name : pname) {
            if (name instanceof String) {
                if (name == null || (name.toString()).equals("".intern())) {
                    return false;
                }
            } else if (name instanceof List) {
                List tem = (List) name;
                if (tem == null || tem.size() <= 0) {
                    return false;
                }
            } else if (name instanceof Map) {
                Map tem = (Map) name;
                if (tem == null || tem.size() <= 0) {
                    return false;
                }
            } else {
                if (name == null) {
                    return false;
                }
            }
        }
        return true;
    }
}
