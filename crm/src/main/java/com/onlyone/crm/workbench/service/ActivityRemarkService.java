package com.onlyone.crm.workbench.service;

import com.onlyone.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkService {

    List<ActivityRemark> selectActivityRemarkListForDetailByActivityId(String activityId);

}
