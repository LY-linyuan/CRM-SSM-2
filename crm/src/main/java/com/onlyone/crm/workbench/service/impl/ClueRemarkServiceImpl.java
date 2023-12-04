package com.onlyone.crm.workbench.service.impl;

import com.onlyone.crm.workbench.domain.ClueRemark;
import com.onlyone.crm.workbench.mapper.ClueRemarkMapper;
import com.onlyone.crm.workbench.service.ClueRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 临渊
 * @Date 2023-12-04 13:43
 */

@Service
public class ClueRemarkServiceImpl implements ClueRemarkService {

    @Autowired
    private ClueRemarkMapper clueRemarkMapper;

    @Override
    public List<ClueRemark> selectClueRemarkByClueId(String clueId) {
        return clueRemarkMapper.selectClueRemarkByClueId(clueId);
    }
}
