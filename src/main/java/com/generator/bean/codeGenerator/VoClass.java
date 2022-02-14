package com.generator.bean.codeGenerator;

import java.util.List;

public class VoClass {

    private String belongModularName;

    private String belongEntityName;

    private String className;

    private String classImpl;

    private String classPath;

    private String classFilePath;

    private List<VoClassField> voClassFields;

    public String getBelongModularName() {
        return belongModularName;
    }

    public void setBelongModularName(String belongModularName) {
        this.belongModularName = belongModularName;
    }

    public String getBelongEntityName() {
        return belongEntityName;
    }

    public void setBelongEntityName(String belongEntityName) {
        this.belongEntityName = belongEntityName;
    }

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

    public List<VoClassField> getVoClassFields() {
        return voClassFields;
    }

    public void setVoClassFields(List<VoClassField> voClassFields) {
        this.voClassFields = voClassFields;
    }
}
