package com.onlyone.crm.workbench.service;

import com.onlyone.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * @Author 临渊
 * @Date 2023-11-25 17:07
 */
public interface ActivityService {

    int saveCreateActivity(Activity activity);

    List<Activity> selectActivityByConditionOfPage(Map map);

    Integer selectActivityByConditionCount(Map map);

    Integer deleteActivityByIds(String[] id);

    Activity selectActivityById(String id);

    Integer updateActivityById(Activity activity);

    List<Activity> selectAllActivities();

    Integer insertActivityList(List<Activity> activityList);

    Activity selectActivityForDetailById(String id);

    List<Activity> selectActivityListByClueActivityRelationByClueId(String clueId);

}
