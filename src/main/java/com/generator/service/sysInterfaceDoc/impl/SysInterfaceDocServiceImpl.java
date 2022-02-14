package com.generator.service.sysInterfaceDoc.impl;

import com.generator.service.sysInterfaceDoc.SysInterfaceDocService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("sysInterfaceDocService")
public class SysInterfaceDocServiceImpl implements SysInterfaceDocService {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void test() throws Exception {
        String apiModular = "";

        Map<String, Object> docMap = new HashMap<String, Object>();
        docMap.put("pageName", apiModular+"接口文档");
        docMap.put("docTitle", apiModular+"接口文档");
        docMap.put("createUser", "莫景华");
        docMap.put("lastUpdateTime", sdf.format(new Date()));

        List<Map<String, Object>> titles = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> apis = new ArrayList<Map<String, Object>>();



        List<Map<String, Object>> list = null;//查询该模块名下的接口信息
        for(Map<String, Object> map : list){
            Map<String, Object> title = new HashMap<String, Object>();
            title.put("id", map.get("id"));
            title.put("name", map.get("name"));
            titles.add(title);

            Map<String, Object> api = new HashMap<String, Object>();
            api.put("id", map.get("id"));
            api.put("name", map.get("name"));
            api.put("url", map.get("url"));

            List<Map<String, Object>> columnList = null;//查询该接口的字段信息
            List<Map<String, Object>> requestsList = new ArrayList<Map<String, Object>>();
            for(Map<String, Object> column : columnList){
                Map<String, Object> requestMap = new HashMap<String, Object>();
                requestMap.put("fieldCode", column.get("fieldCode"));
                requestMap.put("isRequired", column.get("isRequired"));
                requestMap.put("fieldName", column.get("fieldName"));
                requestMap.put("desc", column.get("desc"));
                requestsList.add(requestMap);
            }

            api.put("requests", requestsList);
            apis.add(api);

        }
    }

}
