package com.generator.service.dataProcess.impl;

import com.dee.frame.springbootframe.util.RegularUtil;
import com.generator.bean.codeGenerator.ProcessClass;
import com.generator.bean.codeGenerator.ProcessClassField;
import com.generator.bean.codeGenerator.VoClassField;
import com.generator.processRule.mapper.ProcessRuleMapper;
import com.generator.service.dataProcess.CodeDataProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CodeDataProcessServiceImpl implements CodeDataProcessService {

    @Autowired
    private ProcessRuleMapper processRuleMapper;

    @Override
    public String processData(ProcessClassField processClassField, Map<String, String> processRuleClass) throws Exception {
        if(processClassField.getProcessingRule() == null){
            return null;
        }
        Map<String, String> processRule = processRuleMapper.getRule();
        if(processClassField.getProcessingRule().equals("currentDate")){
            processClassField.setProcessingRule("new Date()");
            processRuleClass.put("Date", "java.util.Date");
        }else if(processClassField.getProcessingRule().indexOf("constant[") == 0){
            List<String> list = RegularUtil.outputMsgs(processClassField.getProcessingRule(), "constant", RegularUtil.SQUARE_BRACKETS);
            processClassField.setProcessingRule("\""+list.get(0)+"\"");
        }else if(processRule.get(processClassField.getProcessingRule()) != null){
            processClassField.setProcessingRule("commonProcess."+processRule.get(processClassField.getProcessingRule()));
        }else{

        }
        return null;
    }
}
