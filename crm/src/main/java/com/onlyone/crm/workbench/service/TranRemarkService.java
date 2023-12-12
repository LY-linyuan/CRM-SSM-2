package com.onlyone.crm.workbench.service;


import com.onlyone.crm.workbench.domain.TranRemark;

import java.util.List;

public interface TranRemarkService {


    List<TranRemark> queryTranRemarkForDetailByTranId(String id);
}
