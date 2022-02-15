package com.generator.bean.codeGenerator;

public class LambdaWrapperDetail {

    private EntityClassField entityClassField;

    private ProcessClassField processClassField;

    private String lambdaWrapperRule;

    public EntityClassField getEntityClassField() {
        return entityClassField;
    }

    public void setEntityClassField(EntityClassField entityClassField) {
        this.entityClassField = entityClassField;
    }

    public ProcessClassField getProcessClassField() {
        return processClassField;
    }

    public void setProcessClassField(ProcessClassField processClassField) {
        this.processClassField = processClassField;
    }

    public String getLambdaWrapperRule() {
        return lambdaWrapperRule;
    }

    public void setLambdaWrapperRule(String lambdaWrapperRule) {
        this.lambdaWrapperRule = lambdaWrapperRule;
    }
}
