package com.generator.mapper.sysInterfaceTableInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.generator.bean.sysInterfaceTableInfo.SysInterfaceTableInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SysInterfaceTableInfoMapper extends BaseMapper<SysInterfaceTableInfo> {

    void insertSysInterfaceTableInfoList(List<SysInterfaceTableInfo> sysInterfaceTableInfoList);

    void deleteSysInterfaceTableInfo(Map<String, Object> paramMap);
}
