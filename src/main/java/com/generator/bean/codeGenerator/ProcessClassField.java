package com.generator.bean.codeGenerator;

public class ProcessClassField {

    /**
     * 字段类型
     */
    private String propertyType;

    /**
     * 字段编码
     */
    private String propertyName;

    /**
     * 字段名称
     */
    private String comment;

    /**
     * set方法名
     */
    private String setters;

    /**
     * get方法名
     */
    private String getters;

    private String voField;

    private String processingRule;

    private String processRuleClass;

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSetters() {
        return setters;
    }

    public void setSetters(String setters) {
        this.setters = setters;
    }

    public String getGetters() {
        return getters;
    }

    public void setGetters(String getters) {
        this.getters = getters;
    }

    public String getVoField() {
        return voField;
    }

    public void setVoField(String voField) {
        this.voField = voField;
    }

    public String getProcessingRule() {
        return processingRule;
    }

    public void setProcessingRule(String processingRule) {
        this.processingRule = processingRule;
    }

    public String getProcessRuleClass() {
        return processRuleClass;
    }

    public void setProcessRuleClass(String processRuleClass) {
        this.processRuleClass = processRuleClass;
    }
}
