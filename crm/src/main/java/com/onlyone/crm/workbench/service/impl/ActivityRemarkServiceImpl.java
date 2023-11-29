package com.onlyone.crm.workbench.service.impl;

import com.onlyone.crm.workbench.domain.ActivityRemark;
import com.onlyone.crm.workbench.mapper.ActivityRemarkMapper;
import com.onlyone.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author 临渊
 * @Date 2023-11-29 12:59
 */
public class ActivityRemarkServiceImpl implements ActivityRemarkService {

    @Autowired
    ActivityRemarkMapper activityRemarkMapper;

    @Override
    public List<ActivityRemark> selectActivityRemarkListForDetailByActivityId(String activityId) {
        return activityRemarkMapper.selectActivityRemarkListForDetailByActivityId(activityId);
    }
}
