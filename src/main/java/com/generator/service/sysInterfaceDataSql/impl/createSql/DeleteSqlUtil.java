package com.generator.service.sysInterfaceDataSql.impl.createSql;


import com.dee.frame.springbootframe.util.common.BaseUtil;

import java.util.HashMap;
import java.util.Map;

public class DeleteSqlUtil {

    /**
     * 生成delete语句
     * @param tableCode 表编码
     * @param sqlMap 生成Sql的参数 key行号。value-key=isEffect,有效的数据；value-key=columnParam,表字段参数;value-key=paramValue,值参数
     * @throws Exception
     */
    public static Map<String, String> createDeleteSql(String tableCode, Map<Integer, Map<String, Object>> sqlMap) throws Exception {
        Map<String, String> countSqlMap = new HashMap<String, String>();
        for(Map.Entry<Integer, Map<String, Object>> entry : sqlMap.entrySet()){
            String sqlCondition = BaseUtil.returnString(entry.getValue().get("sqlCondition"));
            StringBuffer sql = new StringBuffer();
            sql.append("delete from ");
            sql.append(tableCode);
            if(!sqlCondition.equals("")){
                sql.append(" ");
                sql.append(sqlCondition);
            }
            sql.append(" ; ");
            countSqlMap.put(entry.getKey().toString(), sql.toString());
        }
        return countSqlMap;
    }

}
