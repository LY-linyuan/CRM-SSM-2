package com.onlyone.crm.workbench.service.impl;

import com.onlyone.crm.workbench.domain.TranHistory;
import com.onlyone.crm.workbench.mapper.TranHistoryMapper;
import com.onlyone.crm.workbench.service.TranHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 临渊
 * @Date 2023-12-12 14:02
 */

@Service
public class TranHistoryServiceImpl implements TranHistoryService {

    @Autowired
    private TranHistoryMapper tranHistoryMapper;

    @Override
    public List<TranHistory> queryTranHistoryForDetailByTranId(String id) {
        return tranHistoryMapper.selectTranHistoryForDetailByTranId(id);
    }
}
