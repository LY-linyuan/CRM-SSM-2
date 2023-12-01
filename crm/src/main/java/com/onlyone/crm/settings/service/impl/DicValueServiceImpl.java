package com.onlyone.crm.settings.service.impl;

import com.onlyone.crm.settings.domain.DicValue;
import com.onlyone.crm.settings.mapper.DicValueMapper;
import com.onlyone.crm.settings.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 临渊
 * @Date 2023-12-01 14:45
 */

@Service
public class DicValueServiceImpl implements DicValueService {

    @Autowired
    DicValueMapper dicValueMapper;

    @Override
    public List<DicValue> selectDicValueByTypeCode(String typeCode) {
        return dicValueMapper.selectDicValueByTypeCode(typeCode);
    }
}
