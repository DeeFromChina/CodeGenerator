package com.generator.service.sysInterfaceInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.generator.bean.sysInterfaceInfo.SysInterfaceInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface SysInterfaceInfoService extends IService<SysInterfaceInfo> {

    public SysInterfaceInfo querySysInterfaceInfo(SysInterfaceInfo sysInterfaceInfo) throws Exception;

    public SysInterfaceInfo querySysInterfaceInfo(Map<String, Object> paramMap) throws Exception;

    public void insertSysInterfaceInfo(SysInterfaceInfo sysInterfaceInfo) throws Exception;

    public void updateSysInterfaceInfo(SysInterfaceInfo sysInterfaceInfo) throws Exception;

    public void dealInterfaceRequest(Map<String, Object> paramMap) throws Exception;

    public void dataFromExcel(MultipartFile file) throws Exception;

    public void initSingleTalbeTemplateToExcel(String tableName) throws Exception;

    public void initMultipleTablesTemplateToExcel(String mainTableCode, String subTables) throws Exception;

}
