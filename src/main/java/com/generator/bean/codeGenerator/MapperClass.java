package com.generator.bean.codeGenerator;

public class MapperClass {

    private String className;

    private String classImpl;

    private String classPath;

    private String classFilePath;

    private EntityClass entityClass;

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

    public EntityClass getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(EntityClass entityClass) {
        this.entityClass = entityClass;
    }
}
