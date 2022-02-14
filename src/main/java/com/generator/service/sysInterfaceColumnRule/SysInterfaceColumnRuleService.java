package com.generator.service.sysInterfaceColumnRule;


import com.baomidou.mybatisplus.extension.service.IService;
import com.generator.bean.sysInterfaceColumnRule.SysInterfaceColumnRule;

import java.util.List;
import java.util.Map;

public interface SysInterfaceColumnRuleService extends IService<SysInterfaceColumnRule> {

    public List<SysInterfaceColumnRule> querySysInterfaceColumnRuleList(Map<String, Object> paramMap) throws Exception;

    public void insertSysInterfaceColumnRuleList(List<SysInterfaceColumnRule> sysInterfaceColumnRuleList) throws Exception;

    public void deleteSysInterfaceColumnRule(Map<String, Object> paramMap) throws Exception;

}
