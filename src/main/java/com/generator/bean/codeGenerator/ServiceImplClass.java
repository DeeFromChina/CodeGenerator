package com.generator.bean.codeGenerator;

import java.util.Map;

public class ServiceImplClass {

    /**
     * serviceImpl名称
     */
    private String serviceImplName;

    /**
     *
     */
    private EntityClass entityClass;

    /**
     *
     */
    private MapperClass mapperClass;

    /**
     *
     */
    private ServiceClass serviceClass;

    /**
     * serviceimpl路径
     */
    private String serviceImplPath;

    /**
     * serviceimpl文件路径
     */
    private String serviceImplFilePath;

    private Map<String, String> serviceImplMethods;

    public String getServiceImplName() {
        return serviceImplName;
    }

    public void setServiceImplName(String serviceImplName) {
        this.serviceImplName = serviceImplName;
    }

    public EntityClass getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(EntityClass entityClass) {
        this.entityClass = entityClass;
    }

    public MapperClass getMapperClass() {
        return mapperClass;
    }

    public void setMapperClass(MapperClass mapperClass) {
        this.mapperClass = mapperClass;
    }

    public ServiceClass getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(ServiceClass serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getServiceImplPath() {
        return serviceImplPath;
    }

    public void setServiceImplPath(String serviceImplPath) {
        this.serviceImplPath = serviceImplPath;
    }

    public String getServiceImplFilePath() {
        return serviceImplFilePath;
    }

    public void setServiceImplFilePath(String serviceImplFilePath) {
        this.serviceImplFilePath = serviceImplFilePath;
    }

    public Map<String, String> getServiceImplMethods() {
        return serviceImplMethods;
    }

    public void setServiceImplMethods(Map<String, String> serviceImplMethods) {
        this.serviceImplMethods = serviceImplMethods;
    }
}
