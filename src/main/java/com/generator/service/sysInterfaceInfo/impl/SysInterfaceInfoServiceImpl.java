package com.generator.service.sysInterfaceInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dee.frame.springbootframe.service.sysRequestDataInfo.SysRequestDataInfoService;
import com.dee.frame.springbootframe.util.Key2ValueMap;
import com.dee.frame.springbootframe.util.common.BaseUtil;
import com.dee.frame.springbootframe.util.excel.ExcelUtil;
import com.generator.bean.sysInterfaceColumnRule.SysInterfaceColumnRule;
import com.generator.bean.sysInterfaceDataSql.SysInterfaceDataSql;
import com.generator.bean.sysInterfaceInfo.SysInterfaceInfo;
import com.generator.bean.sysInterfaceTableColumnInfo.SysInterfaceTableColumnInfo;
import com.generator.bean.sysInterfaceTableInfo.SysInterfaceTableInfo;
import com.generator.mapper.sysInterfaceInfo.SysInterfaceInfoMapper;
import com.generator.service.dataProcess.SysDataProcessService;
import com.generator.service.dataValidation.SysDataValidationService;
import com.generator.service.databaseTable.DatabaseTableColumnService;
import com.generator.service.sysInterfaceColumnRule.SysInterfaceColumnRuleService;
import com.generator.service.sysInterfaceDataSql.SysInterfaceDataSqlService;
import com.generator.service.sysInterfaceInfo.SysInterfaceInfoService;
import com.generator.service.sysInterfaceTableColumnInfo.SysInterfaceTableColumnInfoService;
import com.generator.service.sysInterfaceTableInfo.SysInterfaceTableInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service("sysInterfaceInfoService")
public class SysInterfaceInfoServiceImpl extends ServiceImpl<SysInterfaceInfoMapper, SysInterfaceInfo> implements SysInterfaceInfoService {

    @Autowired
    private SysInterfaceInfoMapper sysInterfaceInfoMapper;

    @Autowired
    private SysInterfaceColumnRuleService sysInterfaceColumnRuleService;

    @Autowired
    private SysInterfaceTableInfoService sysInterfaceTableInfoService;

    @Autowired
    private SysInterfaceTableColumnInfoService sysInterfaceTableColumnInfoService;

    @Autowired
    private SysDataValidationService sysDataValidationService;

    @Autowired
    private SysRequestDataInfoService sysRequestDataInfoService;

    @Autowired
    private SysDataProcessService sysDataProcessService;

    @Autowired
    private SysInterfaceDataSqlService sysInterfaceDataSqlService;

    @Autowired
    private DatabaseTableColumnService databaseTableColumnService;

    @Override
    public SysInterfaceInfo querySysInterfaceInfo(SysInterfaceInfo sysInterfaceInfo) throws Exception {
        return sysInterfaceInfoMapper.querySysInterfaceInfo(sysInterfaceInfo);
    }

    @Override
    public SysInterfaceInfo querySysInterfaceInfo(Map<String, Object> paramMap) throws Exception {
        return sysInterfaceInfoMapper.querySysInterfaceInfo(paramMap);
    }

    @Override
    public void insertSysInterfaceInfo(SysInterfaceInfo sysInterfaceInfo) throws Exception {
        sysInterfaceInfoMapper.insertSysInterfaceInfo(sysInterfaceInfo);
    }

    @Override
    public void updateSysInterfaceInfo(SysInterfaceInfo sysInterfaceInfo) throws Exception {
        sysInterfaceInfoMapper.updateSysInterfaceInfo(sysInterfaceInfo);
    }

    @Override
    public void dealInterfaceRequest(Map<String, Object> paramMap) throws Exception {
//        String apiId = BaseUtil.returnString(paramMap.get("apiId"));
        SysInterfaceInfo sysInterfaceInfo = querySysInterfaceInfo(paramMap);

        //??????????????????????????????
        Map<String, Object> pageRuleMap = new HashMap<String, Object>();
        pageRuleMap.put("sysInterfaceInfoId", sysInterfaceInfo.getId());

        List<SysInterfaceColumnRule> sysInterfaceColumnRuleList = sysInterfaceColumnRuleService.querySysInterfaceColumnRuleList(pageRuleMap);
        if(sysInterfaceColumnRuleList == null || sysInterfaceColumnRuleList.size() == 0){
            return;
        }

        boolean isPass = false;
        for(SysInterfaceColumnRule sysInterfaceColumnRule : sysInterfaceColumnRuleList){
            //??????id
            Integer interfaceId = BaseUtil.returnInt(sysInterfaceColumnRule.getSysInterfaceInfoId());
            //????????????id
            String pageColumnCode = BaseUtil.returnString(sysInterfaceColumnRule.getPageColumnCode());
            //????????????
            Integer isRequired = BaseUtil.returnInt(sysInterfaceColumnRule.getIsRequired());
            //??????????????????
            Integer maxLength = BaseUtil.returnInt(sysInterfaceColumnRule.getMaxLength());
            //????????????
            String pageColumnType = BaseUtil.returnString(sysInterfaceColumnRule.getPageColumnType());
            //??????????????????code
            String belongRuleGroupCode = BaseUtil.returnString(sysInterfaceColumnRule.getProcessingRule());
            //???????????????????????????
            String fatherNodeCode = BaseUtil.returnString(sysInterfaceColumnRule.getFatherNodeCode());
            ////??????????????????
            String columnAliasCode = BaseUtil.returnString(sysInterfaceColumnRule.getColumnAliasCode());
            //???????????????
            Object pageColumnValue = paramMap.get(pageColumnCode);

            //????????????
            if(!"".equals(fatherNodeCode)){

            }else{
//                isPass = sysDataValidationService.validationData(sysInterfaceColumnRule, pageColumnValue);
            }
        }
        //????????????
        Map<String, Object> processMap = sysDataProcessService.processData(sysInterfaceColumnRuleList, paramMap);

        //??????sql??????
        List<SysInterfaceDataSql> sqlList = sysInterfaceDataSqlService.createInterfaceSql(sysInterfaceInfo.getId(), processMap);

//        for(SysInterfaceDataSql sysInterfaceDataSql : sqlList){
//            System.out.println(sysInterfaceDataSql.getSql());
//        }

        //??????sql
        sysInterfaceDataSqlService.batchExcuteSql(sqlList);
    }

    @Override
    public void dataFromExcel(MultipartFile file) throws Exception {
        Key2ValueMap<String, String, String> excelContent = ExcelUtil.getExcelContent(file, 0, 0);
        //???????????????
        SysInterfaceInfo sysInterfaceInfo = addOrEditSysInterfaceInfo(excelContent);
        //???????????????????????????
        addSysInterfaceColumnRule(excelContent, sysInterfaceInfo);

        Key2ValueMap<String, String, String> excelContent2 = ExcelUtil.getExcelContent(file, 0, 1);
        //????????????????????????
        addTableAndColumn(excelContent2, sysInterfaceInfo);
    }

    private SysInterfaceInfo addOrEditSysInterfaceInfo(Key2ValueMap<String, String, String> excelContent) throws Exception {
        //?????????
        String interfaceName = excelContent.get("1", "B");
        //?????????
        String sysName = BaseUtil.returnString(excelContent.get("2", "B"));
        //?????????
        String apiModular = BaseUtil.returnString(excelContent.get("3", "B"));
        //?????????
        String apiMethod = BaseUtil.returnString(excelContent.get("4", "B"));
        //??????
        String apiType = BaseUtil.returnString(excelContent.get("5", "B"));
        //md5????????????????????????
        String apiId = DigestUtils.md5DigestAsHex((sysName + apiModular + apiMethod).getBytes());

        SysInterfaceInfo sysInterfaceInfo = new SysInterfaceInfo();
        sysInterfaceInfo.setInterfaceName(interfaceName);
        sysInterfaceInfo.setSysName(sysName);
        sysInterfaceInfo.setApiModular(apiModular);
        sysInterfaceInfo.setApiMethod(apiMethod);
        sysInterfaceInfo.setApiType(apiType);
        sysInterfaceInfo.setIsEnable(1);
        sysInterfaceInfo.setCreateTime(new Date());
        sysInterfaceInfo.setCreateUser("admin");
        sysInterfaceInfo.setLastUpdateTime(new Date());
        sysInterfaceInfo.setLastUpdateUser("admin");
        sysInterfaceInfo.setApiId(apiId);

        SysInterfaceInfo querySysInterfaceInfo = querySysInterfaceInfo(sysInterfaceInfo);
        if(querySysInterfaceInfo != null){
            sysInterfaceInfo.setId(querySysInterfaceInfo.getId());
            updateSysInterfaceInfo(sysInterfaceInfo);
        }else{
            insertSysInterfaceInfo(sysInterfaceInfo);
        }
        return sysInterfaceInfo;
    }

    private void addSysInterfaceColumnRule(Key2ValueMap<String, String, String> excelContent, SysInterfaceInfo sysInterfaceInfo) throws Exception {
        //???????????????????????????????????????????????????
        List<SysInterfaceColumnRule> interfaceColumnRuleList = new ArrayList<SysInterfaceColumnRule>();
        for(int i = 8; i < excelContent.size() + 1; i++){
            //??????????????????
            String pageColumnCode = excelContent.get(String.valueOf(i), "A");
            //??????????????????
            String pageColumnName = excelContent.get(String.valueOf(i), "B");
            //????????????
            String isRequired = BaseUtil.returnString(excelContent.get(String.valueOf(i), "C"));
            //??????????????????
            Integer maxLength = BaseUtil.returnInt(excelContent.get(String.valueOf(i), "D"));
            //????????????
            String columnSource = excelContent.get(String.valueOf(i), "E");
            //????????????
            String pageColumnType = excelContent.get(String.valueOf(i), "F");
            //????????????
            String validateRule = excelContent.get(String.valueOf(i), "G");
            //????????????
            String processingRule = excelContent.get(String.valueOf(i), "H");
            //??????????????????
            String columnAliasCode = excelContent.get(String.valueOf(i), "I");
            //????????????
            String fatherNodeCode = excelContent.get(String.valueOf(i), "J");

            if(columnAliasCode == null){
                continue;
            }

            SysInterfaceColumnRule sysInterfaceColumnRule = new SysInterfaceColumnRule();
            sysInterfaceColumnRule.setSysInterfaceInfoId(sysInterfaceInfo.getId());
            sysInterfaceColumnRule.setPageColumnCode(pageColumnCode);
            sysInterfaceColumnRule.setPageColumnName(pageColumnName);
            sysInterfaceColumnRule.setColumnSource(columnSource);
            sysInterfaceColumnRule.setPageColumnType(pageColumnType);
            sysInterfaceColumnRule.setFatherNodeCode(fatherNodeCode);
            sysInterfaceColumnRule.setMaxLength(maxLength);
            sysInterfaceColumnRule.setIsRequired(isRequired.equals("???") ? 1 : 0);
            sysInterfaceColumnRule.setValidateRule(validateRule);
            sysInterfaceColumnRule.setProcessingRule(processingRule);
            sysInterfaceColumnRule.setColumnAliasCode(columnAliasCode);
            sysInterfaceColumnRule.setCreateTime(new Date());
            sysInterfaceColumnRule.setCreateUser("admin");
            sysInterfaceColumnRule.setLastUpdateTime(new Date());
            sysInterfaceColumnRule.setLastUpdateUser("admin");
            interfaceColumnRuleList.add(sysInterfaceColumnRule);
        }
        if(interfaceColumnRuleList.size() > 0){
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("sysInterfaceInfoId", sysInterfaceInfo.getId());
            sysInterfaceColumnRuleService.deleteSysInterfaceColumnRule(paramMap);
            sysInterfaceColumnRuleService.insertSysInterfaceColumnRuleList(interfaceColumnRuleList);
        }
    }

    private void addTableAndColumn(Key2ValueMap<String, String, String> excelContent2, SysInterfaceInfo sysInterfaceInfo) throws Exception {
        //?????????????????????
        List<Map<String, Object>> tableList = new ArrayList<Map<String, Object>>();
        Map<String, String> tableInfoMap = new HashMap<String, String>();
        List<SysInterfaceTableInfo> sysInterfaceTableInfoList = new ArrayList<SysInterfaceTableInfo>();
        List<SysInterfaceTableColumnInfo> sysInterfaceTableColumnInfoList = new ArrayList<SysInterfaceTableColumnInfo>();
        for(int i = 2; i < excelContent2.size() + 1; i++){
            //????????????
            Integer sqlBatchId = BaseUtil.returnInt(excelContent2.get(String.valueOf(i), "A"));
            //????????????
            String conditionValue = excelContent2.get(String.valueOf(i), "B");
            //??????
            String tableCode = excelContent2.get(String.valueOf(i), "C");
            //????????????
            String tableColumnCode = excelContent2.get(String.valueOf(i), "D");
            //?????????
            String paramCode = excelContent2.get(String.valueOf(i), "E");
            //????????????
            String fatherNodeCode = excelContent2.get(String.valueOf(i), "F");
            //????????????
            String sqlType = excelContent2.get(String.valueOf(i), "G");
            //???????????????sql???
            String sqlCondition = excelContent2.get(String.valueOf(i), "H");

            if(tableInfoMap.get(tableCode) == null){
                SysInterfaceTableInfo sysInterfaceTableInfo = new SysInterfaceTableInfo();
                sysInterfaceTableInfo.setSysInterfaceInfoId(sysInterfaceInfo.getId());
                sysInterfaceTableInfo.setTableCode(tableCode);
                sysInterfaceTableInfo.setCreateTime(new Date());
                sysInterfaceTableInfo.setCreateUser("admin");
                sysInterfaceTableInfo.setLastUpdateTime(new Date());
                sysInterfaceTableInfo.setLastUpdateUser("admin");
                tableInfoMap.put(tableCode, "");
                sysInterfaceTableInfoList.add(sysInterfaceTableInfo);
            }

            SysInterfaceTableColumnInfo sysInterfaceTableColumnInfo = new SysInterfaceTableColumnInfo();
            sysInterfaceTableColumnInfo.setSysInterfaceInfoId(sysInterfaceInfo.getId());
            sysInterfaceTableColumnInfo.setSqlBatchId(sqlBatchId);
            sysInterfaceTableColumnInfo.setConditionValue(conditionValue);
            sysInterfaceTableColumnInfo.setTableCode(tableCode);
            sysInterfaceTableColumnInfo.setTableColumnCode(tableColumnCode);
            sysInterfaceTableColumnInfo.setParamCode(paramCode);
            sysInterfaceTableColumnInfo.setFatherNodeCode(fatherNodeCode);
            sysInterfaceTableColumnInfo.setSqlType(sqlType);
            sysInterfaceTableColumnInfo.setSqlCondition(sqlCondition);
            sysInterfaceTableColumnInfo.setCreateTime(new Date());
            sysInterfaceTableColumnInfo.setCreateUser("admin");
            sysInterfaceTableColumnInfo.setLastUpdateTime(new Date());
            sysInterfaceTableColumnInfo.setLastUpdateUser("admin");
            sysInterfaceTableColumnInfoList.add(sysInterfaceTableColumnInfo);
        }
        if(sysInterfaceTableInfoList.size() > 0){
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("sysInterfaceInfoId", sysInterfaceInfo.getId());
            sysInterfaceTableInfoService.deleteSysInterfaceTableInfo(paramMap);
            sysInterfaceTableInfoService.insertSysInterfaceTableInfoList(sysInterfaceTableInfoList);
            sysInterfaceTableColumnInfoService.deleteSysInterfaceTableColumnInfo(paramMap);
            sysInterfaceTableColumnInfoService.insertSysInterfaceTableColumnInfoList(sysInterfaceTableColumnInfoList);
        }
    }

    @Override
    public void initSingleTalbeTemplateToExcel(String tableName) throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("tableName", tableName);
        paramMap.put("databaseName", "test");
        List<Map<String, Object>> tableColumnList = databaseTableColumnService.queryDatabaseTableColumnListMap(paramMap);
        if(tableColumnList.size() == 0){
            throw new Exception("???????????????");
        }
        SingleTableAddTemplate.createTemplate(tableName, tableColumnList);
        SingleTableUpdateTemplate.createTemplate(tableName, tableColumnList);
        SingleTableDeleteTemplate.createTemplate(tableName, tableColumnList);
    }

    @Override
    public void initMultipleTablesTemplateToExcel(String mainTableCode, String subTables) throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("tableName", mainTableCode);
        paramMap.put("databaseName", "test");
        List<Map<String, Object>> tableColumnList = databaseTableColumnService.queryDatabaseTableColumnListMap(paramMap);
        if(tableColumnList.size() == 0){
            throw new Exception("???????????????");
        }
        //???????????????
        SingleTableAddTemplate.createTemplate(mainTableCode, tableColumnList);
        SingleTableUpdateTemplate.createTemplate(mainTableCode, tableColumnList);
        SingleTableDeleteTemplate.createTemplate(mainTableCode, tableColumnList);

        List<List<Map<String, Object>>> subTableList = new ArrayList<List<Map<String, Object>>>();
        String[] subTablesArray = subTables.split(",");
        for(int i = 0; i < subTablesArray.length; i++){
            String tableName = subTablesArray[i];
            paramMap.put("tableName", tableName);
            List<Map<String, Object>> subTableColumnList = databaseTableColumnService.queryDatabaseTableColumnListMap(paramMap);
            if(subTableColumnList.size() == 0){
                throw new Exception("???????????????");
            }
            //???????????????
            SingleTableAddTemplate.createTemplate(tableName, subTableColumnList);
            SingleTableUpdateTemplate.createTemplate(tableName, subTableColumnList);
            SingleTableDeleteTemplate.createTemplate(tableName, subTableColumnList);
            subTableList.add(subTableColumnList);
        }

        MultipleTableAddTemplate.createTemplate(mainTableCode, subTablesArray, tableColumnList, subTableList);
        MultipleTableUpdateTemplate.createTemplate(mainTableCode, subTablesArray, tableColumnList, subTableList);
        MultipleTableDeleteTemplate.createTemplate(mainTableCode, subTablesArray, tableColumnList, subTableList);
    }

}
