package com.onlyone.crm.settings.web.controller;

import com.onlyone.crm.commons.contants.Contants;
import com.onlyone.crm.commons.domain.ReturnObject;
import com.onlyone.crm.commons.utils.DateUtils;
import com.onlyone.crm.settings.domain.User;
import com.onlyone.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 临渊
 * @Date 2023-11-24 14:24
 */

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/settings/qx/user/toLogin")
    public String toLogin() {
        return "settings/qx/user/login";
    }


    @RequestMapping("/settings/qx/user/login")
    public @ResponseBody Object login(String loginAct, String loginPwd,
                                      String isRemPwd, HttpServletRequest request, HttpSession session, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);
        User user = userService.selectUserByLoginActAndLoginPwd(map);
        ReturnObject returnObject = new ReturnObject();
        // 返回空 账号密码错误
        if (user == null) {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("用户名或密码错误");
        } else {
            String nowDateStr = DateUtils.formatDateTime(new Date());
            if (nowDateStr.compareTo(user.getExpireTime()) < 0) {
                // 账号过期
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("您的账户已过期");
            } else if ("0".equals(user.getLockState())) {
                // 账号锁定
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("您的账户已锁定");
            } else if (!user.getAllowIps().contains(request.getRemoteAddr())) {
                // ip受限
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("您的ip地址受限");
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setMessage("登录成功");
                session.setAttribute(Contants.SESSION_USER, user);
                System.out.println(session);
                if ("true".equals(isRemPwd)) {
                    Cookie cookieAct = new Cookie("loginAct", loginAct);
                    cookieAct.setMaxAge(60 * 60 * 24 * 10);
                    Cookie cookiePwd = new Cookie("loginPwd", loginPwd);
                    cookiePwd.setMaxAge(60 * 60 * 24 * 10);
                    response.addCookie(cookieAct);
                    response.addCookie(cookiePwd);
                } else {
                    Cookie cookieAct = new Cookie("loginAct", "1");
                    cookieAct.setMaxAge(0);
                    Cookie cookiePwd = new Cookie("loginPwd", "1");
                    cookiePwd.setMaxAge(0);
                    response.addCookie(cookieAct);
                    response.addCookie(cookiePwd);
                }
            }
        }
        return returnObject;
    }

    @RequestMapping("/settings/qx/user/loginOut")
    public String loginOut(HttpSession session, HttpServletResponse response) {
        Cookie cookieAct = new Cookie("loginAct", "1");
        cookieAct.setMaxAge(0);
        Cookie cookiePwd = new Cookie("loginPwd", "1");
        cookiePwd.setMaxAge(0);
        response.addCookie(cookieAct);
        response.addCookie(cookiePwd);
        session.invalidate();
        return "redirect:/";
    }


}
