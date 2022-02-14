package com.generator.service.databaseTable;

import java.util.List;
import java.util.Map;

public interface DatabaseTableColumnService {

    public List<Map<String, Object>> queryDatabaseTableColumnListMap(Map<String, Object> paramMap) throws Exception;

}
