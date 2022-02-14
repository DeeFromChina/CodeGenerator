package com.generator.bean.sysInterfaceColumnRule;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SysInterfaceColumnRule {

    private Integer id;

    //接口id
    private Integer sysInterfaceInfoId;

    //页面字段编码
    private String pageColumnCode;

    //页面字段名称
    private String pageColumnName;

    //字段来源
    private String columnSource;

    //页面字段类型
    private String pageColumnType;

    //父级节点（用于子表）
    private String fatherNodeCode;

    //最大长度
    private Integer maxLength;

    //是否必填
    private Integer isRequired;

    //校验规则
    private String validateRule;

    //加工规则
    private String processingRule;

    //页面字段加工后别名
    private String columnAliasCode;

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

    public String getPageColumnCode() {
        return pageColumnCode;
    }

    public void setPageColumnCode(String pageColumnCode) {
        this.pageColumnCode = pageColumnCode;
    }

    public String getPageColumnName() {
        return pageColumnName;
    }

    public void setPageColumnName(String pageColumnName) {
        this.pageColumnName = pageColumnName;
    }

    public String getColumnSource() {
        return columnSource;
    }

    public void setColumnSource(String columnSource) {
        this.columnSource = columnSource;
    }

    public String getPageColumnType() {
        return pageColumnType;
    }

    public void setPageColumnType(String pageColumnType) {
        this.pageColumnType = pageColumnType;
    }

    public String getFatherNodeCode() {
        return fatherNodeCode;
    }

    public void setFatherNodeCode(String fatherNodeCode) {
        this.fatherNodeCode = fatherNodeCode;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Integer getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Integer isRequired) {
        this.isRequired = isRequired;
    }

    public String getValidateRule() {
        return validateRule;
    }

    public void setValidateRule(String validateRule) {
        this.validateRule = validateRule;
    }

    public String getProcessingRule() {
        return processingRule;
    }

    public void setProcessingRule(String processingRule) {
        this.processingRule = processingRule;
    }

    public String getColumnAliasCode() {
        return columnAliasCode;
    }

    public void setColumnAliasCode(String columnAliasCode) {
        this.columnAliasCode = columnAliasCode;
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
