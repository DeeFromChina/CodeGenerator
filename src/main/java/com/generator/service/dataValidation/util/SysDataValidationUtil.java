package com.generator.service.dataValidation.util;


import java.text.SimpleDateFormat;
import java.util.Map;

public class SysDataValidationUtil {

    private static Map<String, Object> pageMap;

    private static Map<String, Object> dataMap;

    /**
     * 判断是否必填
     * @param isRequired
     * @param pageColumnValue
     * @return
     * @throws Exception
     */
    public static boolean checkIsRequired(Integer isRequired, Object pageColumnValue) throws Exception {
        if(isRequired == 1){
//            if(BaseUtil.isNull(pageColumnValue)){
//				CommonException.setError("9001");//该字段需要必填
//            }
        }
        return true;
    }

    /**
     * 判断字段最大长度
     * @param maxLength
     * @param pageColumnValue
     * @return
     * @throws Exception
     */
    public static boolean checkMaxLength(Integer maxLength, Object pageColumnValue) throws Exception {
        if(maxLength <= 0){
            return false;
        }
        if(pageColumnValue instanceof String){
//            String value = BaseUtil.returnString(pageColumnValue);
//            if(value.length() > maxLength){
//                CommonException.setError("9002");//超过字段最大长度
//            }
        }
        return true;
    }

    /**
     * 字段类型处理方式
     * @param pageColumnType
     * @param pageColumnValue
     * @return
     * @throws Exception
     */
    public static boolean checkPageColumnType(String pageColumnType, Object pageColumnValue) throws Exception {
//        if(BaseUtil.isNull(pageColumnType)){
//            return true;
//        }
//        String value = BaseUtil.returnString(pageColumnValue);
        return true;
    }

//    public static boolean checkValidateRule(StringRedisTemplate stringRedisTemplate, String validateRule, Object pageColumnValue) throws Exception {
//        if(BaseUtil.isNull(validateRule)){
//            return true;
//        }
//        String value = BaseUtil.returnString(pageColumnValue);
//        //访问业务字典-key
//        if(validateRule.indexOf("para") == 0){
//            if(!validateKeyValueToKey(stringRedisTemplate, validateRule, value)) return false;
//        }
//        //访问业务字典-value
//        if(validateRule.indexOf("para_value") == 0){
//            if(!validateKeyValueToValue(validateRule, value, 0)) return false;
//        }
//        //时间格式
//        else if(validateRule.equals("yyyy-mm-dd")){
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            if(!validateDateFormat(sdf, value)) return false;
//        }
//        //地点（省市）编码
//        else if(validateRule.indexOf("addressCode") == 0){
//            if(!validateAddressCode(validateRule)) return false;
//        }
//        //地点（省市）值
//        else if(validateRule.indexOf("addressName") == 0){
//            if(!validateAddressValue(validateRule, value)) return false;
//        }
//        return true;
//    }

    /**
     * 校验业务字典-key
     * @param validateRule
     * @return
     */
//    public static boolean validateKeyValueToKey(StringRedisTemplate stringRedisTemplate, String validateRule, String pageColumnValue) throws Exception {
//        List<String> conditionList = RegularUtil.outputMsg(validateRule, "para");
//        if(conditionList != null && conditionList.size() > 0){
//            String conditionValue = conditionList.get(0);
//            String keyValue = stringRedisTemplate.opsForValue().get(conditionValue+pageColumnValue);
//            if(keyValue == null){
//                CommonException.setError("9003");//没找到该业务字典
//            }
//        }
//        return true;
//    }

    /**
     * 校验业务字典-value
     * @param pageColumnType
     * @param pageColumnValue
     * @return
     */
    public static boolean validateKeyValueToValue(String pageColumnType, String pageColumnValue, int countNum) throws Exception {
//        List<String> conditionList = RegularUtil.outputMsg(pageColumnType, "paraValue");
//        if(conditionList != null && conditionList.size() > 0){
//            String conditionValue = conditionList.get(0);
//            String[] conditions = conditionValue.split(",");
//            String keyValueOfKey = "";
//            String keyValue = InitKeyValueListener.keyValueMap.get(conditions[0], conditions[1], keyValueOfKey);
//            if(keyValue == null || !pageColumnValue.equals(keyValue)){
//                CommonException.setError("9003");//没找到该业务字典
//            }
//        }
        return true;
    }

    /**
     * 校验时间格式
     * @param dateFormat
     * @param pageColumnValue
     * @return
     */
    public static boolean validateDateFormat(SimpleDateFormat dateFormat, String pageColumnValue) throws Exception {
        try {
            dateFormat.parse(pageColumnValue);
        } catch (Exception e) {
//            CommonException.setError("9004");//不是时间格式
        }
        return true;
    }

    /**
     * 校验城市编码-key
     * @param pageColumnType
     * @return
     */
    public static boolean validateAddressCode(String pageColumnType) throws Exception {
//        List<String> conditionList = RegularUtil.outputMsg(pageColumnType, "addressCode");
//        if(conditionList != null && conditionList.size() > 0){
//            String conditionValue = conditionList.get(0);
//            String[] conditions = conditionValue.split(",");
//            String keyValue = InitKeyValueListener.addressMap.get(conditions[0], conditions[1]);
//            if(keyValue == null){
//                CommonException.setError("9004");//没找到该地点
//            }
//        }
        return true;
    }

    /**
     * 校验城市值-value
     * @param pageColumnType
     * @param pageColumnValue
     * @return
     */
    public static boolean validateAddressValue(String pageColumnType, String pageColumnValue) throws Exception {
//        List<String> conditionList = RegularUtil.outputMsg(pageColumnType, "addressName");
//        if(conditionList != null && conditionList.size() > 0){
//            String conditionValue = conditionList.get(0);
//            String[] conditions = conditionValue.split(",");
//            String keyValue = InitKeyValueListener.addressMap.get(conditions[0], conditions[1]);
//            if(keyValue == null || !pageColumnValue.equals(keyValue)){
//                CommonException.setError("9004");//没找到该地点
//            }
//        }
        return true;
    }

    /**
     * 处理规则
     * @param belongRuleGroupCode
     * @return
     * @throws Exception
     */
//    private String checkBelongRuleGroupCode(String belongRuleGroupCode, String pageColumnValue) throws Exception {
//        if(!BaseUtil.isNull(belongRuleGroupCode)){
//            //数据加工
//            return sysDataProcessService.dataProcessMain(belongRuleGroupCode);
//        }else{
//            return pageColumnValue;
//        }
//    }

}
