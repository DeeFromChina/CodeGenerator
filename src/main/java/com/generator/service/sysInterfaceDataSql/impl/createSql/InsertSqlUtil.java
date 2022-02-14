package com.generator.service.sysInterfaceDataSql.impl.createSql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsertSqlUtil {

    /**
     * 生成insert语句
     * @param tableCode 表编码
     * @param sqlMap 生成Sql的参数 key行号。value-key=isEffect,有效的数据；value-key=columnParam,表字段参数;value-key=paramValue,值参数
     * @throws Exception
     */
    public static Map<String, String> createInsertSql(String tableCode, Map<Integer, Map<String, Object>> sqlMap) throws Exception {
        //存储sql的map数组，key是行数，value是sql语句（不是多行key为-1）
        Map<String, String> countSqlMap = new HashMap<String, String>();
        for(Map.Entry<Integer, Map<String, Object>> entry : sqlMap.entrySet()){
            //将公共参数取出
            Map<String, Object> paramMap = entry.getValue();
            StringBuffer columnParamBuffer = new StringBuffer();
            StringBuffer paramValueBuffer = new StringBuffer();
            List<String> columnParamList = (List<String>) paramMap.get("columnParam");
            List<String> paramValueList = (List<String>) paramMap.get("paramValue");
            int i = 0;
            for(String columnParam : columnParamList){
                if(i > 0){
                    columnParamBuffer.append(",");
                }
                columnParamBuffer.append(columnParam);
                i++;
            }
            i = 0;
            for(String paramValue : paramValueList){
                if(i > 0){
                    paramValueBuffer.append(",");
                }
                paramValueBuffer.append("'");
                paramValueBuffer.append(paramValue);
                paramValueBuffer.append("'");
                i++;
            }
            //拼接insert语句
            mosaicInsertSql(columnParamBuffer, paramValueBuffer, entry.getKey().toString(), tableCode, countSqlMap);
        }
        return countSqlMap;
    }

    /**
     * 拼接insert语句
     * @param columnParamBuffer
     * @param paramValueBuffer
     * @param sqlId
     * @param tableCode
     * @throws Exception
     */
    private static void mosaicInsertSql(StringBuffer columnParamBuffer, StringBuffer paramValueBuffer, String sqlId, String tableCode, Map<String, String> countSqlMap) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ");
        sql.append(tableCode);
        sql.append(" (");
        sql.append(columnParamBuffer.toString());
        sql.append(" ) ");
        sql.append("values (");
        sql.append(paramValueBuffer.toString());
        sql.append(" ); ");
        countSqlMap.put(sqlId, sql.toString());
    }
}
