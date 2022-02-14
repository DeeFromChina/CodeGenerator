package com.generator.service.sysInterfaceDataSql.impl.createSql;


import com.dee.frame.springbootframe.util.common.BaseUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateSqlUtil {

    /**
     * 生成update语句
     * @param tableCode 表编码
     * @param sqlMap 生成Sql的参数 key行号。value-key=isEffect,有效的数据；value-key=columnParam,表字段参数;value-key=paramValue,值参数
     * @throws Exception
     */
    public static Map<String, String> createUpdateSql(String tableCode, Map<Integer, Map<String, Object>> sqlMap) throws Exception {
        Map<String, String> countSqlMap = new HashMap<String, String>();
        for(Map.Entry<Integer, Map<String, Object>> entry : sqlMap.entrySet()){
            List<String> columnParamList = (List<String>) entry.getValue().get("columnParam");
            List<String> paramValueList = (List<String>) entry.getValue().get("paramValue");
            String sqlCondition = BaseUtil.returnString(entry.getValue().get("sqlCondition"));
            StringBuffer sql = new StringBuffer();
            sql.append("update ");
            sql.append(tableCode);
            sql.append(" set ");
            for(int i = 0; i < columnParamList.size(); i++){
                String columnParam = columnParamList.get(i);
                String paramValue = paramValueList.get(i);
                if(i > 0){
                    sql.append(",");
                }
                sql.append(columnParam);
                sql.append(" = ");
                sql.append("'");
                sql.append(paramValue);
                sql.append("'");
            }
            if(!sqlCondition.equals("")){
                sql.append(sqlCondition);
            }
            sql.append(" ; ");
            countSqlMap.put(entry.getKey().toString(), sql.toString());
        }
        return countSqlMap;
    }

}
