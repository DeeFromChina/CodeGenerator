package com.generator.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface DatabaseTableMapper {

    List<Map<String, Object>> queryDatabaseTableListMap(Map<String, Object> paramMap);

}
