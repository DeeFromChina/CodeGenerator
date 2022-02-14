package com.generator.controller.sysInterfaceInfo;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.dee.frame.springbootframe.controller.BaseController;
import com.dee.frame.springbootframe.service.sysRequestDataInfo.SysRequestDataInfoService;
import com.dee.frame.springbootframe.util.common.BaseUtil;
import com.generator.bean.sysInterfaceInfo.SysInterfaceInfo;
import com.generator.service.sysInterfaceColumnRule.SysInterfaceColumnRuleService;
import com.generator.service.sysInterfaceInfo.SysInterfaceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sysInterfaceInfo")
public class SysInterfaceInfoController extends BaseController {

    @Autowired
    private SysInterfaceInfoService sysInterfaceInfoService;

    @Autowired
    private SysInterfaceColumnRuleService sysInterfaceColumnRuleService;

    @Autowired
    private SysRequestDataInfoService sysRequestDataInfoService;

    @PostMapping("/querySysReportSql")
    public Map<String, Object> querySysReportSql(HttpServletRequest request, @RequestBody String data) {
        try{
            setMap(request, data);

            LambdaUpdateWrapper<SysInterfaceInfo> lambdaUpdateWrapper = new LambdaUpdateWrapper<SysInterfaceInfo>();
            lambdaUpdateWrapper.eq(SysInterfaceInfo::getId, "").set(SysInterfaceInfo::getApiId, "");
            sysInterfaceInfoService.update(null, lambdaUpdateWrapper);
            sysInterfaceInfoService.dealInterfaceRequest(form);
            return toJson(SUCCESS);
        }catch(Exception e){
            e.printStackTrace();
            return toJson(ERROR);
        }
    }

    @PostMapping("/dataFromExcel")
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> dataFromExcel(@RequestBody MultipartFile file) {
        try{
            sysInterfaceInfoService.dataFromExcel(file);
            return toJson(SUCCESS);
        }catch(Exception e){
            e.printStackTrace();
            return toJson(ERROR);
        }
    }


    @PostMapping("/initInterfaceTemplateToExcel")
    public Map<String, Object> initInterfaceTemplateToExcel(HttpServletRequest request, @RequestBody String data) {
        try{
            setMap(request, data);
            String tempCode = BaseUtil.returnString(form.get("tempCode"));
            //单表
            if(tempCode.equals("singleTable")){
                String tableName = BaseUtil.returnString(form.get("tableName"));
                sysInterfaceInfoService.initSingleTalbeTemplateToExcel(tableName);
            }
            //多表
            else if(tempCode.equals("multipleTables")){
                String mainTableCode = BaseUtil.returnString(form.get("mainTableCode"));
                String subTables = BaseUtil.returnString(form.get("subTables"));
                String[] subTablesArray = subTables.split(",");
                sysInterfaceInfoService.initMultipleTablesTemplateToExcel(mainTableCode, subTables);
            }
            return toJson(SUCCESS);
        }catch(Exception e){
            e.printStackTrace();
            return toJson(ERROR);
        }
    }

    @PostMapping("/temp")
    public ModelAndView temp(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageName", "test1");
        mv.addObject("docTitle", "test2");
        mv.addObject("createUser", "test3");
        mv.addObject("lastUpdateTime", "test4");
        mv.setViewName("");
        return mv;
    }

    @PostMapping("/temp2")
    public String temp2(Model model) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageName", "接口文档");
        map.put("docTitle", "xx接口文档");
        map.put("createUser", "莫景华");
        map.put("lastUpdateTime", "2021-11-23");
        List<Map<String, Object>> titles = new ArrayList<Map<String, Object>>();
        Map<String, Object> title = new HashMap<String, Object>();
        title.put("id", "1");
        title.put("name", "目录1");
        titles.add(title);
        map.put("titles", titles);
        model.addAllAttributes(map);
        return "temp";
    }
}
