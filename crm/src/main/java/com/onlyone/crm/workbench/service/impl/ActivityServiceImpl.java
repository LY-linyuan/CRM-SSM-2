package com.onlyone.crm.workbench.service.impl;

import com.onlyone.crm.workbench.domain.Activity;
import com.onlyone.crm.workbench.mapper.ActivityMapper;
import com.onlyone.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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


    @Override
    public List<Activity> selectActivityByConditionOfPage(Map map) {
        return activityMapper.selectActivityByConditionOfPage(map);
    }

    @Override
    public Integer selectActivityByConditionCount(Map map) {
        return activityMapper.selectActivityByConditionCount(map);
    }

    @Override
    public Integer deleteActivityByIds(String[] id) {
        return activityMapper.deleteActivityByIds(id);
    }

    @Override
    public Activity selectActivityById(String id) {
        return activityMapper.selectActivityById(id);
    }

    @Override
    public Integer updateActivityById(Activity activity) {
        return activityMapper.updateActivityById(activity);
    }

    @Override
    public List<Activity> selectAllActivities() {
        return activityMapper.selectAllActivities();
    }

    @Override
    public Integer insertActivityList(List<Activity> activityList) {
        return activityMapper.insertActivityList(activityList);
    }

    @Override
    public Activity selectActivityForDetailById(String id) {
        return activityMapper.selectActivityForDetailById(id);
    }
}
