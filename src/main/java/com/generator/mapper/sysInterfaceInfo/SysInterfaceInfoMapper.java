package com.generator.mapper.sysInterfaceInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.generator.bean.sysInterfaceInfo.SysInterfaceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface SysInterfaceInfoMapper extends BaseMapper<SysInterfaceInfo> {

    SysInterfaceInfo querySysInterfaceInfo(SysInterfaceInfo sysInterfaceInfo);

    SysInterfaceInfo querySysInterfaceInfo(Map<String, Object> paramMap);

    void insertSysInterfaceInfo(SysInterfaceInfo sysInterfaceInfo);

    void updateSysInterfaceInfo(SysInterfaceInfo sysInterfaceInfo);
}
