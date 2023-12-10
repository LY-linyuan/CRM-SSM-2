package com.onlyone.crm.workbench.service;

import com.onlyone.crm.workbench.domain.Clue;

import java.util.Map;

public interface ClueService {

    int saveCreateClue(Clue clue);

    Clue selectDetailClueByClueId(String id);

    void saveConvertClue(Map<String,Object> map);

}
