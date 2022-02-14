package com.generator.bean.codeGenerator;

import java.util.Map;

public class ServiceClass {

    /**
     * service名称
     */
    private String serviceName;

    /**
     * service实例对象
     */
    private String serviceImpl;

    /**
     * service路径
     */
    private String servicePath;

    /**
     * service文件路径
     */
    private String serviceFilePath;

    private EntityClass entityClass;

    private Map<String, String> serviceMethods;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceImpl() {
        return serviceImpl;
    }

    public void setServiceImpl(String serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    public String getServicePath() {
        return servicePath;
    }

    public void setServicePath(String servicePath) {
        this.servicePath = servicePath;
    }

    public String getServiceFilePath() {
        return serviceFilePath;
    }

    public void setServiceFilePath(String serviceFilePath) {
        this.serviceFilePath = serviceFilePath;
    }

    public EntityClass getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(EntityClass entityClass) {
        this.entityClass = entityClass;
    }

    public Map<String, String> getServiceMethods() {
        return serviceMethods;
    }

    public void setServiceMethods(Map<String, String> serviceMethods) {
        this.serviceMethods = serviceMethods;
    }
}
