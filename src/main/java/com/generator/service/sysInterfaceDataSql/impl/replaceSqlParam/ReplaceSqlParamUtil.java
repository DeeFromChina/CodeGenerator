package com.generator.service.sysInterfaceDataSql.impl.replaceSqlParam;


import com.dee.frame.springbootframe.util.RegularUtil;
import com.dee.frame.springbootframe.util.common.BaseUtil;

import java.util.List;
import java.util.Map;

public class ReplaceSqlParamUtil {

    public static String repalceParam(String sql, Map<String, Object> paramMap) throws Exception {
        List<String> octothorpeList = RegularUtil.outputMsgs(sql, "#", RegularUtil.BRACES);
        for(String param : octothorpeList){
            String value = BaseUtil.returnString(paramMap.get(param));
            sql = sql.replace("#{"+param+"}", "'" +value+"'");
        }
        return sql;
    }

}
