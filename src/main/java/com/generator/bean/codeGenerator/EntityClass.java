package com.generator.bean.codeGenerator;

import java.util.List;

public class EntityClass {

    /**
     * 实体类
     */
    private String className;

    /**
     * 实体名
     */
    private String classImpl;

    /**
     * 实体路径
     */
    private String classPath;

    /**
     * 实体文件路径
     */
    private String classFilePath;

    /**
     * 实体所属模块
     */
    private String modularCode;

    /**
     * 对应的数据库表
     */
    private String belongTable;

    /**
     * 实体的字段
     */
    private List<EntityClassField> entityClassFields;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassImpl() {
        return classImpl;
    }

    public void setClassImpl(String classImpl) {
        this.classImpl = classImpl;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getClassFilePath() {
        return classFilePath;
    }

    public void setClassFilePath(String classFilePath) {
        this.classFilePath = classFilePath;
    }

    public String getModularCode() {
        return modularCode;
    }

    public void setModularCode(String modularCode) {
        this.modularCode = modularCode;
    }

    public String getBelongTable() {
        return belongTable;
    }

    public void setBelongTable(String belongTable) {
        this.belongTable = belongTable;
    }

    public List<EntityClassField> getEntityClassFields() {
        return entityClassFields;
    }

    public void setEntityClassFields(List<EntityClassField> entityClassFields) {
        this.entityClassFields = entityClassFields;
    }
}
