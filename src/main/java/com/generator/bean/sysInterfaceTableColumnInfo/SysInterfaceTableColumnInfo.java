package com.generator.bean.sysInterfaceTableColumnInfo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SysInterfaceTableColumnInfo {

    private Integer id;

    //接口id
    private Integer sysInterfaceInfoId;

    //sql批次id
    private Integer sqlBatchId;

    //sql判断前置条件
    private String conditionValue;

    //表编码
    private String tableCode;

    //表字段编码
    private String tableColumnCode;

    //引用字段别名编码
    private String paramCode;

    //父级节点（用于子表）
    private String fatherNodeCode;

    //sql类型（insert、update、delete）
    private String sqlType;

    //sql条件
    private String sqlCondition;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastUpdateTime;

    private String lastUpdateUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSysInterfaceInfoId() {
        return sysInterfaceInfoId;
    }

    public void setSysInterfaceInfoId(Integer sysInterfaceInfoId) {
        this.sysInterfaceInfoId = sysInterfaceInfoId;
    }

    public Integer getSqlBatchId() {
        return sqlBatchId;
    }

    public void setSqlBatchId(Integer sqlBatchId) {
        this.sqlBatchId = sqlBatchId;
    }

    public String getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getTableColumnCode() {
        return tableColumnCode;
    }

    public void setTableColumnCode(String tableColumnCode) {
        this.tableColumnCode = tableColumnCode;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getFatherNodeCode() {
        return fatherNodeCode;
    }

    public void setFatherNodeCode(String fatherNodeCode) {
        this.fatherNodeCode = fatherNodeCode;
    }

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getSqlCondition() {
        return sqlCondition;
    }

    public void setSqlCondition(String sqlCondition) {
        this.sqlCondition = sqlCondition;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }
}
