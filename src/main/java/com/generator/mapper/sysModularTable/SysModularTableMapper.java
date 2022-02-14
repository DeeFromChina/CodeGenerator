package com.generator.mapper.sysModularTable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.generator.bean.sysModularTable.SysModularTable;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysModularTableMapper extends BaseMapper<SysModularTable> {
}
