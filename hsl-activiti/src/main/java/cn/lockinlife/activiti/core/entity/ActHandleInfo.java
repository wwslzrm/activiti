package cn.lockinlife.activiti.core.entity;

import java.util.Date;

public class ActHandleInfo {

    private Integer id;
    private String srcDep;
    private Integer srcId;
    private Date createTime;
    private Date endTime;
    private String operator;
    private String applcNum;
    private String reson;
    private String apprvRs;

    public Integer getSrcId() {
        return srcId;
    }

    public void setSrcId(Integer srcId) {
        this.srcId = srcId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSrcDep() {
        return srcDep;
    }

    public void setSrcDep(String srcDep) {
        this.srcDep = srcDep;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getApplcNum() {
        return applcNum;
    }

    public void setApplcNum(String applcNum) {
        this.applcNum = applcNum;
    }

    public String getReson() {
        return reson;
    }

    public void setReson(String reson) {
        this.reson = reson;
    }

    public String getApprvRs() {
        return apprvRs;
    }

    public void setApprvRs(String apprvRs) {
        this.apprvRs = apprvRs;
    }
}
