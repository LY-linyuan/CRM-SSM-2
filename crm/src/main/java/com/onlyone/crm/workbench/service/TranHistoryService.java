package com.onlyone.crm.workbench.service;

import com.onlyone.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryService {
    List<TranHistory> queryTranHistoryForDetailByTranId(String id);
}
