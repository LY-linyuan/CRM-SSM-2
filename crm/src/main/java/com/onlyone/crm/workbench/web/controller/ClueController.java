package com.onlyone.crm.workbench.web.controller;

import com.onlyone.crm.commons.contants.Contants;
import com.onlyone.crm.commons.domain.ReturnObject;
import com.onlyone.crm.commons.utils.DateUtil;
import com.onlyone.crm.commons.utils.UUIDUtil;
import com.onlyone.crm.settings.domain.DicValue;
import com.onlyone.crm.settings.domain.User;
import com.onlyone.crm.settings.service.DicValueService;
import com.onlyone.crm.settings.service.UserService;
import com.onlyone.crm.workbench.domain.Clue;
import com.onlyone.crm.workbench.service.ClueService;
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
}
