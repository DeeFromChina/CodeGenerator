package com.generator.service.codeGenerator;

import com.generator.bean.codeGenerator.ControllerMethod;
import com.generator.bean.codeGenerator.ProjectConfig;
import com.generator.bean.sysInterfaceInfo.SysInterfaceInfo;
import com.generator.bean.sysModularTable.SysModularTable;

import java.util.Map;

public interface CodeGeneratorService {

    public ControllerMethod loadMethodParam(ProjectConfig projectConfig, SysInterfaceInfo sysInterfaceInfo) throws Exception;

    public void loadEntityParam(ProjectConfig projectConfig, SysInterfaceInfo sysInterfaceInfo, Map<String, SysModularTable> sysModularTableMap) throws Exception;

}
