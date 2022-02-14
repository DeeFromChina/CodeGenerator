package com.generator.bean.codeGenerator;

import java.util.List;

public class ControllerMethod {

    /**
     * 接口方法名
     */
    private String apiMethod;

    /**
     * vo类
     */
    private VoClass voClass;

    /**
     * 加工类
     */
    private ProcessClass processClass;

    /**
     * sql操作类
     */
    private List<LambdaWrapper> lambdaWrappers;

    public String getApiMethod() {
        return apiMethod;
    }

    public void setApiMethod(String apiMethod) {
        this.apiMethod = apiMethod;
    }

    public VoClass getVoClass() {
        return voClass;
    }

    public void setVoClass(VoClass voClass) {
        this.voClass = voClass;
    }

    public ProcessClass getProcessClass() {
        return processClass;
    }

    public void setProcessClass(ProcessClass processClass) {
        this.processClass = processClass;
    }

    public List<LambdaWrapper> getLambdaWrappers() {
        return lambdaWrappers;
    }

    public void setLambdaWrappers(List<LambdaWrapper> lambdaWrappers) {
        this.lambdaWrappers = lambdaWrappers;
    }
}
