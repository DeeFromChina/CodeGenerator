package com.generator.service.sysInterfaceTableInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.generator.bean.sysInterfaceTableInfo.SysInterfaceTableInfo;
import com.generator.mapper.sysInterfaceTableInfo.SysInterfaceTableInfoMapper;
import com.generator.service.sysInterfaceTableInfo.SysInterfaceTableInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("sysInterfaceTableInfoService")
public class SysInterfaceTableInfoServiceImpl extends ServiceImpl<SysInterfaceTableInfoMapper, SysInterfaceTableInfo> implements SysInterfaceTableInfoService {

    @Autowired
    private SysInterfaceTableInfoMapper sysInterfaceTableInfoMapper;

    @Override
    public void insertSysInterfaceTableInfoList(List<SysInterfaceTableInfo> sysInterfaceTableInfoList) throws Exception {
        sysInterfaceTableInfoMapper.insertSysInterfaceTableInfoList(sysInterfaceTableInfoList);
    }

    @Override
    public void deleteSysInterfaceTableInfo(Map<String, Object> paramMap) throws Exception {
        sysInterfaceTableInfoMapper.deleteSysInterfaceTableInfo(paramMap);
    }
}
