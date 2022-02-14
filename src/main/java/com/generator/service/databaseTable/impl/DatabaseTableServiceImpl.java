package com.generator.service.databaseTable.impl;

import com.generator.mapper.DatabaseTableMapper;
import com.generator.service.databaseTable.DatabaseTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("databaseTableService")
public class DatabaseTableServiceImpl implements DatabaseTableService {

    @Autowired
    private DatabaseTableMapper databaseTableMapper;

}
