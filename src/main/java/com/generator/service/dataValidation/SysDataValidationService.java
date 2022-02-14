package com.generator.service.dataValidation;


import com.generator.bean.sysInterfaceColumnRule.SysInterfaceColumnRule;

public interface SysDataValidationService {

    public void handleData(String pageColumnCode, String isRequired,
                             Integer maxLength, String pageColumnType,
                             String belongRuleGroupCode, String pageColumnValue,
                             int countNum)
            throws Exception;

    public boolean validationData(SysInterfaceColumnRule sysInterfaceColumnRule, Object pageColumnValue) throws Exception;
}
