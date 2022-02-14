package com.generator.controller.sysInterfaceInfo.process;

import com.dee.frame.springbootframe.util.UnderlineToCamelUtils;
import com.dee.frame.springbootframe.util.common.BaseUtil;
import com.generator.bean.codeGenerator.ProcessClassField;
import com.generator.bean.codeGenerator.VoClassField;
import com.generator.bean.sysInterfaceColumnRule.SysInterfaceColumnRule;
import com.generator.service.dataProcess.CodeDataProcessService;
import com.generator.util.FieldTypeMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysInterfaceInfoProcess {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Map<String, Object> setPublicParam(Map<String, Object> form) {
        Map<String, Object> publicMap = new HashMap<String, Object>();
        publicMap.put("projectPath", "com.project");
        publicMap.put("moduleName", BaseUtil.returnString(form.get("funModular")));
        publicMap.put("entity", BaseUtil.toUpperCaseFirstOne(BaseUtil.returnString(form.get("apiModular"))));
        publicMap.put("apiMethod", BaseUtil.toUpperCaseFirstOne(BaseUtil.returnString(form.get("apiMethod"))));
        publicMap.put("author", "admin");
        publicMap.put("createTime", sdf.format(new Date()));
        return publicMap;
    }

    public static void setFieldParam(List<SysInterfaceColumnRule> sysInterfaceColumnRuleList,
                                      List<VoClassField> fieldList, List<ProcessClassField> processList,
                                      CodeDataProcessService codeDataProcessService, Map<String, String> processRuleClass) throws Exception {
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

    public static List<Map<String, Object>> setserviceParam(List<SysInterfaceColumnRule> sysInterfaceColumnRuleList){
        return null;
    }

}
