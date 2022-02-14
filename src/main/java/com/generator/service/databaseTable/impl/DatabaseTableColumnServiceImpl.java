package com.generator.service.databaseTable.impl;

import com.generator.mapper.DatabaseTableColumnMapper;
import com.generator.service.databaseTable.DatabaseTableColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("databaseTableColumnService")
public class DatabaseTableColumnServiceImpl implements DatabaseTableColumnService {

    @Autowired
    private DatabaseTableColumnMapper databaseTableColumnMapper;

    @Override
    public List<Map<String, Object>> queryDatabaseTableColumnListMap(Map<String, Object> paramMap) throws Exception {
        paramMap.put("databaseName", "test");
        List<Map<String, Object>> list = databaseTableColumnMapper.queryDatabaseTableColumnListMap(paramMap);
        return list;
    }
}
