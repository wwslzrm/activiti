package cn.lockinlife.entity;

import cn.lockinlife.util.ErrCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * author: 胡绍利
 * funciton: 公共返回数据格式
 */
@ApiModel(value = "LockData对象", description = "LockData锁生活统一传输数据类型")
public class LockData implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(LockData.class);

    @ApiModelProperty(value="成功标志", name = "success", dataType = "boolean")
    private boolean success = true;

    @ApiModelProperty(value = "错误描述", name = "msg")
    private String msg;

    @ApiModelProperty(value = "传输数据", name = "obj")
    private Object obj;


    public LockData(){

    }

    public LockData(boolean success, String msg, Object obj){
        this.success = success;
        this.msg = msg;
        this.obj = obj;
        logger.info("response[success[{}]msg[{}]obj[{}]]", success, msg, obj);

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg, String... vars) {
        for(String var : vars) {
            msg = msg.replace("{}", var);
        }
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }




    public static LockData successResponse(){
        return new LockData(true, ErrCode.success, "");
    }
    /*@Override
    public String toString() {
        *//*return "RequestObject [errorCode=" + errorCode + ", msg=" + msg + ", data=" + data == null ? "" :data.toString() + "]";*//*
    }*/


}
