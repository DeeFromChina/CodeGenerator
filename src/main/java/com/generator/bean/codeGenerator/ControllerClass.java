package com.generator.bean.codeGenerator;

import java.util.Map;

public class ControllerClass {

    /**
     * controller名称
     */
    private String controllerName;

    /**
     * controller路径
     */
    private String controllerPath;

    /**
     * controller文件路径
     */
    private String controllerFilePath;

    /**
     * 接口归类
     */
    private String apiModular;

    /**
     * 主entity
     */
    private EntityClass mainEntityClass;

    /**
     * 注入的service
     */
    private Map<String, ServiceClass> serviceClassMap;

    /**
     * 需要声明的方法
     */
    private Map<String, ControllerMethod> methodMap;

    /**
     * 额外依赖的类
     */
    private Map<String, String> processRuleClassMap;

    /**
     * 引用的entity
     */
    private Map<String, EntityClass> entityClassMap;

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public String getControllerPath() {
        return controllerPath;
    }

    public void setControllerPath(String controllerPath) {
        this.controllerPath = controllerPath;
    }

    public String getControllerFilePath() {
        return controllerFilePath;
    }

    public void setControllerFilePath(String controllerFilePath) {
        this.controllerFilePath = controllerFilePath;
    }

    public String getApiModular() {
        return apiModular;
    }

    public void setApiModular(String apiModular) {
        this.apiModular = apiModular;
    }

    public EntityClass getMainEntityClass() {
        return mainEntityClass;
    }

    public void setMainEntityClass(EntityClass mainEntityClass) {
        this.mainEntityClass = mainEntityClass;
    }

    public Map<String, ServiceClass> getServiceClassMap() {
        return serviceClassMap;
    }

    public void setServiceClassMap(Map<String, ServiceClass> serviceClassMap) {
        this.serviceClassMap = serviceClassMap;
    }

    public Map<String, ControllerMethod> getMethodMap() {
        return methodMap;
    }

    public void setMethodMap(Map<String, ControllerMethod> methodMap) {
        this.methodMap = methodMap;
    }

    public Map<String, String> getProcessRuleClassMap() {
        return processRuleClassMap;
    }

    public void setProcessRuleClassMap(Map<String, String> processRuleClassMap) {
        this.processRuleClassMap = processRuleClassMap;
    }

    public Map<String, EntityClass> getEntityClassMap() {
        return entityClassMap;
    }

    public void setEntityClassMap(Map<String, EntityClass> entityClassMap) {
        this.entityClassMap = entityClassMap;
    }
}
