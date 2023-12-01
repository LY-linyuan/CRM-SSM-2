package com.onlyone.crm.settings.service;

import com.onlyone.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueService {

    List<DicValue> selectDicValueByTypeCode(String typeCode);

}
