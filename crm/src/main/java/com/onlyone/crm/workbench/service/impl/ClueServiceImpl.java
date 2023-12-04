package com.onlyone.crm.workbench.service.impl;

import com.onlyone.crm.workbench.domain.Clue;
import com.onlyone.crm.workbench.mapper.ClueMapper;
import com.onlyone.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 临渊
 * @Date 2023-12-01 14:42
 */

@Service
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueMapper clueMapper;

    @Override
    public int saveCreateClue(Clue clue) {
        return clueMapper.insertClue(clue);
    }

    @Override
    public Clue selectDetailClueByClueId(String id) {
        return clueMapper.selectDetailClueByClueId(id);
    }
}
