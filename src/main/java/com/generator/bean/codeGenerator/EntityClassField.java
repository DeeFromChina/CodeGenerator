package com.generator.bean.codeGenerator;

public class EntityClassField {

    /**
     * 字段类型
     */
    private String propertyType;

    /**
     * 字段编码
     */
    private String propertyName;

    /**
     * 是否必填
     */
    private Integer isRequired;

    /**
     * 最大长度
     */
    private Integer maxLength;

    /**
     * 对应sql字段
     */
    private String name;

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

    public Integer getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Integer isRequired) {
        this.isRequired = isRequired;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
