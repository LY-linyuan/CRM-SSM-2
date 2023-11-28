package com.onlyone.crm.workbench.mapper;

import com.onlyone.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Sat Nov 25 17:00:55 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Sat Nov 25 17:00:55 CST 2023
     */
    int insert(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Sat Nov 25 17:00:55 CST 2023
     */
    int insertSelective(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Sat Nov 25 17:00:55 CST 2023
     */
    Activity selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Sat Nov 25 17:00:55 CST 2023
     */
    int updateByPrimaryKeySelective(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Sat Nov 25 17:00:55 CST 2023
     */
    int updateByPrimaryKey(Activity record);


    /**
     * 保存创建的市场活动
     * @param activity
     * @return
     */
    int insertActivity(Activity activity);


    /**
     * 分页按条件查询市场活动
     * @param map
     * @return
     */
    List<Activity> selectActivityByConditionOfPage(Map map);

    /**
     * 分页查询总数目
     * @param map
     * @return
     */
    Integer selectActivityByConditionCount(Map map);


    /**
     * 根据复选框删除市场活动
     * @param id
     * @return
     */
    Integer deleteActivityByIds(String[] id);


    /**
     * 根据id查询市场活动
     * @param id
     * @return
     */
    Activity selectActivityById(String id);

    /**
     * 根据id修改市场活动
     * @param activity
     * @return
     */
    Integer updateActivityById(Activity activity);

    /**
     * 查询所有的市场活动
     * @return
     */
    List<Activity> selectAllActivities();


    /**
     * 导入市场活动List
     * @param activityList
     * @return
     */
    Integer insertActivityList(List<Activity> activityList);
}