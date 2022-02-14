package com.generator.service.dataValidation.impl;

import com.dee.frame.springbootframe.util.common.BaseUtil;
import com.generator.bean.sysInterfaceColumnRule.SysInterfaceColumnRule;
import com.generator.service.dataProcess.SysDataProcessService;
import com.generator.service.dataValidation.SysDataValidationService;
import com.generator.service.dataValidation.util.SysDataValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("sysDataValidationService")
public class SysDataValidationServiceImpl implements SysDataValidationService {

//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SysDataProcessService sysDataProcessService;

    private Map<String, Object> pageMap;

    private Map<String, Object> dataMap;

    @Override
    public boolean validationData(SysInterfaceColumnRule sysInterfaceColumnRule, Object pageColumnValue) throws Exception {
        //是否必填
        Integer isRequired = BaseUtil.returnInt(sysInterfaceColumnRule.getIsRequired());
        //字段最大长度
        Integer maxLength = BaseUtil.returnInt(sysInterfaceColumnRule.getMaxLength());
        //字段类型
        String pageColumnType = BaseUtil.returnString(sysInterfaceColumnRule.getPageColumnType());
        //校验规则
        String validateRule = BaseUtil.returnString(sysInterfaceColumnRule.getValidateRule());

        boolean isPass = false;
        //判断是否必填
        isPass = SysDataValidationUtil.checkIsRequired(isRequired, pageColumnValue);

        //判断字段最大长度
        isPass = SysDataValidationUtil.checkMaxLength(maxLength, pageColumnValue);

        //字段类型处理方式
        isPass = SysDataValidationUtil.checkPageColumnType(pageColumnType, pageColumnValue);

        //校验规则处理
//        isPass = SysDataValidationUtil.checkValidateRule(stringRedisTemplate, validateRule, pageColumnValue);
        return isPass;
    }














    public Map<String, Object> getPageMap() {
        return this.pageMap;
    }

    /*
     * validateKeyValue 校验业务字典
     */

//    public String handleData2(String pageColumnCode, String isRequired,
//                              Integer maxLength, String pageColumnType,
//                              String belongRuleGroupCode, String pageColumnValue)
//            throws Exception {
//        String afterValue = checkBelongRuleGroupCode(belongRuleGroupCode, pageColumnValue);
//        return afterValue;
//    }

    /**
     * 处理数据
     * @param pageColumnCode 页面字段id
     * @param isRequired 是否必填
     * @param maxLength 字段最大长度
     * @param pageColumnType 字段类型
     * @param belongRuleGroupCode 使用的规则组code
     * @param pageColumnValue 页面字段值
     * @param countNum 行号
     * @return
     * @throws Exception
     */
    public void handleData(String pageColumnCode, String isRequired,
                             Integer maxLength, String pageColumnType,
                             String belongRuleGroupCode, String pageColumnValue,
                             int countNum)
            throws Exception {
        boolean isPass = false;
        //判断是否必填
//        isPass = checkIsRequired(BaseUtil.returnInt(isRequired), pageColumnValue);
//        if(!isPass){
////			CommonException.setError(req, commonDao, "300");//需要必填
//            throw new Exception("300");
//        }

        //判断字段最大长度
//        isPass = checkMaxLength(maxLength, pageColumnValue);
//        if(!isPass){
////			CommonException.setError(req, commonDao, "400");//超过最大长度
//            throw new Exception("400");
//        }

        //字段类型处理方式
//        isPass = checkPageColumnType(pageColumnType, pageColumnValue, countNum);
//        if(!isPass){
////			CommonException.setError(req, commonDao, "500");//不合格式规范
//            throw new Exception("500");
//        }

        //处理规则
//        String afterValue = checkBelongRuleGroupCode(belongRuleGroupCode, pageColumnValue);
//        return afterValue;
    }




}
