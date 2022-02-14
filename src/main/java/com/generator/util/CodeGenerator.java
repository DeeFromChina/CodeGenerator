package com.generator.util;

import com.dee.frame.springbootframe.util.common.BaseUtil;
import com.dee.frame.springbootframe.util.fileUtils.FileUtils;
import com.generator.bean.codeGenerator.*;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGenerator{

    private static final String voTempFilePath = "/myTemp/vo.java.vm";
    private static final String processTempFilePath = "/myTemp/processed.java.vm";
    private static final String entityTempFilePath = "/myTemp/entity.java.vm";
    private static final String controllerTempFilePath = "/myTemp/controller.java.vm";
    private static final String serviceTempFilePath = "/myTemp/service.java.vm";
    private static final String serviceImplTempFilePath = "/myTemp/serviceImpl.java.vm";
    private static final String mapperTempFilePath = "/myTemp/mapper.java.vm";
    private static final String commonProcessTempFilePath = "/myTemp/process/commonProcess.java.vm";

    public CodeGenerator createVo(ProjectConfig projectConfig) throws Exception {
        Map<String, VoClass> voClassMap = projectConfig.getVoClassMap();
        for(Map.Entry<String, VoClass> entry : voClassMap.entrySet()){
            VoClass voClass = entry.getValue();
            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            ve.init();
            Template t = ve.getTemplate(voTempFilePath);
            Map<String, Object> paramMap = BaseUtil.objectToMap(projectConfig);
            paramMap.put("voClass", voClass);
            paramMap.put("author", projectConfig.getAuthor());
            paramMap.put("createTime", projectConfig.getCreateTime());

            Map<String, Object> paramListMap = new HashMap<String, Object>();
            //填充模板内容
            String str = setTempContent(t, paramMap, paramListMap);
            String filePath = voClass.getClassFilePath() + File.separator + voClass.getClassName() + ".java";
            if(FileUtils.createNewFile(filePath)) {
                FileUtils.writeFile(filePath, str);
            }
        }
        return this;
    }

    public CodeGenerator createProcess(ProjectConfig projectConfig) throws Exception {
        Map<String, ProcessClass> processClassMap = projectConfig.getProcessClassMap();
        for(Map.Entry<String, ProcessClass> entry : processClassMap.entrySet()){
            ProcessClass processClass = entry.getValue();
            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            ve.init();
            Template t = ve.getTemplate(processTempFilePath);
            Map<String, Object> paramMap = BaseUtil.objectToMap(projectConfig);
            paramMap.put("processClass", processClass);
            paramMap.put("author", projectConfig.getAuthor());
            paramMap.put("createTime", projectConfig.getCreateTime());
            Map<String, Object> paramListMap = new HashMap<String, Object>();
            //填充模板内容
            String str = setTempContent(t, paramMap, paramListMap);
            String filePath = processClass.getClassFilePath() + File.separator + processClass.getClassName() + ".java";
            if(FileUtils.createNewFile(filePath)) {
                FileUtils.writeFile(filePath, str);
            }
        }
        return this;
    }

    /**
     *
     * @param paramMap
     * @param fields
     * @param outputFilePath
     * @return
     * @throws Exception
     */
    public CodeGenerator createEntity(Map<String, Object> paramMap, List fields, String outputFilePath) throws Exception {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        Template t = ve.getTemplate(entityTempFilePath);
        Map<String, Object> paramListMap = new HashMap<String, Object>();
        paramListMap.put("fields", fields);
        //填充模板内容
        String str = setTempContent(t, paramMap, paramListMap);
        String moduleName = BaseUtil.returnString(paramMap.get("moduleName"));
        String entity = BaseUtil.returnString(paramMap.get("entity"));
        String filePath = outputFilePath + File.separator + "entity" + File.separator  + moduleName
                + File.separator + entity + ".java";
        if(FileUtils.createNewFile(filePath)) {
            FileUtils.writeFile(filePath, str);
        }
        return this;
    }

    /**
     * 创建多个Entity
     * @param projectConfig
     * @return
     * @throws Exception
     */
    public CodeGenerator createEntitys(ProjectConfig projectConfig) throws Exception {
        Map<String, EntityClass> entityClassMap = projectConfig.getEntityClassMap();
        for(Map.Entry<String, EntityClass> entry : entityClassMap.entrySet()){
            EntityClass entity = entry.getValue();
            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            ve.init();
            Template t = ve.getTemplate(entityTempFilePath);
            Map<String, Object> paramListMap = new HashMap<String, Object>();
            Map<String, Object> paramMap = BaseUtil.objectToMap(projectConfig);
            paramMap.put("entityClass", entity);
            paramMap.put("author", projectConfig.getAuthor());
            paramMap.put("createTime", projectConfig.getCreateTime());
            //填充模板内容
            String str = setTempContent(t, paramMap, paramListMap);
            String filePath = entity.getClassFilePath() + File.separator + entity.getClassName() + ".java";
            if(FileUtils.createNewFile(filePath)) {
                FileUtils.writeFile(filePath, str);
            }
        };
        return this;
    }

    /**
     * 创建controller
     * @param projectConfig
     * @return
     * @throws Exception
     */
    public CodeGenerator createController(ProjectConfig projectConfig) throws Exception {
        Map<String, ControllerClass> controllerClassMap = projectConfig.getControllerClassMap();
        for(Map.Entry<String, ControllerClass> entry : controllerClassMap.entrySet()){
            ControllerClass controllerClass = entry.getValue();
            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            ve.init();
            Template t = ve.getTemplate(controllerTempFilePath);
            Map<String, Object> paramMap = BaseUtil.objectToMap(projectConfig);
            paramMap.put("controllerClass", controllerClass);
            paramMap.put("author", projectConfig.getAuthor());
            paramMap.put("createTime", projectConfig.getCreateTime());
            paramMap.put("projectPath", projectConfig.getProjectPath());
            Map<String, Object> paramListMap = new HashMap<String, Object>();
            //填充模板内容
            String str = setTempContent(t, paramMap, paramListMap);
            String filePath = controllerClass.getControllerFilePath() + File.separator + controllerClass.getControllerName() + ".java";
            if(FileUtils.createNewFile(filePath)) {
                FileUtils.writeFile(filePath, str);
            }
        }
        return this;
    }

    public CodeGenerator createService(ProjectConfig projectConfig) throws Exception {
        Map<String, ServiceClass> serviceClassMap = projectConfig.getServiceClassMap();
        for(Map.Entry<String, ServiceClass> entry : serviceClassMap.entrySet()){
            ServiceClass serviceClass = entry.getValue();
            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            ve.init();
            Template t = ve.getTemplate(serviceTempFilePath);
            Map<String, Object> paramMap = BaseUtil.objectToMap(projectConfig);
            paramMap.put("service", serviceClass);
            paramMap.put("author", projectConfig.getAuthor());
            paramMap.put("createTime", projectConfig.getCreateTime());
            Map<String, Object> paramListMap = new HashMap<String, Object>();
            //填充模板内容
            String str = setTempContent(t, paramMap, paramListMap);
            String filePath = serviceClass.getServiceFilePath() + File.separator + serviceClass.getServiceName() + ".java";
            if(FileUtils.createNewFile(filePath)) {
                FileUtils.writeFile(filePath, str);
            }
        }
        return this;
    }

    public CodeGenerator createServiceImpl(ProjectConfig projectConfig) throws Exception {
        Map<String, ServiceImplClass> serviceImplClassMap = projectConfig.getServiceImplClassMap();
        for(Map.Entry<String, ServiceImplClass> entry : serviceImplClassMap.entrySet()){
            ServiceImplClass serviceImplClass = entry.getValue();
            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            ve.init();
            Template t = ve.getTemplate(serviceImplTempFilePath);
            Map<String, Object> paramMap = BaseUtil.objectToMap(projectConfig);
            paramMap.put("serviceimpl", serviceImplClass);
            paramMap.put("author", projectConfig.getAuthor());
            paramMap.put("createTime", projectConfig.getCreateTime());
            Map<String, Object> paramListMap = new HashMap<String, Object>();
            //填充模板内容
            String str = setTempContent(t, paramMap, paramListMap);
            String filePath = serviceImplClass.getServiceImplFilePath() + File.separator + serviceImplClass.getServiceImplName() + ".java";
            if(FileUtils.createNewFile(filePath)) {
                FileUtils.writeFile(filePath, str);
            }
        }
        return this;
    }

    public CodeGenerator createMapper(ProjectConfig projectConfig) throws Exception {
        Map<String, MapperClass> mapperClassMap = projectConfig.getMapperClassMap();
        for(Map.Entry<String, MapperClass> entry : mapperClassMap.entrySet()){
            MapperClass mapperClass = entry.getValue();
            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            ve.init();
            Template t = ve.getTemplate(mapperTempFilePath);
            Map<String, Object> paramMap = BaseUtil.objectToMap(projectConfig);
            paramMap.put("mapperClass", mapperClass);
            paramMap.put("author", projectConfig.getAuthor());
            paramMap.put("createTime", projectConfig.getCreateTime());
            Map<String, Object> paramListMap = new HashMap<String, Object>();
            //填充模板内容
            String str = setTempContent(t, paramMap, paramListMap);
            String filePath = mapperClass.getClassFilePath() + File.separator + mapperClass.getClassName() + ".java";
            if(FileUtils.createNewFile(filePath)) {
                FileUtils.writeFile(filePath, str);
            }
        }
        return this;
    }

    public CodeGenerator createCommonProcess(ProjectConfig projectConfig) throws Exception{
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        Template t = ve.getTemplate(commonProcessTempFilePath);

        String packagePath = "com.dee.frame.springbootframe.processRule";
        List<Map<String, Object>> classList = PackageUtil.scanClass(packagePath);

        Map<String, Object> paramListMap = new HashMap<String, Object>();
//        paramListMap.put("beans", );
        //填充模板内容
        String str = setTempContent(t, projectConfig, paramListMap);
        String filePath = projectConfig.getFilePath() + File.separator + "processRule/CommonProcess.java";
        if(FileUtils.createNewFile(filePath)) {
            FileUtils.writeFile(filePath, str);
        }
        return this;
    }

    private static void copyFiles(List<Map<String, Object>> classList) {
        try {
            for(Map<String, Object> classMap : classList){
                Class c = Class.forName(classMap.get("classPath").toString());
                String filePath = c.getResource("/").getPath();
                File file = new File(filePath);
                FileUtils.fileUpload("", file);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static String setTempContent(Template t, Map<String, Object> paramMap, Map<String, Object> paramListMap) {
        VelocityContext ctx = new VelocityContext();
        paramMap.forEach((k,v) -> {
            ctx.put(k, v);
        });

        // list集合
        paramListMap.forEach((k,v) -> {
            ctx.put(k, v);
        });

        StringWriter sw = new StringWriter();
        t.merge(ctx, sw);

        System.out.println(sw.toString());
        return sw.toString();
    }

    private static String setTempContent(Template t, ProjectConfig projectConfig, Map<String, Object> paramListMap) throws Exception {
        Map<String, Object> paramMap = BaseUtil.objectToMap(projectConfig);

        VelocityContext ctx = new VelocityContext();
        paramMap.forEach((k,v) -> {
            ctx.put(k, v);
        });

        // list集合
        paramListMap.forEach((k,v) -> {
            ctx.put(k, v);
        });

        StringWriter sw = new StringWriter();
        t.merge(ctx, sw);

        System.out.println(sw.toString());
        return sw.toString();
    }

}
