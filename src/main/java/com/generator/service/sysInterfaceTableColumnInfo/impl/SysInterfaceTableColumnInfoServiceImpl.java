package com.generator.service.sysInterfaceTableColumnInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.generator.bean.sysInterfaceTableColumnInfo.SysInterfaceTableColumnInfo;
import com.generator.mapper.sysInterfaceTableColumnInfo.SysInterfaceTableColumnInfoMapper;
import com.generator.service.sysInterfaceTableColumnInfo.SysInterfaceTableColumnInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("sysInterfaceTableColumnInfoService")
public class SysInterfaceTableColumnInfoServiceImpl extends ServiceImpl<SysInterfaceTableColumnInfoMapper, SysInterfaceTableColumnInfo> implements SysInterfaceTableColumnInfoService {

    @Autowired
    private SysInterfaceTableColumnInfoMapper sysInterfaceTableColumnInfoMapper;

    @Override
    public List<Map<String, Object>> queryCurrentInterfaceBatchList(Map<String, Object> paramMap) throws Exception {
        return sysInterfaceTableColumnInfoMapper.queryCurrentInterfaceBatchList(paramMap);
    }

    @Override
    public List<SysInterfaceTableColumnInfo> querySysInterfaceTableColumnInfoList(Map<String, Object> paramMap) throws Exception {
        return sysInterfaceTableColumnInfoMapper.querySysInterfaceTableColumnInfoList(paramMap);
    }

    @Override
    public void insertSysInterfaceTableColumnInfoList(List<SysInterfaceTableColumnInfo> sysInterfaceTableColumnInfoList) throws Exception {
        sysInterfaceTableColumnInfoMapper.insertSysInterfaceTableColumnInfoList(sysInterfaceTableColumnInfoList);
    }

    @Override
    public void deleteSysInterfaceTableColumnInfo(Map<String, Object> paramMap) throws Exception {
        sysInterfaceTableColumnInfoMapper.deleteSysInterfaceTableColumnInfo(paramMap);
    }

}
