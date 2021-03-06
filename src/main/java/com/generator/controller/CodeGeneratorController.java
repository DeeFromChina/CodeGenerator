package com.generator.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dee.frame.springbootframe.controller.BaseController;
import com.dee.frame.springbootframe.util.RegularUtil;
import com.dee.frame.springbootframe.util.UnderlineToCamelUtils;
import com.dee.frame.springbootframe.util.common.BaseUtil;
import com.generator.bean.codeGenerator.*;
import com.generator.bean.sysInterfaceColumnRule.SysInterfaceColumnRule;
import com.generator.bean.sysInterfaceInfo.SysInterfaceInfo;
import com.generator.bean.sysInterfaceTableColumnInfo.SysInterfaceTableColumnInfo;
import com.generator.bean.sysInterfaceTableInfo.SysInterfaceTableInfo;
import com.generator.bean.sysModularTable.SysModularTable;
import com.generator.service.codeGenerator.CodeGeneratorService;
import com.generator.service.dataProcess.CodeDataProcessService;
import com.generator.service.databaseTable.DatabaseTableColumnService;
import com.generator.service.sysInterfaceColumnRule.SysInterfaceColumnRuleService;
import com.generator.service.sysInterfaceInfo.SysInterfaceInfoService;
import com.generator.service.sysInterfaceTableColumnInfo.SysInterfaceTableColumnInfoService;
import com.generator.service.sysInterfaceTableInfo.SysInterfaceTableInfoService;
import com.generator.service.sysModularTable.SysModularTableService;
import com.generator.util.CodeGenerator;
import com.generator.util.FieldTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/codeGenerator")
public class CodeGeneratorController extends BaseController {

    @Autowired
    private SysModularTableService sysModularTableService;

    @Autowired
    private SysInterfaceInfoService sysInterfaceInfoService;

    @Autowired
    private SysInterfaceColumnRuleService sysInterfaceColumnRuleService;

    @Autowired
    private SysInterfaceTableInfoService sysInterfaceTableInfoService;

    @Autowired
    private SysInterfaceTableColumnInfoService sysInterfaceTableColumnInfoService;

    @Autowired
    private CodeGeneratorService codeGeneratorService;

    @Autowired
    private CodeDataProcessService codeDataProcessService;

    @Autowired
    private DatabaseTableColumnService databaseTableColumnService;

    private Map<String, SysModularTable> sysModularTableMap = new HashMap<String, SysModularTable>();

    private String filePath = "D:\\sunline\\auto_workspace\\auto-project\\src\\main\\java\\com\\project";

    @PostMapping("/initInterfaceTemplateToJava")
    public Map<String, Object> initInterfaceTemplateToJava(HttpServletRequest request, @RequestBody String data) {
        try{
            setMap(request, data);

            //?????????????????????????????????
            initSysModularTableMapMain();

            //????????????????????????
            ProjectConfig projectConfig = initProjectConfigMain();

            //???????????????????????????
            setProjectConfigMain(projectConfig);

            CodeGenerator codeGenerator = new CodeGenerator();
            codeGenerator
//                    .createCommonProcess(projectConfig)
//                    .createVo(projectConfig)
//                    .createProcess(projectConfig)
//                    .createEntitys(projectConfig)
//                    .createService(projectConfig)
//                    .createServiceImpl(projectConfig)
//                    .createMapper(projectConfig)
                    .createController(projectConfig)
            ;

            return toJson(SUCCESS);
        }catch(Exception e){
            e.printStackTrace();
            return toJson(ERROR);
        }
    }

    /**
     * ?????????????????????????????????
     * @throws Exception
     */
    private void initSysModularTableMapMain() throws Exception {
        List<SysModularTable> sysModularTables = sysModularTableService.list();
        for(SysModularTable sysModularTable : sysModularTables){
            String tableCode = sysModularTable.getTableCode();
            sysModularTableMap.put(tableCode, sysModularTable);
        }
    }

    /**
     * ????????????????????????
     * @return
     * @throws Exception
     */
    private ProjectConfig initProjectConfigMain() throws Exception {
        ProjectConfig projectConfig = new ProjectConfig();
        projectConfig.setFilePath(filePath);
        projectConfig.setProjectPath("com.project");
        projectConfig.setAuthor("admin");
        projectConfig.setCreateTime(sdf.format(new Date()));
        projectConfig.setEntityClassMap(new HashMap<>());
        projectConfig.setVoClassMap(new HashMap<>());
        projectConfig.setProcessClassMap(new HashMap<>());
        projectConfig.setServiceClassMap(new HashMap<>());
        projectConfig.setServiceImplClassMap(new HashMap<>());
        projectConfig.setMapperClassMap(new HashMap<>());
        projectConfig.setMybatisClassMap(new HashMap<>());
        projectConfig.setControllerClassMap(new HashMap<>());
        return projectConfig;
    }

    /**
     * ???????????????????????????
     * @param projectConfig
     * @throws Exception
     */
    private void setProjectConfigMain(ProjectConfig projectConfig) throws Exception {
        QueryWrapper<SysInterfaceInfo> queryWrapper = new QueryWrapper<SysInterfaceInfo>();
        List<SysInterfaceInfo> sysInterfaceInfoList = sysInterfaceInfoService.list();
        for(SysInterfaceInfo sysInterfaceInfo : sysInterfaceInfoList){
            QueryWrapper<SysInterfaceTableInfo> sysInterfaceInfoQueryWrapper = new QueryWrapper<SysInterfaceTableInfo>()
                    .eq("sys_interface_info_id", sysInterfaceInfo.getId());
            List<SysInterfaceTableInfo> sysInterfaceTableInfoList = sysInterfaceTableInfoService.list(sysInterfaceInfoQueryWrapper);
            Map<String, ServiceClass> serviceClassMap = new HashMap<>();
            for(SysInterfaceTableInfo sysInterfaceTableInfo : sysInterfaceTableInfoList){
                String tableCode = sysInterfaceTableInfo.getTableCode();

                //?????????entity??????
                initEntityRelated(projectConfig, sysInterfaceInfo, tableCode);
            }

            //??????controller????????????
            setController(projectConfig, sysInterfaceInfo);
        }
    }

    /**
     * ??????entity??????
     * @param entityFieldMap
     * @return
     * @throws Exception
     */
    private EntityClassField initEntityClassField(Map<String, Object> entityFieldMap) throws Exception {
        String tableColumnCode = BaseUtil.returnString(entityFieldMap.get("columnName"));
        String tableColumnType = FieldTypeMapper.transformType(BaseUtil.returnString(entityFieldMap.get("dataType")));
        String propertyName = UnderlineToCamelUtils.underlineToCamel(tableColumnCode, true);
        String tableColumnName = BaseUtil.returnString(entityFieldMap.get("columnDesc"));
        Integer isRequired = BaseUtil.returnString(entityFieldMap.get("isNull")).equals("NO") ? 1 : null;
        Integer maxLength = BaseUtil.returnInt(entityFieldMap.get("columnLength"));

        EntityClassField entityClassField = new EntityClassField();
        entityClassField.setName(tableColumnCode);
        entityClassField.setPropertyType(tableColumnType);
        entityClassField.setPropertyName(propertyName);
        entityClassField.setComment(tableColumnName);
        entityClassField.setIsRequired(isRequired);
        entityClassField.setMaxLength(maxLength);
        entityClassField.setSetters("set"+BaseUtil.toUpperCaseFirstOne(propertyName));
        entityClassField.setGetters("get"+BaseUtil.toUpperCaseFirstOne(propertyName));
        return entityClassField;
    }

    /**
     * entity??????
     * @throws Exception
     */
    private void initEntityRelated(ProjectConfig projectConfig, SysInterfaceInfo sysInterfaceInfo, String tableCode) throws Exception {
        String entityClassImpl = UnderlineToCamelUtils.underlineToCamel(tableCode, true);
        String entityClassName = BaseUtil.toUpperCaseFirstOne(entityClassImpl);

        Map<String, EntityClass> entityClassMap = projectConfig.getEntityClassMap();
        if(entityClassMap.get(entityClassName) != null){
            return;
        }
        //??????entity
        EntityClass entityClass = initEntity(projectConfig, tableCode);
        entityClassMap.put(entityClassName, entityClass);

        Map<String, MapperClass> mapperClassMap = projectConfig.getMapperClassMap();
        //??????mapper
        MapperClass mapperClass = new MapperClass();
        mapperClass.setClassName(entityClassName+"Mapper");
        mapperClass.setClassImpl(BaseUtil.toLowerCaseFirstOne(entityClassName)+"Mapper");
        mapperClass.setClassPath(projectConfig.getProjectPath() + ".mapper." + sysModularTableMap.get(tableCode).getModularCode());
        mapperClass.setClassFilePath(projectConfig.getFilePath() + File.separator + "mapper" + File.separator + sysModularTableMap.get(tableCode).getModularCode());
        mapperClass.setEntityClass(entityClass);
        mapperClassMap.put(mapperClass.getClassName(), mapperClass);

        Map<String, ServiceClass> serviceClassMap = projectConfig.getServiceClassMap();
        //??????service
        ServiceClass serviceClass = initService(projectConfig, tableCode, entityClass);
        serviceClassMap.put(entityClassName+"Service", serviceClass);

        Map<String, ServiceImplClass> serviceImplClassMap = projectConfig.getServiceImplClassMap();
        //??????serviceimpl
        ServiceImplClass serviceImplClass = initServiceImpl(projectConfig, tableCode, entityClass, mapperClass, serviceClass);
        serviceImplClassMap.put(entityClassName+"ServiceImpl", serviceImplClass);

        //??????mybatis
        MybatisClass mybatisClass = new MybatisClass();

        //??????controller
        initController(projectConfig, entityClass, serviceClass, tableCode);

        //????????????controller
        initMainController(projectConfig, sysInterfaceInfo, entityClass, serviceClass);
    }

    /**
     * ??????entity
     * @param projectConfig
     * @param tableCode
     * @throws Exception
     */
    private EntityClass initEntity(ProjectConfig projectConfig, String tableCode) throws Exception {
        //??????entity
        String entityClassImpl = UnderlineToCamelUtils.underlineToCamel(tableCode, true);
        String entityClassName = BaseUtil.toUpperCaseFirstOne(entityClassImpl);
        String entityClassPath = projectConfig.getProjectPath() + ".entity." + sysModularTableMap.get(tableCode).getModularCode();
        String entityClassFilePath = projectConfig.getFilePath() + File.separator + "entity" + File.separator + sysModularTableMap.get(tableCode).getModularCode();
        String modularCode = BaseUtil.returnString(sysModularTableMap.get(tableCode).getModularCode());

        EntityClass entityClass = new EntityClass();
        entityClass.setClassName(entityClassName);
        entityClass.setClassImpl(entityClassImpl);
        entityClass.setClassPath(entityClassPath);
        entityClass.setClassFilePath(entityClassFilePath);
        entityClass.setModularCode(modularCode);
        entityClass.setBelongTable(tableCode);
        //??????entity??????
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("tableName", tableCode);
        List<Map<String, Object>> entityFieldList = databaseTableColumnService.queryDatabaseTableColumnListMap(paramMap);
        List<EntityClassField> entityClassFields = new ArrayList<EntityClassField>();
        for(Map<String, Object> entityFieldMap : entityFieldList){
            //??????entity??????
            EntityClassField entityClassField = initEntityClassField(entityFieldMap);
            entityClassFields.add(entityClassField);
        }
        entityClass.setEntityClassFields(entityClassFields);
        return entityClass;
    }

    /**
     * ??????service
     * @param projectConfig
     * @param tableCode
     * @param entityClass
     * @return
     * @throws Exception
     */
    private ServiceClass initService(ProjectConfig projectConfig, String tableCode, EntityClass entityClass) throws Exception {
        ServiceClass serviceClass = new ServiceClass();
        serviceClass.setServiceName(entityClass.getClassName()+"Service");
        serviceClass.setServiceImpl(BaseUtil.toLowerCaseFirstOne(entityClass.getClassName())+"Service");
        serviceClass.setEntityClass(entityClass);
        serviceClass.setServicePath(projectConfig.getProjectPath() + ".service." + sysModularTableMap.get(tableCode).getModularCode());
        serviceClass.setServiceFilePath(projectConfig.getFilePath() + File.separator + "service" + File.separator + sysModularTableMap.get(tableCode).getModularCode());
//      serviceClass.setServiceMethods();
        return serviceClass;
    }

    /**
     * ??????serviceimpl
     * @param projectConfig
     * @param tableCode
     * @param entityClass
     * @return
     * @throws Exception
     */
    private ServiceImplClass initServiceImpl(ProjectConfig projectConfig, String tableCode, EntityClass entityClass, MapperClass mapperClass, ServiceClass serviceClass) throws Exception {
        ServiceImplClass serviceImplClass = new ServiceImplClass();
        serviceImplClass.setServiceImplName(entityClass.getClassName()+"ServiceImpl");
        serviceImplClass.setEntityClass(entityClass);
        serviceImplClass.setMapperClass(mapperClass);
        serviceImplClass.setServiceClass(serviceClass);
        serviceImplClass.setServiceImplPath(projectConfig.getProjectPath() + ".service." + sysModularTableMap.get(tableCode).getModularCode() + ".impl");
        serviceImplClass.setServiceImplFilePath(projectConfig.getFilePath() + File.separator + "service" + File.separator + sysModularTableMap.get(tableCode).getModularCode() + File.separator + "impl");
//      serviceImplClass.setServiceImplMethods();
        return serviceImplClass;
    }

    /**
     * ??????controller
     * @param projectConfig
     * @param entityClass
     * @param serviceClass
     * @throws Exception
     */
    private void initController(ProjectConfig projectConfig, EntityClass entityClass, ServiceClass serviceClass, String tableCode) throws Exception {
        Map<String, ControllerClass> controllerClassMap = projectConfig.getControllerClassMap();
        String controllerName = entityClass.getClassName() + "Controller";
        ControllerClass controllerClass = controllerClassMap.get(controllerName);
        if(controllerClass == null){
            controllerClass = new ControllerClass();
        }
        controllerClass.setControllerName(controllerName);
        controllerClass.setMainEntityClass(entityClass);

        String controllerPath = projectConfig.getProjectPath() + ".controller." + sysModularTableMap.get(tableCode).getModularCode();
        String controllerFilePath = projectConfig.getFilePath() + File.separator + "controller" + File.separator + sysModularTableMap.get(tableCode).getModularCode();

        controllerClass.setControllerName(controllerName);
        controllerClass.setControllerPath(controllerPath);
        controllerClass.setControllerFilePath(controllerFilePath);
        controllerClass.setApiModular("/"+entityClass.getClassImpl());

        Map<String, EntityClass> controllerClassEntityClassMap = controllerClass.getEntityClassMap();
        if(controllerClassEntityClassMap == null){
            controllerClassEntityClassMap = new HashMap<>();
        }
        controllerClassEntityClassMap.put(entityClass.getClassName(), entityClass);
        controllerClass.setEntityClassMap(controllerClassEntityClassMap);

        Map<String, ServiceClass> controllerClassServiceClassMap = controllerClass.getServiceClassMap();
        if(controllerClassServiceClassMap == null){
            controllerClassServiceClassMap = new HashMap<>();
        }
        controllerClassServiceClassMap.put(serviceClass.getServiceName(), serviceClass);
        controllerClass.setServiceClassMap(controllerClassServiceClassMap);
        controllerClassMap.put(controllerClass.getControllerName(), controllerClass);
    }

    /**
     * ????????????controller,???????????????????????????controller???
     * @param projectConfig
     * @param sysInterfaceInfo
     * @param entityClass
     * @param serviceClass
     * @throws Exception
     */
    private void initMainController(ProjectConfig projectConfig, SysInterfaceInfo sysInterfaceInfo, EntityClass entityClass, ServiceClass serviceClass) throws Exception {
        Map<String, ControllerClass> controllerClassMap = projectConfig.getControllerClassMap();
        String controllerName = BaseUtil.toUpperCaseFirstOne(sysInterfaceInfo.getApiModular())+"Controller";
        ControllerClass controllerClass = controllerClassMap.get(controllerName);
        if(controllerClass == null){
            controllerClass = new ControllerClass();
            controllerClass.setControllerName(controllerName);
//          controllerClass.setMainEntityClass(entityClass);

            String controllerPath = projectConfig.getProjectPath() + ".controller." + sysInterfaceInfo.getFunModular();
            String controllerFilePath = projectConfig.getFilePath() + File.separator + "controller" + File.separator + sysInterfaceInfo.getFunModular();

            controllerClass.setControllerName(controllerName);
            controllerClass.setControllerPath(controllerPath);
            controllerClass.setControllerFilePath(controllerFilePath);
            controllerClass.setApiModular("/"+sysInterfaceInfo.getApiModular());
        }
        Map<String, EntityClass> controllerClassEntityClassMap = controllerClass.getEntityClassMap();
        if(controllerClassEntityClassMap == null){
            controllerClassEntityClassMap = new HashMap<>();
        }
        controllerClassEntityClassMap.put(entityClass.getClassName(), entityClass);
        controllerClass.setEntityClassMap(controllerClassEntityClassMap);

        Map<String, ServiceClass> controllerClassServiceClassMap = controllerClass.getServiceClassMap();
        if(controllerClassServiceClassMap == null){
            controllerClassServiceClassMap = new HashMap<>();
        }
        controllerClassServiceClassMap.put(serviceClass.getServiceName(), serviceClass);
        controllerClass.setServiceClassMap(controllerClassServiceClassMap);
        controllerClassMap.put(controllerClass.getControllerName(), controllerClass);
    }

    /**
     * ?????????LambdaWrappers
     * @param sysInterfaceInfo
     * @param projectConfig
     * @return
     * @throws Exception
     */
    private List<LambdaWrapper> initLambdaWrappers(SysInterfaceInfo sysInterfaceInfo, ProjectConfig projectConfig) throws Exception {
        List<LambdaWrapper> lambdaWrappers = new ArrayList<LambdaWrapper>();

        //querySqlBatch????????????????????????sql??????
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("sysInterfaceInfoId", sysInterfaceInfo.getId());
        List<Map<String, Object>> sqlBatchList = sysInterfaceTableColumnInfoService.queryCurrentInterfaceBatchList(paramMap);
        for(Map<String, Object> sqlBatchMap : sqlBatchList){
            List<LambdaWrapperDetail> lambdaWrapperDetails = new ArrayList<LambdaWrapperDetail>();
            QueryWrapper<SysInterfaceTableColumnInfo> sysInterfaceTableColumnInfoQueryWrapper = new QueryWrapper<SysInterfaceTableColumnInfo>()
                    .eq("sys_interface_info_id", sysInterfaceInfo.getId())
                    .eq("sql_batch_id", sqlBatchMap.get("sqlBatchId"));

            String tableCode = BaseUtil.returnString(sqlBatchMap.get("tableCode"));
            String entityName = BaseUtil.toUpperCaseFirstOne(UnderlineToCamelUtils.underlineToCamel(tableCode, true));
            Map<String, EntityClass> entityClassMap = projectConfig.getEntityClassMap();
            EntityClass entityClass = entityClassMap.get(entityName);
            Map<String, ProcessClass> processClassMap = projectConfig.getProcessClassMap();
            ProcessClass processClass = processClassMap.get(BaseUtil.toUpperCaseFirstOne(sysInterfaceInfo.getApiMethod())+"Process");

            //??????sql?????????
            List<SysInterfaceTableColumnInfo> sysInterfaceTableColumnInfoList = sysInterfaceTableColumnInfoService.list(sysInterfaceTableColumnInfoQueryWrapper);
            for(SysInterfaceTableColumnInfo sysInterfaceTableColumnInfo : sysInterfaceTableColumnInfoList){
                //??????LambdaWrapper??????
                LambdaWrapperDetail lambdaWrapperDetail = initLambdaWrapperDetail(entityClassMap, sysInterfaceTableColumnInfo, entityClass, processClass);
                lambdaWrapperDetails.add(lambdaWrapperDetail);
            }
            LambdaWrapper lambdaWrapper = new LambdaWrapper();
            lambdaWrapper.setLambdaWrapperDetails(lambdaWrapperDetails);
            lambdaWrapper.setType(BaseUtil.returnString(sqlBatchMap.get("sqlType")));
            lambdaWrapper.setCondition(BaseUtil.returnString(sqlBatchMap.get("sqlCondition")));
            lambdaWrapper.setEntityClass(entityClass);
            lambdaWrappers.add(lambdaWrapper);
        }
        return lambdaWrappers;
    }

    /**
     * ??????LambdaWrapper??????
     * @param sysInterfaceTableColumnInfo
     * @param entityClass
     * @param processClass
     * @return
     * @throws Exception
     */
    private LambdaWrapperDetail initLambdaWrapperDetail(Map<String, EntityClass> entityClassMap, SysInterfaceTableColumnInfo sysInterfaceTableColumnInfo, EntityClass entityClass, ProcessClass processClass) throws Exception {
        List<EntityClassField> entityClassFields = entityClass.getEntityClassFields();
        List<ProcessClassField> processClassFields = processClass.getProcessClassFields();
        String tableColumnCode = sysInterfaceTableColumnInfo.getTableColumnCode();
        String propertyName = UnderlineToCamelUtils.underlineToCamel(tableColumnCode, true);
        String paramCode = sysInterfaceTableColumnInfo.getParamCode();

        LambdaWrapperDetail lambdaWrapperDetail = new LambdaWrapperDetail();
        for(EntityClassField entityClassField : entityClassFields){
            if(entityClassField.getPropertyName().equals(propertyName)){
                lambdaWrapperDetail.setEntityClassField(entityClassField);
            }
        }
        for(ProcessClassField processClassField : processClassFields){
            if(processClassField.getPropertyName().equals(paramCode)){
                lambdaWrapperDetail.setProcessClassField(processClassField);
            }
        }
        String lambdaWrapperRule = dealLambdaWrapperRule(entityClassMap, paramCode);
        lambdaWrapperDetail.setLambdaWrapperRule(lambdaWrapperRule);
        return lambdaWrapperDetail;
    }

    public String dealLambdaWrapperRule(Map<String, EntityClass> entityClassMap, String paramCode) {
        if(paramCode.indexOf("entity") == 0){
            List<String> entityNames = RegularUtil.outputMsgs(paramCode, "entity", RegularUtil.SQUARE_BRACKETS);
            String entityName = entityNames.get(0);
            String field = paramCode.replace("entity["+entityName+"].", "");
            EntityClass entityClass = entityClassMap.get(entityName);
            List<EntityClassField> entityClassFields = entityClass.getEntityClassFields();
            String lambdaWrapperRule = entityClass.getClassImpl() + ".";
            for(EntityClassField entityClassField : entityClassFields){
                if(entityClassField.getPropertyName().equals(field)){
                    lambdaWrapperRule += entityClassField.getGetters() + "()";
                    break;
                }
            }
            return lambdaWrapperRule;
        }
        return null;
    }

    /**
     * ??????controller????????????
     * @param projectConfig
     * @param sysInterfaceInfo
     * @throws Exception
     */
    private void setController(ProjectConfig projectConfig, SysInterfaceInfo sysInterfaceInfo) throws Exception {
        String tableCode = UnderlineToCamelUtils.camelToUnderline(sysInterfaceInfo.getApiModular()).toLowerCase();
        Map<String, ControllerClass> controllerClassMap = projectConfig.getControllerClassMap();
        String controllerName = BaseUtil.toUpperCaseFirstOne(sysInterfaceInfo.getApiModular()) + "Controller";
        ControllerClass controllerClass = controllerClassMap.get(controllerName);
        //??????controller
        controllerClass.setProcessRuleClassMap(new HashMap<>());

        Map<String, ControllerMethod> controllerMethodMap = controllerClass.getMethodMap();
        if(controllerMethodMap == null){
            controllerMethodMap = new HashMap<>();
        }
        ControllerMethod controllerMethod = controllerMethodMap.get(sysInterfaceInfo.getApiMethod());
        if(controllerMethod == null){
            controllerMethod = new ControllerMethod();
        }
        controllerMethod.setApiMethod(sysInterfaceInfo.getApiMethod());

        //??????Vo??????process???
        setVoAndProcess(projectConfig, sysInterfaceInfo, controllerClass, controllerMethod);

        //?????????sqlLambdaWrapper
        List<LambdaWrapper> lambdaWrappers = initLambdaWrappers(sysInterfaceInfo, projectConfig);
        controllerMethod.setLambdaWrappers(lambdaWrappers);

        controllerMethodMap.put(sysInterfaceInfo.getApiMethod(), controllerMethod);
        controllerClass.setMethodMap(controllerMethodMap);

        controllerClassMap.put(controllerName, controllerClass);
        projectConfig.setControllerClassMap(controllerClassMap);
    }

    /**
     * ??????Vo??????process???
     * @param sysInterfaceInfo
     * @param controllerClass
     * @param controllerMethod
     * @throws Exception
     */
    private void setVoAndProcess(ProjectConfig projectConfig, SysInterfaceInfo sysInterfaceInfo, ControllerClass controllerClass, ControllerMethod controllerMethod) throws Exception {
        QueryWrapper<SysInterfaceColumnRule> sysInterfaceColumnRuleQueryWrapper = new QueryWrapper<SysInterfaceColumnRule>()
                .eq("sys_interface_info_id", sysInterfaceInfo.getId());
        List<SysInterfaceColumnRule> sysInterfaceColumnRuleList = sysInterfaceColumnRuleService.list(sysInterfaceColumnRuleQueryWrapper);
        ProcessClass processClass = new ProcessClass();
        processClass.setBelongModularName(sysInterfaceInfo.getFunModular());
        processClass.setBelongEntityName(BaseUtil.toUpperCaseFirstOne(sysInterfaceInfo.getApiModular()));
        processClass.setClassName(BaseUtil.toUpperCaseFirstOne(sysInterfaceInfo.getApiMethod())+"Process");
        processClass.setClassImpl(sysInterfaceInfo.getApiMethod()+"Process");
        processClass.setClassPath(projectConfig.getProjectPath() + ".process." + processClass.getBelongModularName()
                + "." + processClass.getBelongEntityName());
        processClass.setClassFilePath(projectConfig.getFilePath() + File.separator + "process" + File.separator + processClass.getBelongModularName()
                + File.separator + processClass.getBelongEntityName());
        List<ProcessClassField> processClassFields = new ArrayList<ProcessClassField>();

        VoClass voClass = new VoClass();
        voClass.setBelongModularName(sysInterfaceInfo.getFunModular());
        voClass.setBelongEntityName(BaseUtil.toUpperCaseFirstOne(sysInterfaceInfo.getApiModular()));
        voClass.setClassName(BaseUtil.toUpperCaseFirstOne(sysInterfaceInfo.getApiMethod())+"Vo");
        voClass.setClassImpl(sysInterfaceInfo.getApiMethod()+"Vo");
        voClass.setClassPath(projectConfig.getProjectPath() + ".vo." + voClass.getBelongModularName()
                + "." + voClass.getBelongEntityName());
        voClass.setClassFilePath(projectConfig.getFilePath() + File.separator + "vo" + File.separator + voClass.getBelongModularName()
                + File.separator + voClass.getBelongEntityName());
        List<VoClassField> voClassFields = new ArrayList<VoClassField>();

        for(SysInterfaceColumnRule sysInterfaceColumnRule : sysInterfaceColumnRuleList){
            //???????????????
            ProcessClassField processClassField = initProcessClassField(controllerClass, sysInterfaceColumnRule);
            processClassFields.add(processClassField);

            //??????vo??????
            VoClassField voClassField = initVoClassField(sysInterfaceColumnRule);
            if(voClassField != null){
                voClassFields.add(voClassField);
            }
        }
        processClass.setProcessClassFields(processClassFields);
        controllerMethod.setProcessClass(processClass);

        voClass.setVoClassFields(voClassFields);
        controllerMethod.setVoClass(voClass);

        Map<String, VoClass> voClassMap = projectConfig.getVoClassMap();
        if(voClassMap.get(voClass.getClassName()) == null){
            voClassMap.put(voClass.getClassName(), voClass);
        }

        Map<String, ProcessClass> processClassMap = projectConfig.getProcessClassMap();
        if(processClassMap.get(processClass.getClassName()) == null){
            processClassMap.put(processClass.getClassName(), processClass);
        }
    }

    /**
     * ?????????ProcessClassField
     * @param controllerClass
     * @param sysInterfaceColumnRule
     * @return
     * @throws Exception
     */
    private ProcessClassField initProcessClassField(ControllerClass controllerClass, SysInterfaceColumnRule sysInterfaceColumnRule) throws Exception {
        //???????????????
        ProcessClassField processClassField = new ProcessClassField();
        processClassField.setVoField(sysInterfaceColumnRule.getPageColumnCode());
        processClassField.setProcessingRule(sysInterfaceColumnRule.getProcessingRule());
        processClassField.setComment(sysInterfaceColumnRule.getPageColumnName());
        processClassField.setPropertyName(sysInterfaceColumnRule.getColumnAliasCode());
        processClassField.setPropertyType(FieldTypeMapper.transformType(sysInterfaceColumnRule.getPageColumnType()));
        processClassField.setGetters("get"+BaseUtil.toUpperCaseFirstOne(sysInterfaceColumnRule.getColumnAliasCode()));
        processClassField.setSetters("set"+BaseUtil.toUpperCaseFirstOne(sysInterfaceColumnRule.getColumnAliasCode()));
        //setProcessRuleClass
        codeDataProcessService.processData(processClassField, controllerClass.getProcessRuleClassMap());
        return processClassField;
    }

    /**
     * ?????????VoClassField
     * @param sysInterfaceColumnRule
     * @return
     * @throws Exception
     */
    private VoClassField initVoClassField(SysInterfaceColumnRule sysInterfaceColumnRule) throws Exception {
        if(sysInterfaceColumnRule.getPageColumnCode() == null){
            return null;
        }
        VoClassField voClassField = new VoClassField();
        voClassField.setComment(sysInterfaceColumnRule.getPageColumnName());
        voClassField.setPropertyName(sysInterfaceColumnRule.getPageColumnCode());
        voClassField.setPropertyType(FieldTypeMapper.transformType(sysInterfaceColumnRule.getPageColumnType()));
        voClassField.setGetters("get"+BaseUtil.toUpperCaseFirstOne(sysInterfaceColumnRule.getPageColumnCode()));
        voClassField.setSetters("set"+BaseUtil.toUpperCaseFirstOne(sysInterfaceColumnRule.getPageColumnCode()));
        voClassField.setIsRequired(sysInterfaceColumnRule.getIsRequired());
        voClassField.setMaxLength(sysInterfaceColumnRule.getMaxLength());
        return voClassField;
    }




















































































    /**
     * ????????????sql????????????
     * @param sqlMap ??????Sql????????? key?????????value-key=columnParam,???????????????;value-key=paramValue,?????????
     * @param columnValue ????????????
     * @param tableColumnCode ?????????
     * @param paramCode ?????????
     * @param sqlId sqlID
     * @param sqlCondition ????????????
     * @throws Exception
     */
    private void createSqlsMap(Map<Integer, Map<String, Object>> sqlMap, Object columnValue,
                               String tableColumnCode, String paramCode, int sqlId, String sqlCondition) throws Exception {
        Map<String, Object> sqlColumnMap = new HashMap<String, Object>();
        if(sqlMap.get(sqlId) != null){
            sqlColumnMap = sqlMap.get(sqlId);
        }

        String paramValue = columnValue == null ? null : BaseUtil.returnString(columnValue);
        if(paramValue == null && paramCode != null){
            //???????????????????????????
            if(paramCode.indexOf("sql_") == 0){
                paramValue = "#{"+paramCode+"}";
            }
        }

        //??????????????????
        List<String> columnParamList = new ArrayList<String>();
        if(sqlColumnMap.get("columnParam") != null){
            columnParamList = (List<String>) sqlColumnMap.get("columnParam");
        }
        columnParamList.add(tableColumnCode);
        sqlColumnMap.put("columnParam", columnParamList);

        //???????????????
        List<String> paramValueList = new ArrayList<String>();
        if(sqlColumnMap.get("paramValue") != null){
            paramValueList = (List<String>) sqlColumnMap.get("paramValue");
        }
        paramValueList.add(paramValue);
        sqlColumnMap.put("paramValue", paramValueList);
        sqlColumnMap.put("sqlCondition", sqlCondition);

        sqlMap.put(sqlId, sqlColumnMap);
    }

    /**
     * ??????sql????????????
     * @param sqlMap ??????Sql????????? key?????????value-key=isEffect,??????????????????value-key=columnParam,???????????????;value-key=paramValue,?????????
     * @param requestMap ??????????????????????????????
     * @param tableColumnCode ?????????
     * @param paramCode ?????????
     * @param sqlId sqlID
     * @param sqlCondition ????????????
     * @throws Exception
     */
    private void createSingleSqlMap(Map<Integer, Map<String, Object>> sqlMap, Map<String, Object> requestMap,
                                    String tableColumnCode, String paramCode, int sqlId, String sqlCondition) throws Exception {
        Map<String, Object> sqlColumnMap = new HashMap<String, Object>();
        if(sqlMap.get(sqlId) != null){
            sqlColumnMap = sqlMap.get(sqlId);
        }

        String paramValue = requestMap.get(paramCode) == null ? null : BaseUtil.returnString(requestMap.get(paramCode));
        if(paramValue == null && paramCode != null){
            //???????????????????????????
            if(paramCode.indexOf("sql_") == 0){
                paramValue = "#{"+paramCode+"}";
            }
        }

        //??????????????????
        List<String> columnParamList = new ArrayList<String>();
        if(sqlColumnMap.get("columnParam") != null){
            columnParamList = (List<String>) sqlColumnMap.get("columnParam");
        }
        columnParamList.add(tableColumnCode);
        sqlColumnMap.put("columnParam", columnParamList);

        //???????????????
        List<String> paramValueList = new ArrayList<String>();
        if(sqlColumnMap.get("paramValue") != null){
            paramValueList = (List<String>) sqlColumnMap.get("paramValue");
        }
        paramValueList.add(paramValue);
        sqlColumnMap.put("paramValue", paramValueList);
        sqlColumnMap.put("sqlCondition", sqlCondition);

        sqlMap.put(sqlId, sqlColumnMap);
    }
}
