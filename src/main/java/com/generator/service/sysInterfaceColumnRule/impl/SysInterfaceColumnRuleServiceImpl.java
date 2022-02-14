package com.generator.service.sysInterfaceColumnRule.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.generator.bean.sysInterfaceColumnRule.SysInterfaceColumnRule;
import com.generator.mapper.sysInterfaceColumnRule.SysInterfaceColumnRuleMapper;
import com.generator.service.sysInterfaceColumnRule.SysInterfaceColumnRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("sysInterfaceColumnRuleService")
public class SysInterfaceColumnRuleServiceImpl extends ServiceImpl<SysInterfaceColumnRuleMapper, SysInterfaceColumnRule> implements SysInterfaceColumnRuleService {

    @Autowired
    private SysInterfaceColumnRuleMapper sysInterfaceColumnRuleMapper;

    @Override
    public List<SysInterfaceColumnRule> querySysInterfaceColumnRuleList(Map<String, Object> paramMap) throws Exception {
        return sysInterfaceColumnRuleMapper.querySysInterfaceColumnRuleList(paramMap);
    }

    @Override
    public void insertSysInterfaceColumnRuleList(List<SysInterfaceColumnRule> sysInterfaceColumnRuleList) throws Exception{
        sysInterfaceColumnRuleMapper.insertSysInterfaceColumnRuleList(sysInterfaceColumnRuleList);
    }

    @Override
    public void deleteSysInterfaceColumnRule(Map<String, Object> paramMap) throws Exception {
        sysInterfaceColumnRuleMapper.deleteSysInterfaceColumnRule(paramMap);
    }
}
