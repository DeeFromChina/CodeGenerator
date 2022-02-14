package com.generator.bean.codeGenerator;

import java.util.Map;

public class ProjectConfig {

    /**
     * 输出项目绝对路径根目录
     */
    private String filePath;

    /**
     * 输出项目根目录
     */
    private String projectPath;

    /**
     * 创建人
     */
    private String author;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 实体类(k:路径)
     */
    private Map<String, EntityClass> entityClassMap;

    /**
     * vo类(k:路径)
     */
    private Map<String, VoClass> voClassMap;

    /**
     * process类(k:路径)
     */
    private Map<String, ProcessClass> processClassMap;

    /**
     * service类(k:路径)
     */
    private Map<String, ServiceClass> serviceClassMap;

    /**
     * serviceImpl类(k:路径)
     */
    private Map<String, ServiceImplClass> serviceImplClassMap;

    /**
     * mapper类(k:路径)
     */
    private Map<String, MapperClass> mapperClassMap;

    /**
     * mybatis类(k:路径)
     */
    private Map<String, MybatisClass> mybatisClassMap;

    /**
     * 实体类(k:路径)
     */
    private Map<String, ControllerClass> controllerClassMap;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Map<String, EntityClass> getEntityClassMap() {
        return entityClassMap;
    }

    public void setEntityClassMap(Map<String, EntityClass> entityClassMap) {
        this.entityClassMap = entityClassMap;
    }

    public Map<String, VoClass> getVoClassMap() {
        return voClassMap;
    }

    public void setVoClassMap(Map<String, VoClass> voClassMap) {
        this.voClassMap = voClassMap;
    }

    public Map<String, ProcessClass> getProcessClassMap() {
        return processClassMap;
    }

    public void setProcessClassMap(Map<String, ProcessClass> processClassMap) {
        this.processClassMap = processClassMap;
    }

    public Map<String, ServiceClass> getServiceClassMap() {
        return serviceClassMap;
    }

    public void setServiceClassMap(Map<String, ServiceClass> serviceClassMap) {
        this.serviceClassMap = serviceClassMap;
    }

    public Map<String, ServiceImplClass> getServiceImplClassMap() {
        return serviceImplClassMap;
    }

    public void setServiceImplClassMap(Map<String, ServiceImplClass> serviceImplClassMap) {
        this.serviceImplClassMap = serviceImplClassMap;
    }

    public Map<String, MapperClass> getMapperClassMap() {
        return mapperClassMap;
    }

    public void setMapperClassMap(Map<String, MapperClass> mapperClassMap) {
        this.mapperClassMap = mapperClassMap;
    }

    public Map<String, MybatisClass> getMybatisClassMap() {
        return mybatisClassMap;
    }

    public void setMybatisClassMap(Map<String, MybatisClass> mybatisClassMap) {
        this.mybatisClassMap = mybatisClassMap;
    }

    public Map<String, ControllerClass> getControllerClassMap() {
        return controllerClassMap;
    }

    public void setControllerClassMap(Map<String, ControllerClass> controllerClassMap) {
        this.controllerClassMap = controllerClassMap;
    }
}
