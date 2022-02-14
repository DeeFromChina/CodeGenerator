package com.generator.service.sysInterfaceDataSql.impl;

import com.dee.frame.springbootframe.util.RegularUtil;
import com.dee.frame.springbootframe.util.common.BaseUtil;
import com.dee.frame.springbootframe.util.jexl.SqlParams;
import com.generator.service.sysInterfaceDataSql.SysInterfaceDataSqlValidityService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sysInterfaceDataSqlValidityService")
public class SysInterfaceDataSqlValidityServiceImpl implements SysInterfaceDataSqlValidityService {

    @Override
    public void validityExcuteSqlCondition(Map<String, String> countSqlMap,
                                           String conditionId, String conditionValue, Map<String, Object> requestMap) throws Exception {
        Map<String, String> countSqlMap2 = new HashMap<String, String>();
        countSqlMap2.putAll(countSqlMap);
        if(conditionValue.indexOf("foreach") > -1){
            List<String> msgs = RegularUtil.outputMsgs(conditionValue, "foreach", RegularUtil.BRACES);
            if(msgs != null && msgs.size() > 0){
                String msg = msgs.get(0);
                for(Map.Entry<String, String> entry : countSqlMap2.entrySet()){
                    String conditionKey = msg + entry.getKey();
                    if("".equals(BaseUtil.returnString(requestMap.get(conditionKey)))){
                        conditionValue = conditionValue.replace("foreach{"+msg+"}", "null");
                    }else{
                        conditionValue = conditionValue.replace("foreach{"+msg+"}", BaseUtil.returnString(requestMap.get(conditionKey)));
                    }
                    System.out.println("sqlCondition:"+conditionValue);
                    String flag = BaseUtil.returnString(SqlParams.convertToCode(conditionValue, null));
                    if("false".equals(flag)){
                        countSqlMap.remove(entry.getKey());
                    }
                }
            }
        }
    }


}
