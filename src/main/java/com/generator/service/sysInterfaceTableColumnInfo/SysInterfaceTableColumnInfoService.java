package com.generator.service.sysInterfaceTableColumnInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.generator.bean.sysInterfaceTableColumnInfo.SysInterfaceTableColumnInfo;

import java.util.List;
import java.util.Map;

public interface SysInterfaceTableColumnInfoService extends IService<SysInterfaceTableColumnInfo> {

    public List<Map<String, Object>> queryCurrentInterfaceBatchList(Map<String, Object> paramMap) throws Exception;

    public List<SysInterfaceTableColumnInfo> querySysInterfaceTableColumnInfoList(Map<String, Object> paramMap) throws Exception;

    public void insertSysInterfaceTableColumnInfoList(List<SysInterfaceTableColumnInfo> sysInterfaceTableColumnInfoList) throws Exception;

    public void deleteSysInterfaceTableColumnInfo(Map<String, Object> paramMap) throws Exception;
}
