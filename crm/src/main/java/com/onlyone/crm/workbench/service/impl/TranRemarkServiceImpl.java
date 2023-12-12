package com.onlyone.crm.workbench.service.impl;

import com.onlyone.crm.workbench.domain.TranRemark;
import com.onlyone.crm.workbench.mapper.TranRemarkMapper;
import com.onlyone.crm.workbench.service.TranRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 临渊
 * @Date 2023-12-12 14:01
 */

@Service
public class TranRemarkServiceImpl implements TranRemarkService {

    @Autowired
    private TranRemarkMapper tranRemarkMapper;

    @Override
    public List<TranRemark> queryTranRemarkForDetailByTranId(String id) {
        return tranRemarkMapper.selectTranRemarkForDetailByTranId(id);
    }
}
