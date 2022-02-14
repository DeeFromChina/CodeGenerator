package com.generator.service.sysInterfaceDataSql.impl;

import com.dee.frame.springbootframe.util.common.BaseUtil;
import com.generator.bean.mybatisTable.MyBatisTable;
import com.generator.bean.mybatisTable.MyBatisTableColumn;
import com.generator.bean.sysInterfaceDataSql.SysInterfaceDataSql;
import com.generator.bean.sysInterfaceTableColumnInfo.SysInterfaceTableColumnInfo;
import com.generator.mapper.sysInterfaceDataSql.SysInterfaceDataSqlMapper;
import com.generator.service.sysInterfaceDataSql.SysInterfaceDataSqlService;
import com.generator.service.sysInterfaceDataSql.SysInterfaceDataSqlValidityService;
import com.generator.service.sysInterfaceDataSql.impl.createSql.DeleteSqlUtil;
import com.generator.service.sysInterfaceDataSql.impl.createSql.InsertSqlUtil;
import com.generator.service.sysInterfaceDataSql.impl.createSql.UpdateSqlUtil;
import com.generator.service.sysInterfaceDataSql.impl.replaceSqlParam.ReplaceSqlParamUtil;
import com.generator.service.sysInterfaceTableColumnInfo.SysInterfaceTableColumnInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sysInterfaceDataSqlService")
public class SysInterfaceDataSqlServiceImpl implements SysInterfaceDataSqlService {

    @Autowired
    private SysInterfaceDataSqlMapper sysInterfaceDataSqlMapper;

    @Autowired
    private SysInterfaceDataSqlValidityService sysInterfaceDataSqlValidityService;

    @Autowired
    private SysInterfaceTableColumnInfoService sysInterfaceTableColumnInfoService;

    /**
     * 返回表操作的判断条件
     * @param paramMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> querySysInterfaceDataCondition(Map<String, Object> paramMap) throws Exception {
        return sysInterfaceTableColumnInfoService.queryCurrentInterfaceBatchList(paramMap);
    }

    /**
     * 根据批次号查询表操作涉及的字段
     * @param paramMap（sysInterfaceInfoId接口id（必填），sqlBatchId批次号（必填），tableCode表编码（必填））
     * @return
     * @throws Exception
     */
    public List<SysInterfaceTableColumnInfo> queryConditionColumn(Map<String, Object> paramMap) throws Exception {
        return sysInterfaceTableColumnInfoService.querySysInterfaceTableColumnInfoList(paramMap);
    }

    /**
     * 实时处理数据
     * @param paramMap
     * @throws Exception
     */
    public void handleData(Map<String, Object> paramMap) throws Exception {
        //表列表
        List<Map<String, Object>> tableList = null;//querySysInterfaceDataTable(paramMap);
        if(tableList == null || tableList.size() == 0){
            return;
        }
        //转换形态
        Map<Integer, MyBatisTable> myBatisTableMap = new HashMap<Integer, MyBatisTable>();
        for(Map<String, Object> tableMap : tableList){
            List<Map<String, Object>> columnList = null;//querySysInterfaceDataColumn(tableMap);
            if(columnList == null || columnList.size() == 0){
                continue;
            }
            for(Map<String, Object> columnMap : columnList){
                //表字段编码
                String tableColumnCode = BaseUtil.returnString(columnMap.get("table_column_code"));
                //引用字段别名
                String paramCode = BaseUtil.returnString(columnMap.get("param_code"));
                //行数编码
                String countCode = BaseUtil.returnString(columnMap.get("is_count"));
                if(!"".equals(countCode)){
                    int count = BaseUtil.returnInt(countCode);
                    for(int j = 0; j < count + 1; j++){
                        setMyBatisTableMap(myBatisTableMap, tableColumnCode, paramCode, j);
                    }
                }else{
                    setMyBatisTableMap(myBatisTableMap, tableColumnCode, paramCode, -1);
                }
            }
        }
        if(myBatisTableMap == null || myBatisTableMap.size() == 0){
            return;
        }
        //生成sql
        Map<String, String> countSqlMap = new HashMap<String, String>();
        for(Map.Entry<Integer, MyBatisTable> entry : myBatisTableMap.entrySet()){
            MyBatisTable myBatisTable = entry.getValue();
            if(myBatisTable.getEffectValue() < 1){
                continue;
            }

            countSqlMap.put(entry.getKey().toString(), myBatisTable.toString());
        }
        //执行sql
//		batchExcuteSql(countSqlMap);
    }

    //表操作的判断条件
//			List<Map<String, Object>> tableConditionList = querySysInterfaceDataCondition(paramMap);
//			if(tableConditionList == null || tableConditionList.size() == 0){
//				return;
//			}
//
//
//
//			//循环每个表操作的判断条件
//			for(Map<String, Object> tableConditionMap : tableConditionList){
//				tableConditionMap.putAll(paramMap);
//
//				String conditionValue = BaseUtil.returnString(tableConditionMap.get("condition_value"));
//				List<String> conditionParamList = RegularUtil.outputMsg22(conditionValue, "foreach");
//				if(conditionParamList != null && conditionParamList.size() > 0){
//					String conditionParam = conditionParamList.get(0);
//					tableConditionMap.put("column_alias_code", conditionParam);
//					//查询页面字段信息，取循环关键字
//					SysInterfaceColumnRuleService sysInterfaceColumnRuleService = new SysInterfaceColumnRuleServiceImpl(req, commonDao);
//					List<Map<String, Object>> list = sysInterfaceColumnRuleService.querySysInterfaceColumnRuleToListMap(tableConditionMap);
//					if(list == null || list.size() == 0){
//						CommonException.setError(req, commonDao, "9004");//没找到该地点
//						return;
//					}
//
//					for(Map<String, Object> map : list){
//						//行数编码
//						String countCode = BaseUtil.returnString(map.get("is_count"));
//						//多行操作
//						if(!"".equals(countCode)){
//							int count = BaseUtil.returnInt(requestMap.get(countCode));
//							for(int j = 0; j < count + 1; j++){
//								Map<String, Object> param = new HashMap<String, Object>();
//								param.put(conditionParam, conditionParam + j);
//								conditionValue = conditionValue.replace("foreach{"+conditionParam+"}", conditionParam);
//								Object o = SqlParams.convertToCode(conditionValue, requestMap);
//								System.out.println(o);
//							}
//						}
//					}
//
//
//				}
//			}

    /**
     * 跑批处理数据
     * @param sysInterfaceInfoId
     * @throws Exception
     */
    @Override
    public List<SysInterfaceDataSql> createInterfaceSql(Integer sysInterfaceInfoId, Map<String, Object> processMap) throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("sysInterfaceInfoId", sysInterfaceInfoId);
        //按条件批次查询
        List<Map<String, Object>> tableConditionList = querySysInterfaceDataCondition(paramMap);
        if(tableConditionList == null || tableConditionList.size() == 0){
            return null;
        }
        List<SysInterfaceDataSql> sqlList = new ArrayList<SysInterfaceDataSql>();
        //循环条件
        for(Map<String, Object> tableConditionMap : tableConditionList){
            String sqlBatchId = BaseUtil.returnString(tableConditionMap.get("sqlBatchId"));
            tableConditionMap.putAll(paramMap);

            Map<String, String> countSqlMap = createBatchSql(tableConditionMap, processMap);
            for(Map.Entry<String, String> entry : countSqlMap.entrySet()){
                SysInterfaceDataSql sysInterfaceDataSql = new SysInterfaceDataSql();
                sysInterfaceDataSql.setSqlId(sqlBatchId + "_" + entry.getKey());
                sysInterfaceDataSql.setSqlBatchId(sqlBatchId);
                String sql = entry.getValue();
                //替换sql中的替换符
                sql = ReplaceSqlParamUtil.repalceParam(sql, processMap);
                sysInterfaceDataSql.setSql(sql);
                sqlList.add(sysInterfaceDataSql);
            }
        }
        return sqlList;

    }

    /**
     * 生成同批次sql
     * @param paramMap
     * @param processMap
     * @throws Exception
     */
    private Map<String, String> createBatchSql(Map<String, Object> paramMap, Map<String, Object> processMap) throws Exception {

        String sqlBatchId = BaseUtil.returnString(paramMap.get("sqlBatchId"));
        String conditionValue = BaseUtil.returnString(paramMap.get("conditionValue"));
        String tableCode = BaseUtil.returnString(paramMap.get("tableCode"));
        String sqlType = BaseUtil.returnString(paramMap.get("sqlType"));

        //根据接口和批次号查询表
        List<SysInterfaceTableColumnInfo> columnList = queryConditionColumn(paramMap);
        if(columnList == null || columnList.size() == 0){
            return null;
        }
        //生成sql
        Map<String, String> countSqlMap = new HashMap<String, String>();

        //多行操作
        //记录操作sql的数组,key是行数,value是有效性，表字段，值
        Map<Integer, Map<String, Object>> sqlMap = new HashMap<Integer, Map<String, Object>>();
        boolean hasFatherCode = false;
        //字段操作
        for(SysInterfaceTableColumnInfo sysInterfaceTableColumnInfo : columnList){
            //表字段编码
            String tableColumnCode = BaseUtil.returnString(sysInterfaceTableColumnInfo.getTableColumnCode());
            //引用字段别名
            String paramCode = BaseUtil.returnString(sysInterfaceTableColumnInfo.getParamCode());
            //语句条件
            String sqlCondition = BaseUtil.returnString(sysInterfaceTableColumnInfo.getSqlCondition());
            //行数编码
            String fatherNodeCode = BaseUtil.returnString(sysInterfaceTableColumnInfo.getFatherNodeCode());

            //多行操作
            if(!"".equals(fatherNodeCode)){
                hasFatherCode = true;
                List<Map<String, Object>> dataList = (List<Map<String, Object>>) processMap.get(fatherNodeCode);
                int i = 1;
                for(Map<String, Object> map : dataList){
                    String v = BaseUtil.returnString(map.get(paramCode));
                    createSqlsMap(sqlMap, map.get(paramCode), tableColumnCode, paramCode, i, sqlCondition);
                    i++;
                }
            }
            //单行操作
            else{
                createSingleSqlMap(sqlMap, processMap, tableColumnCode, paramCode, -1, sqlCondition);
            }
        }

        if(hasFatherCode){
            Map<String, Object> commonMap = sqlMap.get(-1);
            List<String> columnParamList = (List<String>) commonMap.get("columnParam");
            List<String> paramValueList = (List<String>) commonMap.get("paramValue");
            for(Map.Entry<Integer, Map<String, Object>> entry : sqlMap.entrySet()){
                if(entry.getKey() == -1){
                    continue;
                }
                Map<String, Object> map = entry.getValue();
                List<String> columnParam = (List<String>) map.get("columnParam");
                List<String> paramValue = (List<String>) map.get("paramValue");
                columnParam.addAll(columnParamList);
                paramValue.addAll(paramValueList);
            }
            sqlMap.remove(-1);
        }

        //创建sql语句
        countSqlMap.putAll(createSql(sqlType, tableCode, sqlMap));

        //校验sql的可执行条件，不可执行的sql将去掉
        sysInterfaceDataSqlValidityService.validityExcuteSqlCondition(countSqlMap, sqlBatchId, conditionValue, processMap);

        return countSqlMap;

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

    /**
     * 创建sql语句
     * @param sqlType 表操作类型（insert,update,select,delete）
     * @param tableCode 表编码
     * @param sqlMap 生成Sql的参数 key行号。value-key=isEffect,有效的数据；value-key=columnParam,表字段参数;value-key=paramValue,值参数
     * @return
     * @throws Exception
     */
    private Map<String, String> createSql(String sqlType, String tableCode, Map<Integer, Map<String, Object>> sqlMap) throws Exception {
        Map<String, String> countSqlMap = new HashMap<String, String>();
        if(sqlMap.size() > 0){
            //insert
            if("insert".equals(sqlType)){
                countSqlMap.putAll(
                        //生成insert语句
                        InsertSqlUtil.createInsertSql(tableCode, sqlMap)
                );
            }
            //update
            else if("update".equals(sqlType)){
                countSqlMap.putAll(
                        //生成update语句
                        UpdateSqlUtil.createUpdateSql(tableCode, sqlMap)
                );
            }
            //delete
            else if("delete".equals(sqlType)){
                countSqlMap.putAll(
                        //生成update语句
                        DeleteSqlUtil.createDeleteSql(tableCode, sqlMap)
                );
            }
            //select
            else if("select".equals(sqlType)){

            }
            //InsertOrUpdate,符合条件更新，不符合条件插入
            else if("InsertOrUpdate".equals(sqlType)){

            }
            //UpdateOrInsert,先更新，更新0条就插入
            else if("UpdateOrInsert".equals(sqlType)){

            }
        }
        return countSqlMap;
    }


    /**
     *
     * @param myBatisTableMap
     * @param tableColumnCode
     * @param columnAliasCode
     * @param myBatisTableId
     */
    private void setMyBatisTableMap(Map<Integer, MyBatisTable> myBatisTableMap,
                                    String tableColumnCode,
                                    String columnAliasCode, int myBatisTableId) throws Exception {
        MyBatisTable myBatisTable = myBatisTableMap.get(myBatisTableId);
        if(myBatisTable == null){
            myBatisTable = new MyBatisTable();
        }

        Map<String, MyBatisTableColumn> tableDatas = myBatisTable.getTableDatas();
        if(tableDatas == null || tableDatas.size() == 0){
            tableDatas = new HashMap<String, MyBatisTableColumn>();
        }

        MyBatisTableColumn myBatisTableColumn = tableDatas.get(tableColumnCode);
        if(myBatisTableColumn == null){
            myBatisTableColumn = new MyBatisTableColumn();
        }

        myBatisTableColumn.setTableColumnCode(tableColumnCode);
        myBatisTableColumn.setColumnAliasCode(columnAliasCode);
        //这里有问题
//        myBatisTableColumn.setColumnValue(req.getReqDataStr(columnAliasCode));

        tableDatas.put(tableColumnCode, myBatisTableColumn);
        myBatisTable.setTableDatas(tableDatas);
        myBatisTable.setId(-1);
        //这里有问题
//        if(req.getReqDataStr(columnAliasCode) == null){
//            myBatisTable.setEffectValue(0);
//        }else{
//            myBatisTable.setEffectValue(1);
//        }

        myBatisTableMap.put(myBatisTableId, myBatisTable);
    }

    /**
     *
     * @param myBatisTableMap
     * @param tableColumnCode
     * @param requestMap
     * @param columnAliasCode
     * @param myBatisTableId
     */
    private void setMyBatisTableMap(Map<Integer, MyBatisTable> myBatisTableMap,
                                    String tableColumnCode, Map<String, Object> requestMap,
                                    String columnAliasCode, int myBatisTableId) throws Exception {
        MyBatisTable myBatisTable = myBatisTableMap.get(myBatisTableId);
        if(myBatisTable == null){
            myBatisTable = new MyBatisTable();
        }

        Map<String, MyBatisTableColumn> tableDatas = myBatisTable.getTableDatas();
        if(tableDatas == null || tableDatas.size() == 0){
            tableDatas = new HashMap<String, MyBatisTableColumn>();
        }

        MyBatisTableColumn myBatisTableColumn = tableDatas.get(tableColumnCode);
        if(myBatisTableColumn == null){
            myBatisTableColumn = new MyBatisTableColumn();
        }

        myBatisTableColumn.setTableColumnCode(tableColumnCode);
        myBatisTableColumn.setColumnAliasCode(columnAliasCode);
        myBatisTableColumn.setColumnValue(requestMap.get(columnAliasCode));

        tableDatas.put(tableColumnCode, myBatisTableColumn);
        myBatisTable.setTableDatas(tableDatas);
        myBatisTable.setId(-1);
        if(requestMap.get(columnAliasCode) == null){
            myBatisTable.setEffectValue(0);
        }else{
            myBatisTable.setEffectValue(1);
        }

        myBatisTableMap.put(myBatisTableId, myBatisTable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchExcuteSql(List<SysInterfaceDataSql> sqlList) throws Exception {
        Map<String, Integer> idMap = new HashMap<String, Integer>();
        for(SysInterfaceDataSql sysInterfaceDataSql : sqlList){
            String sqlBatchId = sysInterfaceDataSql.getSqlBatchId();
            String sql = sysInterfaceDataSql.getSql();
            String key = "sql_"+sqlBatchId+"_id";
            for(int i = 0; i < BaseUtil.returnInt(sqlBatchId); i++){
                String replaceKey = "sql_"+i+"_id";
                if(idMap.get(replaceKey) != null){
                    sql = sql.replace("#{" + replaceKey + "}", BaseUtil.returnString(idMap.get(replaceKey)));
                }
            }
            if(sql.indexOf("insert") == 0){
                sysInterfaceDataSql.setSql(sql);
                int count = sysInterfaceDataSqlMapper.insertBySql(sysInterfaceDataSql);
                idMap.put(key, sysInterfaceDataSql.getId());
            }
            else if(sql.indexOf("update") == 0){
                sysInterfaceDataSqlMapper.updateBySql(sysInterfaceDataSql);
            }
            else if(sql.indexOf("delete") == 0){
                sysInterfaceDataSqlMapper.deleteBySql(sysInterfaceDataSql);
            }
        }
    }

}
