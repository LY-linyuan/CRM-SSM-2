package com.onlyone.crm.workbench.web.controller;

import com.onlyone.crm.commons.contants.Contants;
import com.onlyone.crm.commons.domain.ReturnObject;
import com.onlyone.crm.commons.utils.DateUtil;
import com.onlyone.crm.commons.utils.UUIDUtil;
import com.onlyone.crm.settings.domain.User;
import com.onlyone.crm.settings.service.UserService;
import com.onlyone.crm.workbench.domain.Activity;
import com.onlyone.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 临渊
 * @Date 2023-11-25 16:13
 */

@Controller
public class ActivityController {

    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;

    @RequestMapping("/workbench/activity/index")
    public String index(HttpServletRequest request) {
        List<User> userList = userService.selectAllNoLockStateUser();
        request.setAttribute("userList", userList);
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/saveCreatActivity")
    public @ResponseBody Object saveCreatActivity(Activity activity, HttpServletRequest request) {
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateUtil.formatDateTime(new Date()));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        activity.setCreateBy(user.getId());
        ReturnObject returnObject = new ReturnObject();
        try {
            int resultCount = activityService.saveCreateActivity(activity);
            if (resultCount == 1) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙请...稍后再试!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙请...稍后再试!");
        }
        return returnObject;
    }



    @RequestMapping("/workbench/activity/selectActivityByConditionOfPageAndCount")
    public @ResponseBody Object selectActivityByConditionOfPageAndCount(String name, String owner, String startDate, String endDate,
                                                                        Integer pageNo, Integer pageSize) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        Integer startNo = ( pageNo - 1 ) * pageSize;
        map.put("startNo", startNo);
        map.put("pageSize", pageSize);
        List<Activity> activityList = activityService.selectActivityByConditionOfPage(map);
        Integer totalRows = activityService.selectActivityByConditionCount(map);
        Map<String, Object> returnObjectMap = new HashMap<String, Object>();
        returnObjectMap.put("activityList", activityList);
        returnObjectMap.put("totalRows", totalRows);
        return returnObjectMap;
    }

    @RequestMapping("/workbench/activity/deleteActivityByIds")
    public @ResponseBody Object deleteActivityByIds(String[] id) {
        ReturnObject returnObject = new ReturnObject();
        try {
            Integer count = activityService.deleteActivityByIds(id);
            if (count > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙...请稍后再试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙...请稍后再试");
        }
        return returnObject;
    }


    @RequestMapping("/workbench/activity/selectActivityById")
    public @ResponseBody Object selectActivityById(String id) {
        return activityService.selectActivityById(id);
    }

    @RequestMapping("/workbench/activity/updateActivityById")
    public @ResponseBody Object updateActivityById(Activity activity, HttpServletRequest request) {
        activity.setEditTime(DateUtil.formatDateTime(new Date()));
        activity.setEditBy(((User) request.getSession().getAttribute(Contants.SESSION_USER)).getId());
        ReturnObject returnObject = new ReturnObject();
        try {
            Integer count = activityService.updateActivityById(activity);
            if (count == 1) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙...请稍后再试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙...请稍后再试");
        }
        return returnObject;
    }


}
