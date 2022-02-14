package com.generator.service.sysInterfaceDataSql;

import java.util.Map;

/**
 * @author mjh
 * 根据数据生成的sql的可执行性校验（判断该sql要不要被执行）
 */
public interface SysInterfaceDataSqlValidityService {

    /**
     * 校验sql的可执行性
     * @param countSqlMap key : 行号， value : sql;
     * @param conditionId 所属条件id
     * @throws Exception
     */
    public void validityExcuteSqlCondition(Map<String, String> countSqlMap, String conditionId, String conditionValue, Map<String, Object> requestMap) throws Exception;

}
