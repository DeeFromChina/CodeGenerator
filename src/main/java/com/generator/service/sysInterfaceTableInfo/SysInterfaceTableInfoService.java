package com.generator.service.sysInterfaceTableInfo;


import com.baomidou.mybatisplus.extension.service.IService;
import com.generator.bean.sysInterfaceTableInfo.SysInterfaceTableInfo;

import java.util.List;
import java.util.Map;

public interface SysInterfaceTableInfoService extends IService<SysInterfaceTableInfo> {

    public void insertSysInterfaceTableInfoList(List<SysInterfaceTableInfo> sysInterfaceTableInfoList) throws Exception;

    public void deleteSysInterfaceTableInfo(Map<String, Object> paramMap) throws Exception;
}
