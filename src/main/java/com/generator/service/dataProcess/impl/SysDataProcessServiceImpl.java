package com.generator.service.dataProcess.impl;

import com.alibaba.fastjson.JSONObject;
import com.dee.frame.springbootframe.util.RegularUtil;
import com.dee.frame.springbootframe.util.common.BaseUtil;
import com.generator.bean.sysInterfaceColumnRule.SysInterfaceColumnRule;
import com.generator.service.dataProcess.SysDataProcessService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("sysDataProcessService")
public class SysDataProcessServiceImpl implements SysDataProcessService {

//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Map<String, Object> processData(List<SysInterfaceColumnRule> sysInterfaceColumnRuleList, Map<String, Object> paramMap) throws Exception {
        Map<String, Object> processMap = new HashMap<String, Object>();
        for(SysInterfaceColumnRule sysInterfaceColumnRule : sysInterfaceColumnRuleList){
            //页面字段id
            String pageColumnCode = BaseUtil.returnString(sysInterfaceColumnRule.getPageColumnCode());
            //加工规则
            String processingRule = BaseUtil.returnString(sysInterfaceColumnRule.getProcessingRule());
            //字段别名
            String columnAliasCode = BaseUtil.returnString(sysInterfaceColumnRule.getColumnAliasCode());
            //父级节点
            String fatherNodeCode = BaseUtil.returnString(sysInterfaceColumnRule.getFatherNodeCode());

            //如果是有父级节点，则将子表中的字段都做一次别名转换
            if(!"".equals(fatherNodeCode)){
                List dataList = (List) paramMap.get(fatherNodeCode);
                List<Map<String, Object>> subDataList = new ArrayList<Map<String, Object>>();
                if(processMap.get(fatherNodeCode) != null){
                    subDataList = (List) processMap.get(fatherNodeCode);
                }
                for(int i = 0; i < dataList.size(); i++){
                    JSONObject o = (JSONObject) dataList.get(i);
                    Object obj = o.get(pageColumnCode);
                    Map<String, Object> map = new HashMap<String, Object>();
                    if(subDataList.size() >= i+1){
                        map = subDataList.get(i);
                        map.put(columnAliasCode, obj);
                    }else{
                        map.put(columnAliasCode, obj);
                        subDataList.add(map);
                    }
                }
                processMap.put(fatherNodeCode, subDataList);
            }
            else{
                //当前时间
                if("currentDate".equals(processingRule)){
                    processMap.put(columnAliasCode, sdf.format(new Date()));
                }
                //当前使用id
                else if("currentUserId".equals(processingRule)){
                    Integer keyValue = 1;
                    processMap.put(columnAliasCode, keyValue);
                }
                //常量
                else if(processingRule.indexOf("constant") == 0){
                    List<String> list = RegularUtil.outputMsgs(processingRule, "constant", RegularUtil.SQUARE_BRACKETS);
                    if(list != null && list.size() > 0){
                        String keyValue = list.get(0);
                        processMap.put(columnAliasCode, keyValue);
                    }
                }else{
                    processMap.put(columnAliasCode, paramMap.get(pageColumnCode));
                }
            }
        }
        return processMap;
    }

    /**
     * 根据规则做数据加工
     */
    public void dataProcessByRule() {

    }

    /**
     * 根据业务代码做数据加工
     */
    public void dataProcessByMethod() {

    }


}
