package com.onlyone.crm.workbench.service;

import com.onlyone.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationService {

    int saveCreateClueActivityRelationByList(List<ClueActivityRelation> clueActivityRelationList);

    int deleteClueActivityRelationById(ClueActivityRelation clueActivityRelation);

}
