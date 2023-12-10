package com.onlyone.crm.workbench.web.controller;

import com.onlyone.crm.commons.contants.Contants;
import com.onlyone.crm.commons.domain.ReturnObject;
import com.onlyone.crm.commons.utils.DateUtil;
import com.onlyone.crm.commons.utils.UUIDUtil;
import com.onlyone.crm.settings.domain.DicValue;
import com.onlyone.crm.settings.domain.User;
import com.onlyone.crm.settings.service.DicValueService;
import com.onlyone.crm.settings.service.UserService;
import com.onlyone.crm.workbench.domain.Activity;
import com.onlyone.crm.workbench.domain.Clue;
import com.onlyone.crm.workbench.domain.ClueActivityRelation;
import com.onlyone.crm.workbench.domain.ClueRemark;
import com.onlyone.crm.workbench.service.ActivityService;
import com.onlyone.crm.workbench.service.ClueActivityRelationService;
import com.onlyone.crm.workbench.service.ClueRemarkService;
import com.onlyone.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @Author 临渊
 * @Date 2023-12-01 14:39
 */

@Controller
public class ClueController {

    @Autowired
    DicValueService dicValueService;
    @Autowired
    UserService userService;
    @Autowired
    ClueService clueService;

    @Autowired
    ActivityService activityService;

    @Autowired
    ClueRemarkService clueRemarkService;

    @Autowired
    ClueActivityRelationService clueActivityRelationService;


    @RequestMapping("/workbench/clue/index")
    public String index(HttpServletRequest request) {
        List<DicValue> appellationList = dicValueService.selectDicValueByTypeCode("appellation");
        List<DicValue> clueStateList = dicValueService.selectDicValueByTypeCode("clueState");
        List<DicValue> sourceList = dicValueService.selectDicValueByTypeCode("source");
        List<User> userList = userService.selectAllNoLockStateUser();
        request.setAttribute("appellationList", appellationList);
        request.setAttribute("clueStateList", clueStateList);
        request.setAttribute("sourceList", sourceList);
        request.setAttribute("userList", userList);
        return "workbench/clue/index";
    }

    @RequestMapping("/workbench/clue/saveCreateClue")
    public @ResponseBody Object saveCreateClue(Clue clue, HttpSession session) {
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateTime(DateUtil.formatDateTime(new Date()));
        clue.setCreateBy(((User) session.getAttribute(Contants.SESSION_USER)).getId());
        ReturnObject returnObject = new ReturnObject();
        try {
            int count = clueService.saveCreateClue(clue);
            if (count == 1) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
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


    @RequestMapping("/workbench/clue/detailClue")
    public String detailClue(String id, HttpServletRequest request) {

        Clue clue = clueService.selectDetailClueByClueId(id);
        List<ClueRemark> clueRemarkList = clueRemarkService.selectClueRemarkByClueId(id);
        List<Activity> activityList = activityService.selectActivityListByClueActivityRelationByClueId(id);
        request.setAttribute("clue", clue);
        request.setAttribute("clueRemarkList", clueRemarkList);
        request.setAttribute("activityList", activityList);
        return "workbench/clue/detail";
    }

    @RequestMapping("/workbench/clue/selectActivityListByLikeNameAndClueId")
    public @ResponseBody Object selectActivityListByLikeNameAndClueId(String activityName, String clueId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("activityName", activityName);
        map.put("clueId", clueId);
        return activityService.selectActivityListByLikeNameAndClueId(map);

    }

    @RequestMapping("/workbench/clue/saveClueActivityRelation")
    public @ResponseBody Object saveClueActivityRelation(String[] activityIds, String clueId) {
        ReturnObject returnObject = new ReturnObject();
        List<ClueActivityRelation> clueActivityRelationList = new ArrayList<ClueActivityRelation>();
        for (String activityId : activityIds) {
            ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setClueId(clueId);
            clueActivityRelation.setActivityId(activityId);
            clueActivityRelationList.add(clueActivityRelation);
        }
        try {
            int count = clueActivityRelationService.saveCreateClueActivityRelationByList(clueActivityRelationList);
            if (count == activityIds.length) {
                List<Activity> activityList = activityService.selectActivityListByActivityId(activityIds);
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(activityList);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("网络繁忙...请稍后再试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("网络繁忙...请稍后再试");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/clue/deleteClueActivityRelationById")
    public @ResponseBody Object deleteClueActivityRelationById(ClueActivityRelation clueActivityRelation) {
        ReturnObject returnObject = new ReturnObject();
        try {
            int count = clueActivityRelationService.deleteClueActivityRelationById(clueActivityRelation);
            if (count == 1) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("当前网络繁忙...请稍后再试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("当前网络繁忙...请稍后再试");
        }
        return returnObject;
    }


    @RequestMapping("/workbench/clue/toConvert")
    public String toConvert(String id, HttpServletRequest request) {
        Clue clue = clueService.selectDetailClueByClueId(id);
        List<DicValue> stageList = dicValueService.selectDicValueByTypeCode("stage");
        request.setAttribute("clue", clue);
        request.setAttribute("stageList", stageList);
        return "workbench/clue/convert";
    }


    @RequestMapping("/workbench/clue/selectConvertActivityListByLikeNameAndClueId")
    public @ResponseBody Object selectConvertActivityListByLikeNameAndClueId(String activityName, String clueId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("activityName", activityName);
        map.put("clueId", clueId);
        return activityService.selectConvertActivityListByLikeNameAndClueId(map);
    }

    @RequestMapping("/workbench/clue/convertClue")
    public @ResponseBody Object convertClue(String clueId, String money, String name,
                                            String expectedDate, String stage, String activityId,
                                            String isCreateTran, HttpSession session) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("clueId", clueId);
        map.put("monet", money);
        map.put("name", name);
        map.put("expectedDate", expectedDate);
        map.put("stage", stage);
        map.put("activityId", activityId);
        map.put("isCreateTran", isCreateTran);
        map.put(Contants.SESSION_USER, (User) session.getAttribute(Contants.SESSION_USER));
        ReturnObject returnObject = new ReturnObject();
        try {
            clueService.saveConvertClue(map);
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("网络繁忙...请稍后再试");
        }
        return returnObject;
    }
}
