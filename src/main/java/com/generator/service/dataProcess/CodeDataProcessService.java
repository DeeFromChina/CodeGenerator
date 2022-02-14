package com.generator.service.dataProcess;

import com.generator.bean.codeGenerator.ProcessClass;
import com.generator.bean.codeGenerator.ProcessClassField;
import com.generator.bean.codeGenerator.VoClassField;

import java.util.Map;

public interface CodeDataProcessService {

    public String processData(ProcessClassField processClassField, Map<String, String> processRuleClass) throws Exception;

}
