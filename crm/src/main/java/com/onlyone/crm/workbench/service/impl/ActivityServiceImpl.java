package com.onlyone.crm.workbench.service.impl;

import com.onlyone.crm.workbench.domain.Activity;
import com.onlyone.crm.workbench.mapper.ActivityMapper;
import com.onlyone.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 临渊
 * @Date 2023-11-25 17:08
 */

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public int saveCreateActivity(Activity activity) {
        return activityMapper.insertActivity(activity);
    }
}
