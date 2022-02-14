package com.generator.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dee.frame.springbootframe.controller.BaseController;
import com.dee.frame.springbootframe.util.common.BaseUtil;
import com.generator.bean.codeGenerator.ControllerMethod;
import com.generator.bean.codeGenerator.ProjectConfig;
import com.generator.bean.sysInterfaceInfo.SysInterfaceInfo;
import com.generator.bean.sysModularTable.SysModularTable;
import com.generator.service.codeGenerator.CodeGeneratorService;
import com.generator.service.dataProcess.CodeDataProcessService;
import com.generator.service.sysInterfaceColumnRule.SysInterfaceColumnRuleService;
import com.generator.service.sysInterfaceInfo.SysInterfaceInfoService;
import com.generator.service.sysInterfaceTableColumnInfo.SysInterfaceTableColumnInfoService;
import com.generator.service.sysModularTable.SysModularTableService;
import com.generator.util.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/codeGenerator2")
public class CodeGeneratorController2 extends BaseController {

    @Autowired
    private SysModularTableService sysModularTableService;

    @Autowired
    private SysInterfaceInfoService sysInterfaceInfoService;

    @Autowired
    private SysInterfaceColumnRuleService sysInterfaceColumnRuleService;

    @Autowired
    private SysInterfaceTableColumnInfoService sysInterfaceTableColumnInfoService;

    @Autowired
    private CodeGeneratorService codeGeneratorService;

    @Autowired
    private CodeDataProcessService codeDataProcessService;

    private Map<String, SysModularTable> sysModularTableMap;

    private String filePath = "D:\\sunline\\auto_workspace\\auto-project\\src\\main\\java\\com\\project";

    @PostMapping("/initInterfaceTemplateToJava")
    public Map<String, Object> initInterfaceTemplateToJava(HttpServletRequest request, @RequestBody String data) {
        try{
            setMap(request, data);
            sysModularTableMap = new HashMap<String, SysModularTable>();
            String sysName = BaseUtil.returnString(form.get("sysName"));
            String apiModular = BaseUtil.returnString(form.get("apiModular"));
            String apiMethod = BaseUtil.returnString(form.get("apiMethod"));

            //设置项目公共参数
            ProjectConfig projectConfig = new ProjectConfig();
            projectConfig.setFilePath(filePath);
            projectConfig.setProjectPath("com.project");
//            projectConfig.setModuleName(BaseUtil.returnString(form.get("funModular")));
//            projectConfig.setApiModular(apiModular);
//            projectConfig.setMethods(null);
            projectConfig.setAuthor("admin");
            projectConfig.setCreateTime(sdf.format(new Date()));
//            projectConfig.setProcessRuleClass(new HashMap<String, String>());
//            projectConfig.setEntityClasse(new HashMap<>());

            List<SysModularTable> sysModularTables = sysModularTableService.list();
            for(SysModularTable sysModularTable : sysModularTables){
                sysModularTableMap.put(sysModularTable.getTableCode(), sysModularTable);
            }

            QueryWrapper<SysInterfaceInfo> queryWrapper = new QueryWrapper<SysInterfaceInfo>()
                    .like("sys_name", sysName)
                    .like("api_modular", apiModular)
                    .like("api_method", apiMethod);
            List<SysInterfaceInfo> sysInterfaceInfoList = sysInterfaceInfoService.list(queryWrapper);
            List<ControllerMethod> methods = new ArrayList<ControllerMethod>();
            for(SysInterfaceInfo sysInterfaceInfo : sysInterfaceInfoList){
                ControllerMethod method = codeGeneratorService.loadMethodParam(projectConfig, sysInterfaceInfo);
                codeGeneratorService.loadEntityParam(projectConfig, sysInterfaceInfo, sysModularTableMap);
                methods.add(method);
            }
//            projectConfig.setMethods(methods);

            CodeGenerator codeGenerator = new CodeGenerator();
            codeGenerator//.createVo(projectConfig)
                    //.createProcess(projectConfig)
                    //.createEntitys(projectConfig)
//                    .createService(projectConfig)
//                    .createServiceImpl(projectConfig);
//                    .createMapper(projectConfig);
                    .createController(projectConfig);
//            .createCommonProcess(publicMap, null, filePath);

            return toJson(SUCCESS);
        }catch(Exception e){
            e.printStackTrace();
            return toJson(ERROR);
        }
    }


    /**
     * 创建多行sql组成参数
     * @param sqlMap 生成Sql的参数 key行号。value-key=columnParam,表字段参数;value-key=paramValue,值参数
     * @param columnValue 页面取值
     * @param tableColumnCode 表字段
     * @param paramCode 值编码
     * @param sqlId sqlID
     * @param sqlCondition 数据条件
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
            //需要取前面批次的值
            if(paramCode.indexOf("sql_") == 0){
                paramValue = "#{"+paramCode+"}";
            }
        }

        //字段参数列表
        List<String> columnParamList = new ArrayList<String>();
        if(sqlColumnMap.get("columnParam") != null){
            columnParamList = (List<String>) sqlColumnMap.get("columnParam");
        }
        columnParamList.add(tableColumnCode);
        sqlColumnMap.put("columnParam", columnParamList);

        //值参数列表
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
     * 创建sql组成参数
     * @param sqlMap 生成Sql的参数 key行号。value-key=isEffect,有效的数据；value-key=columnParam,表字段参数;value-key=paramValue,值参数
     * @param requestMap 页面处理后的数据数组
     * @param tableColumnCode 表字段
     * @param paramCode 值编码
     * @param sqlId sqlID
     * @param sqlCondition 数据条件
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
            //需要取前面批次的值
            if(paramCode.indexOf("sql_") == 0){
                paramValue = "#{"+paramCode+"}";
            }
        }

        //字段参数列表
        List<String> columnParamList = new ArrayList<String>();
        if(sqlColumnMap.get("columnParam") != null){
            columnParamList = (List<String>) sqlColumnMap.get("columnParam");
        }
        columnParamList.add(tableColumnCode);
        sqlColumnMap.put("columnParam", columnParamList);

        //值参数列表
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
