package com.generator.service.sysInterfaceDataSql;


import com.generator.bean.sysInterfaceDataSql.SysInterfaceDataSql;

import java.util.List;
import java.util.Map;

public interface SysInterfaceDataSqlService {

    public List<SysInterfaceDataSql> createInterfaceSql(Integer sysInterfaceInfoId, Map<String, Object> requestMap) throws Exception;

    public void batchExcuteSql(List<SysInterfaceDataSql> sqlList) throws Exception;

}
