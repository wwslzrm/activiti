package cn.lockinlife.activiti.core.entity;

/**
 * Created by Yuck on 2019/6/26
 */
public class ActRoleAssign {
    private Integer id;
    private String applcNum;
    private String department;
    private String tel;

    public ActRoleAssign(String applcNum, String department, String tel) {
        this.applcNum = applcNum;
        this.department = department;
        this.tel = tel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplcNum() {
        return applcNum;
    }

    public void setApplcNum(String applcNum) {
        this.applcNum = applcNum;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
