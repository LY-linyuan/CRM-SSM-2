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
import java.util.List;

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





}
