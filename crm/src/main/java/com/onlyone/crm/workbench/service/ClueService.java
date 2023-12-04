package com.onlyone.crm.workbench.service;

import com.onlyone.crm.workbench.domain.Clue;

public interface ClueService {

    int saveCreateClue(Clue clue);

    Clue selectDetailClueByClueId(String id);

}
