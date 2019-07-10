package cn.lockinlife.activiti.core.entity;

import java.util.Date;

public class ActApproveRs {

    private Integer id;

    private String srcDep;

    private String curDep;

    private Date creatTime;

    private Date endTime;

    private String processId;

    private String apprvRs;

    private String area;

    private String applcNum;

    private String apprvReason;

    public String getApprvReason() {
        return apprvReason;
    }

    public void setApprvReason(String apprvReason) {
        this.apprvReason = apprvReason;
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

    public String getCurDep() {
        return curDep;
    }

    public void setCurDep(String curDep) {
        this.curDep = curDep;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getApprvRs() {
        return apprvRs;
    }

    public void setApprvRs(String apprvRs) {
        this.apprvRs = apprvRs;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getApplcNum() {
        return applcNum;
    }

    public void setApplcNum(String applcNum) {
        this.applcNum = applcNum;
    }
}
