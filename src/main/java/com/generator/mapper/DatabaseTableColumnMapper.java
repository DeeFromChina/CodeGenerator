package com.generator.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface DatabaseTableColumnMapper {

    List<Map<String, Object>> queryDatabaseTableColumnListMap(Map<String, Object> paramMap);

}
