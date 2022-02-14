package com.generator.mapper.sysInterfaceColumnRule;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.generator.bean.sysInterfaceColumnRule.SysInterfaceColumnRule;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SysInterfaceColumnRuleMapper extends BaseMapper<SysInterfaceColumnRule> {

    List<SysInterfaceColumnRule> querySysInterfaceColumnRuleList(Map<String, Object> paramMap);

    void insertSysInterfaceColumnRuleList(List<SysInterfaceColumnRule> sysInterfaceColumnRuleList);

    void deleteSysInterfaceColumnRule(Map<String, Object> paramMap);

}
