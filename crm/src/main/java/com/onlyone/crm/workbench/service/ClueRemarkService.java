package com.onlyone.crm.workbench.service;

import com.onlyone.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkService {

    List<ClueRemark> selectClueRemarkByClueId(String clueId);

}
