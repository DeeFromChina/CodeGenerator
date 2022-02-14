package com.generator.service.codeGenerator.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.generator.service.sysInterfaceTableColumnInfo.SysInterfaceTableColumnInfoService;
import com.generator.service.sysInterfaceTableInfo.SysInterfaceTableInfoService;
import com.generator.util.FieldTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CodeGeneratorServiceImpl implements CodeGeneratorService {

    @Autowired
    private SysInterfaceColumnRuleService sysInterfaceColumnRuleService;

    @Autowired
    private SysInterfaceTableColumnInfoService sysInterfaceTableColumnInfoService;

    @Autowired
    private SysInterfaceTableInfoService sysInterfaceTableInfoService;

    @Autowired
    private DatabaseTableColumnService databaseTableColumnService;

    @Autowired
    private CodeDataProcessService codeDataProcessService;

    @Override
    public ControllerMethod loadMethodParam(ProjectConfig projectConfig, SysInterfaceInfo sysInterfaceInfo) throws Exception {
        QueryWrapper<SysInterfaceColumnRule> queryWrapper1 = new QueryWrapper<SysInterfaceColumnRule>()
                .eq("sys_interface_info_id", sysInterfaceInfo.getId());
        List<SysInterfaceColumnRule> sysInterfaceColumnRuleList = sysInterfaceColumnRuleService.list(queryWrapper1);

        return initMethod(projectConfig, sysInterfaceInfo, sysInterfaceColumnRuleList);
    }

    @Override
    public void loadEntityParam(ProjectConfig projectConfig, SysInterfaceInfo sysInterfaceInfo, Map<String, SysModularTable> sysModularTableMap) throws Exception {
        QueryWrapper<SysInterfaceTableInfo> queryWrapper = new QueryWrapper<SysInterfaceTableInfo>()
                .eq("sys_interface_info_id", sysInterfaceInfo.getId());
        List<SysInterfaceTableInfo> sysInterfaceTableInfos = sysInterfaceTableInfoService.list(queryWrapper);

        Map<String, ControllerClass> controllerClassMap = projectConfig.getControllerClassMap();
//        Map<String, EntityClass> entityClasses = projectConfig.getEntityClasse();
        for(SysInterfaceTableInfo sysInterfaceTableInfo : sysInterfaceTableInfos){
            //设置实体类信息
            String entityName = UnderlineToCamelUtils.underlineToCamel(sysInterfaceTableInfo.getTableCode(), true);
            String entityClass = BaseUtil.toUpperCaseFirstOne(entityName);
            EntityClass entity = new EntityClass();
            entity.setClassName(entityClass);
            entity.setClassImpl(entityName);
            SysModularTable sysModularTable = sysModularTableMap.get(sysInterfaceTableInfo.getTableCode());
            entity.setModularCode(sysModularTable.getModularCode());
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("tableName", sysInterfaceTableInfo.getTableCode());
            List<Map<String, Object>> datbaseTableColumns = databaseTableColumnService.queryDatabaseTableColumnListMap(paramMap);
            List<EntityClassField> entityClassFields = new ArrayList<EntityClassField>();
            for(Map<String, Object> datbaseTableColumn : datbaseTableColumns){
                EntityClassField entityClassField = new EntityClassField();
                String columnName = BaseUtil.returnString(datbaseTableColumn.get("columnName"));
                entityClassField.setPropertyName(UnderlineToCamelUtils.underlineToCamel(columnName, true));
                entityClassField.setPropertyType(FieldTypeMapper.transformType(BaseUtil.returnString(datbaseTableColumn.get("dataType"))));
                if(datbaseTableColumn.get("isNull") != null){
                    entityClassField.setIsRequired(1);
                }
                entityClassField.setMaxLength(BaseUtil.returnInt(datbaseTableColumn.get("columnLength")));
                entityClassField.setName(columnName);
                entityClassField.setComment(BaseUtil.returnString(datbaseTableColumn.get("columnDesc")));
                entityClassField.setGetters("get"+BaseUtil.toUpperCaseFirstOne(UnderlineToCamelUtils.underlineToCamel(columnName, true)));
                entityClassField.setSetters("set"+BaseUtil.toUpperCaseFirstOne(UnderlineToCamelUtils.underlineToCamel(columnName, true)));
                entityClassFields.add(entityClassField);
                entity.setEntityClassFields(entityClassFields);
            }
//            entityClasses.put(entityName, entity);

            if(controllerClassMap.get(entityClass+"Controller") == null){
                String controllerName = entityClass + "Controller";
//                String controllerPath = projectConfig.getProjectPath() + File.separator + "controller" + File.separator + projectConfig.getModuleName();

                ControllerClass controllerClass = new ControllerClass();
                controllerClass.setControllerName(controllerName);
//                controllerClass.setControllerPath(controllerPath);
//                controllerClass.setApiModular(projectConfig.getApiModular());
                controllerClass.setMethodMap(new HashMap<>());
                controllerClass.setProcessRuleClassMap(new HashMap<>());
                controllerClass.setServiceClassMap(new HashMap<>());
                controllerClassMap.put(controllerName, controllerClass);
            }
        }
//        if(entityClasses.get(projectConfig.getApiModular()) != null){
//            projectConfig.setMainEntityClass(entityClasses.get(projectConfig.getApiModular()));
//        }
    }

    /**
     * 设置接口方法内容
     * @param projectConfig
     * @param sysInterfaceInfo
     * @param sysInterfaceColumnRuleList
     * @return
     * @throws Exception
     */
    private ControllerMethod initMethod(ProjectConfig projectConfig, SysInterfaceInfo sysInterfaceInfo, List<SysInterfaceColumnRule> sysInterfaceColumnRuleList) throws Exception {
        List<VoClassField> fieldList = new ArrayList<>();
        List<ProcessClassField> processList = new ArrayList<>();
//        Map<String, String> processRuleClass = projectConfig.getProcessRuleClass();
        for(SysInterfaceColumnRule sysInterfaceColumnRule : sysInterfaceColumnRuleList){
            ProcessClassField processClassField = setProcessParam(sysInterfaceColumnRule);
            VoClassField voClassField = setVoParam(sysInterfaceColumnRule);
//            codeDataProcessService.processData(voClassField, processClassField, processRuleClass);
            processList.add(processClassField);
            if(BaseUtil.returnString(sysInterfaceColumnRule.getPageColumnCode()).equals("")){
                continue;
            }
            fieldList.add(voClassField);
        }

        ControllerMethod method = new ControllerMethod();
        String apiMethod = sysInterfaceInfo.getApiMethod();
        method.setApiMethod(apiMethod);

        //加载vo类数据
        VoClass voClass = new VoClass();
        voClass.setClassName(BaseUtil.toUpperCaseFirstOne(apiMethod)+"Vo");
        voClass.setClassImpl(apiMethod+"Vo");
        voClass.setVoClassFields(fieldList);
        method.setVoClass(voClass);

        //加载加工类数据
        ProcessClass processClass = new ProcessClass();
        processClass.setClassName(BaseUtil.toUpperCaseFirstOne(apiMethod)+"Process");
        processClass.setClassImpl(apiMethod+"Process");
        processClass.setProcessClassFields(processList);
        method.setProcessClass(processClass);

        //数据库操作
        method.setLambdaWrappers(initLambdaWrapper(sysInterfaceInfo.getId()));
        return method;
    }

    private static ProcessClassField setProcessParam(SysInterfaceColumnRule sysInterfaceColumnRule) {
        ProcessClassField processClassField = new ProcessClassField();
        processClassField.setSetters("set"+BaseUtil.toUpperCaseFirstOne(sysInterfaceColumnRule.getColumnAliasCode()));
        processClassField.setGetters("get"+BaseUtil.toUpperCaseFirstOne(sysInterfaceColumnRule.getColumnAliasCode()));
        //字段类型
        processClassField.setPropertyType(FieldTypeMapper.transformType(sysInterfaceColumnRule.getPageColumnType()));
        //字段编码
        processClassField.setPropertyName(sysInterfaceColumnRule.getColumnAliasCode());
        //字段名称
        processClassField.setComment(sysInterfaceColumnRule.getPageColumnName());
        if(!BaseUtil.isNull(sysInterfaceColumnRule.getPageColumnCode())){
            processClassField.setVoField(BaseUtil.toUpperCaseFirstOne(sysInterfaceColumnRule.getPageColumnCode()));
        }

        processClassField.setProcessingRule(sysInterfaceColumnRule.getProcessingRule());

        return processClassField;
    }

    private static VoClassField setVoParam(SysInterfaceColumnRule sysInterfaceColumnRule) {
        VoClassField voClassField = new VoClassField();
        voClassField.setPropertyType(FieldTypeMapper.transformType(sysInterfaceColumnRule.getPageColumnType()));
        voClassField.setPropertyName(sysInterfaceColumnRule.getPageColumnCode());
        voClassField.setComment(sysInterfaceColumnRule.getPageColumnName());
        if(sysInterfaceColumnRule.getIsRequired() == 1){
            voClassField.setIsRequired(sysInterfaceColumnRule.getIsRequired());
        }
        voClassField.setMaxLength(sysInterfaceColumnRule.getMaxLength());
        voClassField.setSetters("set"+BaseUtil.toUpperCaseFirstOne(sysInterfaceColumnRule.getColumnAliasCode()));
        voClassField.setGetters("get"+BaseUtil.toUpperCaseFirstOne(sysInterfaceColumnRule.getColumnAliasCode()));

        return voClassField;
    }

    private List<LambdaWrapper> initLambdaWrapper(Integer sysInterfaceInfoId) throws Exception {
        List<LambdaWrapper> lambdaWrappers = new ArrayList<LambdaWrapper>();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("sysInterfaceInfoId", sysInterfaceInfoId);
        //生成sql
        List<Map<String, Object>> sqlBatchList = sysInterfaceTableColumnInfoService.queryCurrentInterfaceBatchList(paramMap);

        //按批次
        for(Map<String, Object> sqlBatchMap : sqlBatchList){
            LambdaWrapper lambdaWrapper = new LambdaWrapper();
            lambdaWrapper.setType(BaseUtil.returnString(sqlBatchMap.get("type")));
            List<LambdaWrapperDetail> lambdaWrapperDetails = new ArrayList<LambdaWrapperDetail>();
            String fatherNodeCode = BaseUtil.returnString(sqlBatchMap.get("fatherNodeCode"));
            boolean hasFatherNodeCode = !"".equals(fatherNodeCode);
            //本批次
            List<SysInterfaceTableColumnInfo> sysInterfaceTableColumnInfoList = sysInterfaceTableColumnInfoService.querySysInterfaceTableColumnInfoList(sqlBatchMap);
            for(SysInterfaceTableColumnInfo sysInterfaceTableColumnInfo : sysInterfaceTableColumnInfoList){
                if(hasFatherNodeCode){

                }else{
                    String fieldName = UnderlineToCamelUtils.underlineToCamel(sysInterfaceTableColumnInfo.getTableColumnCode(), true);
                    String setValue1 = "get"+BaseUtil.toUpperCaseFirstOne(fieldName);
                    String setValue2 = "get"+BaseUtil.toUpperCaseFirstOne(sysInterfaceTableColumnInfo.getParamCode());
                    LambdaWrapperDetail lambdaWrapperDetail = new LambdaWrapperDetail();
//                    lambdaWrapperDetail.setEntityGetter(setValue1);
//                    lambdaWrapperDetail.setProcessGetter(setValue2);
                    lambdaWrapperDetails.add(lambdaWrapperDetail);
                }
            }
            lambdaWrapper.setLambdaWrapperDetails(lambdaWrapperDetails);
            lambdaWrappers.add(lambdaWrapper);
        }
        return lambdaWrappers;
    }

}
