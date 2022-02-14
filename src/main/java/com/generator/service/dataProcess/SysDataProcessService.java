package com.generator.service.dataProcess;


import com.generator.bean.sysInterfaceColumnRule.SysInterfaceColumnRule;

import java.util.List;
import java.util.Map;

public interface SysDataProcessService {

    public Map<String, Object> processData(List<SysInterfaceColumnRule> sysInterfaceColumnRuleList, Map<String, Object> paramMap) throws Exception;

}
