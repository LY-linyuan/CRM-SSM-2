package com.onlyone.crm.workbench.web.controller;

import com.onlyone.crm.commons.contants.Contants;
import com.onlyone.crm.commons.domain.ReturnObject;
import com.onlyone.crm.commons.utils.DateUtil;
import com.onlyone.crm.commons.utils.UUIDUtil;
import com.onlyone.crm.settings.domain.User;
import com.onlyone.crm.workbench.domain.ActivityRemark;
import com.onlyone.crm.workbench.mapper.ActivityMapper;
import com.onlyone.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Author 临渊
 * @Date 2023-11-29 15:18
 */

@Controller
public class ActivityRemarkController {

    @Autowired
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/saveCreateActivityRemark")
    public @ResponseBody Object saveCreateActivityRemark(ActivityRemark activityRemark, HttpSession session) {
        activityRemark.setId(UUIDUtil.getUUID());
        activityRemark.setCreateTime(DateUtil.formatDateTime(new Date()));
        activityRemark.setCreateBy(((User) session.getAttribute(Contants.SESSION_USER)).getId());
        activityRemark.setEditFlag("0");
        ReturnObject returnObject = new ReturnObject();
        try {
            Integer count = activityRemarkService.saveCreateActivityRemark(activityRemark);
            if (count == 1) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(activityRemark);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("当前网络繁忙...请稍后再试!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("当前网络繁忙...请稍后再试!");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/deleteActivityRemarkById")
    public @ResponseBody Object deleteActivityRemarkById(String id) {
        ReturnObject returnObject = new ReturnObject();
        try {
            Integer count = activityRemarkService.deleteActivityRemarkById(id);
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

    @RequestMapping("/workbench/activity/saveEditActivityRemark")
    public @ResponseBody Object saveEditActivityRemark(ActivityRemark activityRemark, HttpSession session) {
        activityRemark.setEditTime(DateUtil.formatDateTime(new Date()));
        activityRemark.setEditBy(((User) session.getAttribute(Contants.SESSION_USER)).getId());
        activityRemark.setEditFlag(Contants.REMARK_EDIT_FLAG_YES_EDITED);
        ReturnObject returnObject = new ReturnObject();
        try {
            Integer count = activityRemarkService.saveEditActivityRemark(activityRemark);
            if (count == 1) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(activityRemark);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("当前系统繁忙...请稍后再试");
            }

        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("当前系统繁忙...请稍后再试");
        }
        return returnObject;
    }
}
