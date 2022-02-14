package com.generator.mapper.sysInterfaceDataSql;

import com.generator.bean.sysInterfaceDataSql.SysInterfaceDataSql;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysInterfaceDataSqlMapper {

    Integer insertBySql(SysInterfaceDataSql sysInterfaceDataSql);

    void updateBySql(SysInterfaceDataSql sysInterfaceDataSql);

    void deleteBySql(SysInterfaceDataSql sysInterfaceDataSql);

}
