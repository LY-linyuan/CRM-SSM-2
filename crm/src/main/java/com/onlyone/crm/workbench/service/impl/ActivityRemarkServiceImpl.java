package com.onlyone.crm.workbench.service.impl;

import com.onlyone.crm.workbench.domain.ActivityRemark;
import com.onlyone.crm.workbench.mapper.ActivityRemarkMapper;
import com.onlyone.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 临渊
 * @Date 2023-11-29 12:59
 */

@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {

    @Autowired
    ActivityRemarkMapper activityRemarkMapper;

    @Override
    public List<ActivityRemark> selectActivityRemarkListForDetailByActivityId(String activityId) {
        return activityRemarkMapper.selectActivityRemarkListForDetailByActivityId(activityId);
    }

    @Override
    public Integer saveCreateActivityRemark(ActivityRemark activityRemark) {
        return activityRemarkMapper.insertActivityRemark(activityRemark);
    }

    @Override
    public Integer deleteActivityRemarkById(String id) {
        return activityRemarkMapper.deleteActivityRemarkById(id);
    }

    @Override
    public Integer saveEditActivityRemark(ActivityRemark activityRemark) {
        return activityRemarkMapper.updateActivityRemark(activityRemark);
    }
}
