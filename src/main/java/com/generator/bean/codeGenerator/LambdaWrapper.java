package com.generator.bean.codeGenerator;

import java.util.List;

public class LambdaWrapper {

    private String type;

    private EntityClass entityClass;

    private String condition;

    private List<LambdaWrapperDetail> lambdaWrapperDetails;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public EntityClass getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(EntityClass entityClass) {
        this.entityClass = entityClass;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public List<LambdaWrapperDetail> getLambdaWrapperDetails() {
        return lambdaWrapperDetails;
    }

    public void setLambdaWrapperDetails(List<LambdaWrapperDetail> lambdaWrapperDetails) {
        this.lambdaWrapperDetails = lambdaWrapperDetails;
    }
}
