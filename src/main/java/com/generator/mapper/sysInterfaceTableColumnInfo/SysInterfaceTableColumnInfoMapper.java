package com.generator.mapper.sysInterfaceTableColumnInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.generator.bean.sysInterfaceTableColumnInfo.SysInterfaceTableColumnInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SysInterfaceTableColumnInfoMapper extends BaseMapper<SysInterfaceTableColumnInfo> {

    List<Map<String, Object>> queryCurrentInterfaceBatchList(Map<String, Object> paramMap);

    List<SysInterfaceTableColumnInfo> querySysInterfaceTableColumnInfoList(Map<String, Object> paramMap);

    void insertSysInterfaceTableColumnInfoList(List<SysInterfaceTableColumnInfo> sysInterfaceTableColumnInfoList);

    void deleteSysInterfaceTableColumnInfo(Map<String, Object> paramMap);
}
